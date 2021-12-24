package apps.page.settings.controller;

import apps.authentication.model.User;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SettingsController {
    private final User user;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(SettingsController.class);

    public SettingsController(User user)
    {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setPrivacy(boolean type)
    {
        logger.warn("user : " + user.getId() + " changed the privacy settings");
        user.setPrivate(type);
        context.users.update(user);

    }

    public void setLastSeen(int type)
    {
        logger.warn("user : " + user.getId() + " changed the last seen settings");
        user.setLastSeenMode(type);
        context.users.update(user);
    }

    public int deleteAccount(String pass)
    {
        if(pass.equals(user.getPassword()))
        {
            logger.warn("user : " + user.getId() + " deleted his/her account");

            user.setDeleted(true);
            user.setActive(false);
            user.setUsername("DeletedAccount");
            user.setEmail("DeletedAccount");
            user.setName("Deleted");
            user.setUsername("Account");

            for(int i=0 ; i<user.getProfile().getFollowers().size() ; i++)
            {
                User currentUser = context.users.get(user.getProfile().getFollowers().get(i));
                currentUser.getProfile().getFollowings().remove((Integer)user.getId());
                context.users.update(currentUser);
            }

            for(int i=0 ; i<user.getProfile().getFollowings().size() ; i++)
            {
                User currentUser = context.users.get(user.getProfile().getFollowings().get(i));
                currentUser.getProfile().getFollowers().remove((Integer)user.getId());
                context.users.update(currentUser);
            }

            context.users.update(user);
            return 0;
        }


        logger.warn("user : " + user.getId() + " entered wrong password for deleting his/her account");
        return 1;
    }

    public int deactiveAccount(String pass)
    {
        if(pass.equals(user.getPassword()))
        {
            logger.warn("user : " + user.getId() + " deactivated his/her account");
            user.setActive(false);
            context.users.update(user);

            return 0;
        }

        logger.warn("user : " + user.getId() + " entered wrong password for deactivating his/her account");
        return 1;
    }
}
