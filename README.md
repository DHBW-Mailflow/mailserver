# mailserver

This server is supposed to handle TCP connections to receive emails from clients. A connection will be possible via IMAP as well as SMTP.
The vision is a fully functional mailserver to manage your emails.

## Run it

`docker build --no-cache -t mailflow .; docker run -it mailflow`

## Our Domain

### Value Objects

- [Address](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/Address.java): Representation of an email address, consisting of a localPart and a domain, separated by an `@` sign.
- [Attachment](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/Attachment.java): Representation of an email attachment, consisting of the data bytes, content-type and filename
- [EmailId](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/EmailId.java): Holds the unique ID of an email
- [EmailMetadata](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/EmailMetadata.java): Consists of all the metadata of an email
  - [Address](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/Address.java): Representation of an email address, consisting of a localPart and a domain, separated by an `@` sign.
  - List<[Header](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/Header.java)>: Has a name and a value, e.g. `Content-Language: de`
  - [Recipients](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/Recipients.java): Holds the addresses To, CC and BCC recipients
  - [SentDate](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/SentDate.java): Represents the date and time an email has been send
  - [Subject](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/Subject.java): Holds the subject of the email

- [ExportableEmail](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/ExportableEmail.java): Special representation of an email used for exporting to a different format
- [ExportableMailbox](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/ExportableMailbox.java): Special representation of a mailbox used for exporting to a different format
- [ExportableRecipients](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/ExportableRecipients.java): Special representation of the recipients used for exporting to a different format
- [MailboxId](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/domain/email/value_objects/MailboxId.java): Holds the unique ID of a mailbox

### Use Cases

- [LoginUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/auth/LoginUseCase.java)
- [LogoutUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/auth/LogoutUseCase.java)
- [RegisterUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/auth/RegisterUseCase.java)
- [AnswerEmailUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/email/answer/AnswerEmailUseCase.java)
- [DeliverUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/email/deliver_services/DeliverUseCase.java)
- [DeleteEmailsUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/email/organize/DeleteEmailsUseCase.java)
- [MarkEmailUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/email/organize/mark/MarkEmailUseCase.java)
- [ProvideEmailsUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/email/provide/ProvideEmailsUseCase.java)
- [SearchEmailUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/email/searchemail/content/SearchEmailUseCase.java)
- [EmailSendUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/email/send/EmailSendUseCase.java)
- [ChangePasswordUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/usersettings/changepassword/ChangePasswordUseCase.java)
- [ChangeSignatureUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/usersettings/changesignature/ChangeSignatureUseCase.java)
- [ExportUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/core/application/usersettings/export/ExportUseCase.java)
- [ScheduledSendTimeParserUseCase](src/main/java/de/dhbw/karlsruhe/students/mailflow/external/infrastructure/email/parsing/ScheduledSendTimeParserUseCase.java)

## Functionality

- [x] Receive Emails
  - [x] Parse incoming Emails.
  - [x] Store Emails in a Mailbox.
    - [x] Create a Mailbox with multiple Emails.
  - [x] Store Emails in a Folder.
  - [x] Send Emails to the cc, bcc, to-Addresses.
- [ ] Synchronization
  - [ ] provide a list of emails when a client connects.
  - [ ] provide a list of emails when a client requests a refresh.
  - [ ] provide a list of emails with according folders instead.
- [x] Filtering
  - [x] Filter incoming messages Spam to a spam folder
- [x] Authorization / Authentication
  - [x] implicit registration
  - [x] login to get access to own mailbox

## Domains

## Ubiquitous Language

- `Recieving Email` : Emails that are sent to this server. This can happen by a client or in the future by another mailserver
- `Account`: Has multiple mailboxes that contain `unread`-, filtered `spam`- or `sent`-Emails.
- `Mailbox`: A mailbox is a collection of e-mails that are grouped together (e.g. all `spam` emails are in the `spam` Mailbox of one User)
- `Email`: An email is a message that can be sent to a mailbox.
- `Address`: An address is a unique identifier for a user. They are represented by a string like `some@mail.de`.
- `User`: A user is linked with one account.

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
