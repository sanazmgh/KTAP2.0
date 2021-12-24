package apps.page.profile.edit.controller;

import apps.authentication.event.UserInfoFormEvent;
import apps.authentication.model.User;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditController {

    private static EditController editController;
    private User user;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(EditController.class);

    private EditController() {}

    public static EditController getEditController()
    {
        if(editController == null)
            editController = new EditController();

        return editController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int changeInfo(UserInfoFormEvent formEvent)
    {
        int valid = validChange(formEvent);

        if(valid != 0)
        {
            logger.warn("invalid change in edit section by user : " + user.getId());
            return valid;
        }

        logger.warn("a change in edit section by user : " + user.getId());
        setData(formEvent);
        return 0;
    }

    public int validChange(UserInfoFormEvent formEvent)
    {
        if(context.users.getByUsername(formEvent.getUsername()) != null && !formEvent.getUsername().equals(user.getUsername()))
            return 1;

        if(!formEvent.getPass().equals(formEvent.getPass2()))
            return 2;

        if(context.users.getByEmail(formEvent.getEmail()) != null && !formEvent.getEmail().equals(user.getEmail()))
            return 3;

        if(formEvent.getUsername().equals("") || formEvent.getPass().equals("") || formEvent.getPass2().equals("") ||
                formEvent.getName().equals("") || formEvent.getLastName().equals("") || formEvent.getEmail().equals(""))
            return 4;

        return 0;
    }

    public void setData(UserInfoFormEvent formEvent)
    {
        user.setUsername(formEvent.getUsername());
        user.setPassword(formEvent.getPass());
        user.setName(formEvent.getName());
        user.setLastName(formEvent.getLastName());
        user.setBio(formEvent.getBio());
        user.setDateOfBirth(formEvent.getDateOfBirth());
        user.setVisibleDateOfBirth(formEvent.isVisibleDate());
        user.setPhone(formEvent.getPhone());
        user.setVisiblePhone(formEvent.isVisiblePhone());
        user.setEmail(formEvent.getEmail());
        user.setVisibleEmail(formEvent.isVisibleEmail());
        user.setImagePath(formEvent.getPicPath());

        context.users.update(user);
    }

    public String getPic()
    {
        return user.getImagePath();
    }
}
