package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

public class LogoutCLIPrompt extends AbstractCLIPrompt {


    //TODO remove from LoginUseCase session
    @Override
    public void start() {
        //   System.out.printf("Good bye, %s!", getSessionUser().email());
        System.out.println("Good bye!");
        System.exit(0);
    }

}
