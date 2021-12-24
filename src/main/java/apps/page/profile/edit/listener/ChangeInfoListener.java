package apps.page.profile.edit.listener;

import apps.authentication.event.UserInfoFormEvent;
import apps.page.profile.edit.controller.EditController;

public class ChangeInfoListener {

    EditController controller = EditController.getEditController();

    public int eventOccurred(UserInfoFormEvent userInfoFormEvent)
    {
        return controller.changeInfo(userInfoFormEvent);
    }
}
