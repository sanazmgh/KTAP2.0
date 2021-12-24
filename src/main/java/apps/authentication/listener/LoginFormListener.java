package apps.authentication.listener;

import apps.authentication.controller.AuthenticationController;
import apps.authentication.event.LoginFormEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginFormListener {
    private final AuthenticationController controller;

    public LoginFormListener (AuthenticationController controller)
    {
        this.controller = controller;
    }

    public boolean eventOccurred(LoginFormEvent formEvent) {
        return controller.login(formEvent);
    }
}
