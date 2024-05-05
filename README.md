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
<details>
  <summary>auth</summary>

- `AuthorizationException`: A custom exception to handle authorization errors.
- `HashingFailedException`: A custom exception to handle hashing errors.
- `LoadingUserException`: A custom exception to handle loading user errors.
- `PasswordChecker`: An interface to check the password of a user.
- `UserCreater`: An interface to create a user.
</details>

<details>
  <summary>common.models</summary>

- `AggregateRoot`: An abstract class template for every AggregateRoot object that will be created.
- `Entity`: An abstract class template for every Entity object that will be created.
</details>

<details>
  <summary>email</summary>

- enums
  - `Label`: Represents the label of an email. An email can be `READ`, `UNREAD`.
  - `MailboxType`: Represents the type of mailbox. A mailbox can be `INBOX`, `SENT`, `SPAM`, `DELETED` and `DRAFT`.
- exceptions
  - `ExportMailboxException`: A custom exception to handle exporting errors.
  - `MailboxLoadingException`: A custom exception to handle loading mailbox errors.
  - `MailboxSavingException`: A custom exception to handle saving mailbox errors.
- value_objects
  - `Address`: Represents an Email-Address like `user@mail.de`.
  - `Attachment`: Represents an attachment of an email. The attachment has a `name` and the data is represented in a byte-array.
  - `EmailId`: Represents the unique id of an email.
  - `EmailMetadata`: Represents the metadata of an email. It contains the `sentDate`, `subject` and `attachments`.
  - `ExportableEmail`: Represents an email that can be exported. It contains the `Subject`, `Content`, `Senderaddress` ,`ExportableRecipients`, `SentDate`, `isRead` and `Header`.
  - `ExportableMailbox`: Represents a mailbox that can be exported. It contains the `Address`, `MailboxType`, `ExportableEmail` and `Exporteddate`.
  - `ExportableRecipients`: Represents the recipients of an email that can be exported. It contains the `To`, `CC` and `BCC` recipients.
  - `Header`: Represents the header of an email. A header could look like this: `From: someone@example.com`.
  - `MailboxId`: Represents the unique id of a mailbox.
  - `Recipients`: Represents the recipients of an email. It contains the `To`, `CC` and `BCC` recipients.
  - `SentDate`: Represents the date when an email was sent.
  - `Subject`: Represents the subject the short title of an email.
- `Email`: Represents an email. An email has a `EmailId`, `Content`, `Address`, `Header` and `EmailMetadata` linked to it.
- `EmailBuilder`: A builder to build emails.
- `ExportableMailboxRepository`: A interface to save mailboxes.
- `InvalidRecipients`: A custom exception to handle invalid recipients.
- `Mailbox`: Represents a mailbox. A Mailbox has a `MailboxId` and a EmailAddress `Address` linked to it.
- `MailboxRepository`: A interface to save and find mailboxes.
</details>

<details>
  <summary>imap</summary>

  - `ImapListenerConfig`: Represents the configuration of an IMAP listener.
  - `ImapListenerException`: A custom exception to handle IMAP listener errors.
</details>

<details>
  <summary>server</summary>

- `Server`: A interface to start and stop a server.
</details>

<details>
  <summary>user</summary>

- user
  - exceptions
    - `InvalidEmailException`: A custom exception to handle invalid email errors.
    - `InvalidPasswordException`: A custom exception to handle invalid password errors.
    - `InvalidSaltException`: A custom exception to handle invalid salt errors.
    - `LoadSettingsException`: A custom exception to handle loading settings errors.
    - `SaveSettingsException`: A custom exception to handle saving settings errors.
    - `SaveUserException`: A custom exception to handle saving user errors.
  - `User`: Represents a user. A user has a `Address`, `Password` and a `Salt` linked to it.
  - `UserRepository`: A interface to save and find users.
  - `UserSettings`: Represents the settings of a user. A user has a `Signature` linked to it.
  - `UserSettingsRepository`: A interface to update and get user settings.
</details>




### Application Layer

<details>
  <summary>auth</summary>

- auth
  - `AuthSession`: Represents a user session. It contains a user object that represents the user currently logged in.
  - `AuthSessionUseCase`: A interface to handle the login and logout of a user.
  - `LoginService`: Represents a service to login and authorize a user.
  - `LoginUseCase`: A interface to handle the login of a user.
  - `LogoutService`: Represents a service to logout a user.
  - `LogoutUseCase`: A interface to handle the logout of a user.
  - `RegisterService`: Represents a service to register a user.
  - `RegisterUseCase`: A interface to handle the registration of a user.
  - `UCCollectionAuth`: A collection of all use cases for the auth domain.
</details>


<details>
  <summary>email</summary>

- answer
  - `AnswerEmailUseCase`: A interface to handle the answering of an email.
  - `AnswerEmailService`: Represents a service to answer an email.
  - `UCCollectionAnswerEmails`: A collection of all use cases for the answer domain.
