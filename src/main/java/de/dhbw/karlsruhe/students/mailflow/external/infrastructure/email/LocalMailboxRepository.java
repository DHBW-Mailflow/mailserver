package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;
import java.util.Optional;

public class LocalMailboxRepository implements MailboxRepository {

  @Override
  public Optional<File> provideStoredMailboxFileFor(Address userAddress) {

    String localMBoxPath = userAddress.toString()+".mbox";
    File mboxFile = new File(localMBoxPath);

    if(!mboxFile.exists()){
      return Optional.empty();
    }
    //ggf. noch mehr logik
    return Optional.of(mboxFile);

  }
}
