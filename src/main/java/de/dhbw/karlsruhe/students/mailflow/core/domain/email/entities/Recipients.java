package de.dhbw.karlsruhe.students.mailflow.core.domain.email.entities;

import java.util.List;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.Entity;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.RecipientsId;

public class Recipients extends Entity<RecipientsId> {

    private List<Address> to;
    private List<Address> cc;
    private List<Address> bcc;

    private Recipients(RecipientsId id, List<Address> to, List<Address> cc, List<Address> bcc) {
        super(id);
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
    }

    public static Recipients create(List<Address> to, List<Address> cc, List<Address> bcc) {
        return new Recipients(RecipientsId.createUnique(), to, cc, bcc);
    }
}