- deliver_services
  - `DeliverUseCase`: A interface to handle the delivery of an email.
  - `DeliverInInboxService`: Represents a service to deliver an email to the inbox of a user.
  - `DeliverInSentService`: Represents a service to deliver an email to the sent folder of a user.
  - `DeliverIntoFolderService`: Represents a service to deliver an email to a specific folder of a user.
  - `DeliverIntoSpamService`: Represents a service to deliver an email to the spam folder of a user.
  - `DeliverScheduledMailJob`: Represents a job to deliver a scheduled email.
- organize
  - mark
    - `MarkAsReadService`: Represents a service to mark an email as read.
    - `MarkAsUnreadService`: Represents a service to mark an email as unread.
    - `MarkEmailUseCase`: A interface to handle the marking of an email.
  - `DeleteEmailService`: Represents a service to delete an email from a mailbox.
  - `DeleteEmailsUseCase`: A interface to handle the deletion of emails.
  - `MarkAsNotSpamService`: Represents a service to mark an email as not spam.
  - `MarkAsSpamService`: Represents a service to mark an email as spam.
  - `UCCollectionOrganizeEmails`: A collection of all use cases for the organize domain.
- parsing
  - `EmailParser`: An interface to parse Email of any format to a domain Email object.
  - `EmailParsingException`: A custom exception to handle parsing errors.
- provide
  - `AbstractProvideEmailsService`: Represents a service to provide emails and count of emails.
  - `AbstractProvideMailboxTypeEmailsService`: Represents a service to provide emails of a specific type.
  - `ProvideAllEmailsService`: Represents a service to provide all emails of a mailbox.
  - `ProvideAllInboxEmailsService`: Represents a service to provide all inbox emails of a mailbox.
  - `ProvideAllReadEmailsService`: Represents a service to provide all read emails of a mailbox.
  - `ProvideAllUnreadEmailsService`: Represents a service to provide all unread emails of a mailbox.
  - `ProvideDeletedEmailsService`: Represents a service to provide all deleted emails of a mailbox.
  - `ProvideEmailsUseCase`: A interface to handle the providing of emails and count of emails.
  - `ProvideInboxReadEmailsService`: Represents a service to provide all read inbox emails of a mailbox.
  - `ProvideInboxUnreadEmailsService`: Represents a service to provide all unread inbox emails of a mailbox.
  - `ProvideSentEmailsService`: Represents a service to provide all sent emails of a mailbox.
  - `ProvideSpamEmailsService`: Represents a service to provide all spam emails of a mailbox.
  - `UCCollectionProvideEmails`: A collection of all use cases for the provide domain.
- rules
  - `MailboxRule`: A interface to define a rule for a mailbox.
  - `SpamDetectionStrategy`: A interface to define a spam detection strategy.
- searchemail
  - content
  - `SearchContentEmailService`: Represents a service to search emails by content.
  - `SearchEmailUseCase`: A interface to handle the searching of emails.
  - date
  - `HelperParsing`: A helper class to parse a date string to a date object.
  - `SearchAfterDateEmailService`: Represents a service to search emails after a specific date.
  - `SearchBeforeDateEmailService`: Represents a service to search emails before a specific date.
  - `SearchEqualDateEmailService`: Represents a service to search emails with a specific date.
  - recipient
  - `SearchRecipientEmailService`: Represents a service to search emails by recipient.
  - sender
  - `SearchSenderEmailService`: Represents a service to search emails by sender.
  - subject
  - `SearchSubjectEmailService`: Represents a service to search emails by subject.
  - `UCCollectionSearchEmails`: A collection of all use cases for the search email domain.
- send
  - `EmailSendService`: Represents a service to send an email, prepare email for sending and validate Recipients.
  - `EmailSendUseCase`: A interface to handle the sending of an email and validate Recipients.
</details>


<details>
  <summary>imap</summary>

- `ImapListener`: A interface to listen, configure and handle incoming IMAP connections.
- `ListenerService`: A interface to listen to ports and handle incoming connections.
- `LocalListenerService`: Represents a service to listen to a local port and handle incoming connections.
</details>


<details>
  <summary>job</summary>

  - `Job`: A interface to define a job.
  - `JobExecutionException`: A custom exception to handle job execution errors.
  - `WorkerQueue`: Represents a queue to handle jobs.
</details>

<details>
  <summary>usersettings</summary>

- changepassword
  - `ChangePasswordService`: Represents a service to change the password of a user.
  - `ChangePasswordUseCase`: A interface to handle the changing of the password of a user.
- changesignature
  - `ChangeSignatureService`: Represents a service to change the signature of a user.
  - `ChangeSignatureUseCase`: A interface to handle the changing of the signature of a user.
  - `SettingsBuilder`: A builder to build settings.
- export
  - `ExportUseCase`: A interface to handle the exporting of emails.
  - `MailboxExportService`: Represents a service to export a mailbox.
- `UCCollectionSettings`: A collection of all use cases for the user settings domain.
</details>




### External Layer

#### Infrastructure 

