package de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature;

public interface ChangeSignatureUseCase {
    void updateSignature(String newSignature);

    void removeSignature();
}
