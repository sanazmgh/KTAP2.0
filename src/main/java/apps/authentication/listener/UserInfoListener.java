package apps.authentication.listener;

import apps.authentication.controller.AuthenticationController;
import apps.authentication.event.UserInfoFormEvent;

public class UserInfoListener {
    private final AuthenticationController controller ;

    public UserInfoListener(AuthenticationController controller)
    {
        this.controller = controller;
    }

    public int eventOccurred(UserInfoFormEvent formEvent ) {
            return controller.signUp(formEvent);
    }
}
