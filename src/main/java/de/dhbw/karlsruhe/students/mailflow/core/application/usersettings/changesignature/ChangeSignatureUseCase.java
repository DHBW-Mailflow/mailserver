package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

import java.io.FileNotFoundException;

public interface ChangeSignatureUseCase {
    void updateSignature(String newSignature) throws FileNotFoundException;

    void removeSignature();
}
