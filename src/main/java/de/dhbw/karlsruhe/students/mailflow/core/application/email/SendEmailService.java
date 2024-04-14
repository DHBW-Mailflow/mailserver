package de.dhbw.karlsruhe.students.mailflow.core.application.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.*;

import java.util.Arrays;
import java.util.List;

public class SendEmailService implements SendEmailUseCase {
    @Override
    public Email prepareEmail(Address sender, String subject, String content, String recipientTo, String recipientsCC, String recipientsBCC) {
        List<Address> addressesTo = Arrays.stream(recipientTo.split(",")).map(Address::from).toList();
        List<Address> addressesCC = Arrays.stream(recipientsCC.split(",")).map(Address::from).toList();
        List<Address> addressesBCC = Arrays.stream(recipientsBCC.split(",")).map(Address::from).toList();

        Recipients recipients = new Recipients(addressesTo, addressesCC, addressesBCC);


        EmailMetadata metadata = new EmailMetadata(new Subject(subject), sender, List.of(), recipients, SentDate.ofNow());

        return Email.create(content, metadata);
    }

    //TODO merge previous PR
    @Override
    public void send(Email email) {
        //  email.getEmailMetadata()
    }
}
