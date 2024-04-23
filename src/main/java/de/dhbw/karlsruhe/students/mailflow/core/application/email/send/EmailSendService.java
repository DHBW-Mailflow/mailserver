package de.dhbw.karlsruhe.students.mailflow.core.application.email.send;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services.DeliverInSentService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services.DeliverUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.MailboxRule;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.EmailBuilder;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.InvalidRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author jens1o
 */
public class EmailSendService implements EmailSendUseCase {
  private final AuthSessionUseCase authSession;
  private final MailboxRepository mailboxRepository;
  private final MailboxRule spamDetector;
  private List<Address> bccAddresses;
  private List<Address> ccAddresses;
  private List<Address> toAddresses;
  private String message;
  private Subject subject;

  public EmailSendService(
      AuthSessionUseCase authSession,
      MailboxRepository mailboxRepository,
      MailboxRule spamDetector) {
    this.authSession = authSession;
    this.mailboxRepository = mailboxRepository;
    this.spamDetector = spamDetector;
  }

  private List<Address> collectRecipientAddresses(Email email) {
    return Stream.of(email.getRecipientTo(), email.getRecipientCC(), email.getRecipientBCC())
        .flatMap(Collection::stream)
        .toList();
  }

  private List<Address> getAddressesFromPromptResponse(String promptResponse)
      throws InvalidRecipients {
    ArrayList<Address> result = new ArrayList<>();

    for (String addressCandidate : promptResponse.split(",")) {
      addressCandidate = addressCandidate.trim();

      if (addressCandidate.isEmpty()) {
        continue;
      }
      try {
        Address.from(addressCandidate);
        result.add(Address.from(addressCandidate));
      } catch (IllegalArgumentException e) {
        throw new InvalidRecipients("Could not parse address: " + addressCandidate);
      }
    }

    return result;
  }

  @Override
  public void sendPreparedEmail()
      throws MailboxLoadingException, MailboxSavingException, InvalidRecipients {
    validateRecipients();
    Email email =
        new EmailBuilder()
            .withSender(authSession.getSessionUserAddress())
            .withSubject(subject)
            .withRecipientsTo(toAddresses)
            .withRecipientsBCC(bccAddresses)
            .withRecipientsCC(ccAddresses)
            .withContent(message)
            .build();

    saveToSenderMailbox(email);

    // strip the BCC recipients to uphold the guarantee that they stay hidden
    List<Address> addresses = collectRecipientAddresses(email);
    email = email.withoutBCCRecipients();

    sendToRecipients(addresses, email);
  }

  private void sendToRecipients(List<Address> recipients, Email email)
      throws MailboxLoadingException, MailboxSavingException {
    // put the email into the INBOX folder of the respective recipient
    DeliverUseCase deliverUseCase = spamDetector.runOnEmail(email);
    for (Address recipient : recipients) {
      deliverUseCase.deliverEmailTo(recipient, email);
    }
  }

  private void saveToSenderMailbox(Email email)
      throws MailboxLoadingException, MailboxSavingException {
    DeliverUseCase deliverUseCase = new DeliverInSentService(mailboxRepository);
    deliverUseCase.deliverEmailTo(email.getSender(), email);
  }

  @Override
  public void validateRecipients() throws InvalidRecipients {
    if (toAddresses.isEmpty() && ccAddresses.isEmpty() && bccAddresses.isEmpty()) {
      throw new InvalidRecipients("At least one recipient must be declared");
    }
  }

  @Override
  public void setToRecipients(String toRecipientsString) throws InvalidRecipients {
    this.toAddresses = getAddressesFromPromptResponse(toRecipientsString);
  }

  @Override
  public void setCCRecipients(String ccRecipientsString) throws InvalidRecipients {
    this.ccAddresses = getAddressesFromPromptResponse(ccRecipientsString);
  }

  @Override
  public void setBCCRecipients(String bccRecipientsString) throws InvalidRecipients {
    this.bccAddresses = getAddressesFromPromptResponse(bccRecipientsString);
  }

  @Override
  public void setSubject(String subject) {
    this.subject = new Subject(subject);
  }

  @Override
  public void setMessage(String message) {
    this.message = message;
  }
}