<details>
  <summary>authorization</summary>

  - `FileUserRepository`: A implementation of the `UserRepository` interface to save and find users in a file.
  - `LocalPasswordChecker`: A implementation of the `PasswordChecker` interface to check the password of a user.
  - `LocalUserCreater`: A implementation of the `UserCreater` interface to create a user.
  - `PasswordHasher`: Represents a service to hash a password.
</details>

<details>
  <summary>email</summary>

- parsing
  - `FileMailboxRepository`: A implementation of the `MailboxRepository` interface to save and find mailboxes in a file.
  - `JSONMailboxConverter`: A implementation of the `MailboxConverter` interface to deserialize and serialize a mailbox.
  - `MailboxConverter`: A interface to deserialize and serialize a mailbox.
  - `ScheduledSendTimeParserService`: Represents a service to parse a scheduled send time.
  - `ScheduledSendTimeParserUseCase`: A interface to handle the parsing of a scheduled send time.
- `CreateEmailHelper`: A helper class to create emails.
- `EmailMetadataFactory`: A factory to create domain EmailMetadata objects by passing information of the external library jakarta.
- `EmlParser`: An implementation of the `EmailParser` interface to parse .eml files to domain Email objects.
</details>

<details>
  <summary>james</summary>

- parsing
- `JamesImapListener`: An implementation of the `ImapListener` interface to listen, configure and handle incoming IMAP connections with Apache James.
- `JamesImapProcessor`: An implementation of the `ImapProcessor` interface to process incoming IMAP connections with Apache James.
</details>


<details>
  <summary>mailbox_rules.spam</summary>

  - strategies
    - `ContentAnalysisSpamDetectionStrategy`: An implementation of the `SpamDetectionStrategy` interface to detect spam by analyzing the content of an email.
    - `ReputationAnalysisSpamDetectionStrategy`: An implementation of the `SpamDetectionStrategy` interface to detect spam by analyzing the reputation of an email.
    - `UnusualSenderSpamDetectionStrategy`: An implementation of the `SpamDetectionStrategy` interface to detect spam by analyzing the sender of an email.
  - `DetectSpamOnIncomingEmailService`: Represents a service to detect spam on incoming emails.
</details>

<details>
  <summary>preferences</summary>

  - `FileExportableMailboxRepository`: A implementation of the `ExportableMailboxRepository` interface to save mailboxes in a file.
  - `FileUserSettingsRepository`: A implementation of the `UserSettingsRepository` interface to update and get user settings in a file.
  - `LocalDateTimeAdapter`: A adapter for Gson to handle LocalDateTime objects during serialization and deserialization.
</details>

<details>
  <summary>utils</summary>

  - `FileHelper`: A helper class to handle file operations.
</details>





#### Presentation

<details>
  <summary>usecases</summary>

- delete
  - `DeleteEmailsCLIPrompt`: Represents a command line interface to delete emails.
  - `ShowDeleteEmailsCLIPrompt`: Represents a command line interface to show possible deletion emails.
- preferences
  - `ChangePasswordCLIPrompt`: Represents a command line interface to change the password of a user.
  - `ChangeSignatureCLIPrompt`: Represents a command line interface to change the signature of a user.
  - `ExportMailboxCLIPrompt`: Represents a command line interface to export a mailbox.
  - `PrintSignatureCLIPrompt`: Represents a command line interface to print the signature of a user.
  - `ResetSignatureCLIPrompt`: Represents a command line interface to reset the signature of a user.
  - `SelectMailboxTypeToExportCLIPrompt`: Represents a command line interface to select the mailbox type to export.
  - `SettingsCLIPrompt`: Represents a command line interface to handle user settings.
- searchemails
  - `SearchEmailsCLIPrompt`: Represents a command line interface to search emails.
  - `SearchEmailTypesCLIPrompt`: Represents a command line interface to select the type of search.
- showemails
  - `AnswerEmailCLIPrompt`: Represents a command line interface to answer an email.
  - `ReadEmailCLIPrompt`: Represents a command line interface to read an email.
  - `ReadEmailContentCLIPrompt`: Represents a command line interface to read the content of an email.
  - `ShowEmailsCLIPrompt`: Represents a command line interface to show emails.
  - `ShowEmailsTypesCLIPrompt`: Represents a command line interface to select the type of emails to show.
- `ComposeEmailCLIPrompt`: Represents a command line interface to compose an email.
- `LoginCLIPrompt`: Represents a command line interface to log in a user.
- `LogoutCLIPrompt`: Represents a command line interface to log out a user.
- `MarkEmailCLIPrompt`: Represents a command line interface to mark an email.
- `OrganizeEmailsCLIPrompt`: Represents a command line interface to organize emails.
- `RegisterCLIPrompt`: Represents a command line interface to register a user.


</details>

- `BaseCLIPrompt`: Represents a base command line interface that implements the Server interface and provides reusable functionality for other CLIPrompts
- `MainCLIPrompt`: Represents the main command line interface which is the CLI-Entrypoint to this application.

