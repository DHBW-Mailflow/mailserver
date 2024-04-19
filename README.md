# mailserver

This server is supposed to handle TCP connections to receive emails from clients. A connection will be possible via IMAP as well as SMTP.
The vision is a fully functional mailserver to manage your emails.

## Run it

`docker build --no-cache -t mailflow .; docker run -it mailflow`

## Functionality

- [ ] Receive Emails
  - [x] Parse incoming Emails.
  - [ ] Store Emails in a Mailbox.
    - [ ] Create a Mailbox with multiple Emails.
  - [ ] Store Emails in a Folder.
  - [ ] Send Emails to the cc, bcc, to-Addresses.
- [ ] Synchronization
  - [ ] provide a list of emails when a client connects.
  - [ ] provide a list of emails when a client requests a refresh.
  - [ ] provide a list of emails with according folders instead.
- [ ] Filtering
  - [ ] Filter incoming messages Spam to a spam folder
- [ ] Authorization / Authentication
  - [x] implicit registration
  - [ ] login to get access to own mailbox

## Domains

## Ubiquitous Language

- `Recieving Email` : Emails that are sent to this server. This can happen by a client or in the future by another mailserver
- `Folders`: A folder is a collections of emails. In the future, there might be a folder of `unread`-, filtered `spam`- or `sent`-Emails.
- `Mailbox`: A mailbox is a collection of folders. It is linked to an EmailAddress.
- `Email`: An email is a message that can be sent to a mailbox.
- `Address`: An address is a unique identifier for a user. They are represented by a string like `some@mail.de`.

## Architecture

### Domain Layer

There are the following domains:

- `Common`: Contains common interfaces like `AggregateRoot`, `Entity` and `ValueObject` which are implemented in the other domains.
- `Mailbox`: Represents a mailbox. A Mailbox has a `MailboxId` and a EmailAddress `Address` linked to it.
- `Email`: Represents a user with all its attributes. There is an extra `Email.Metadata` class for further attributes.
  - `EmailId`: Represents the unique id of an email. It is a value object.
  - `Content`: Represents the content of an email. Currently, it is a mutable String object inside the Email entity.
  - `Address`: Represents an Email-Address like `some@mail.de`. It is a value object.
  - `Header`: Represents the header of an email. A header could look like this: `From: someone@example.com`.
  - `EmailMetadata`: Represents the metadata of an email. It contains the `sentDate`, `subject` and `attachments`. It is a value object.
    - `SentDate`: Represents the date when an email was sent. It is a value object.
    - `Subject`: Represents the subject the short title of an email. It is a value object.
    - `Attachment`: Represents an attachment of an email. The attachment has a `name` and the data is represented in a byte-array. It is a value object.

### Application Layer

- parsing
  - `EmailParser`: An interface to parse Email of any format to a domain Email object.
  - `EmailParsingException`: A custom exception to handle parsing errors.

### Infrastructure Layer

- emails:
  - `EmailMetadataFactory`: A factory to create domain EmailMetadata objects by passing information of the external library jakarta.
  - `EmlParser`: An implementation of the `EmailParser` interface to parse .eml files to domain Email objects.
