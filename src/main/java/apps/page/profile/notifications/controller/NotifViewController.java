package apps.page.profile.notifications.controller;

import apps.authentication.model.User;
import apps.page.profile.notifications.model.Notification;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotifViewController {
    private final Notification notification;
    private final User user;
    private final int type;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(NotifViewController.class);


    public NotifViewController (Notification notification , int type , User user)
    {
        this.notification = notification;
        this.type = type;
        this.user = user;
    }

    public String getText()
    {
        return notification.getText();
    }

    public int getType() {
        return type;
    }

    public void accept()
    {
        logger.info("user : " + user.getId() + " accepted the notification : " + notification.getId());

        User sender = context.users.get(notification.getSenderID());
        int lastID = context.notifications.lastID();

        Notification senderNotification = Notification.newFollowing(user.getId());
        senderNotification.setId(lastID+1);
        logger.warn("Notification : " + notification.getId() + " was created");

        Notification receiverNotification = Notification.newFollower(sender.getId());
        receiverNotification.setId(lastID+2);
        logger.warn("Notification : " + notification.getId() + " was created");


        user.getProfile().getRequestsNotifications().remove((Integer)notification.getId());
        user.getProfile().getRequested().remove((Integer)sender.getId()); //TODO
        user.getProfile().getSystemNotifications().add(receiverNotification.getId());
        sender.getProfile().getSystemNotifications().add(senderNotification.getId());

        user.getProfile().getFollowers().add(sender.getId());
        sender.getProfile().getFollowings().add(user.getId());

        context.users.update(user);
        context.users.update(sender);

        context.notifications.add(senderNotification);
        context.notifications.add(receiverNotification);
    }


    public void deny(boolean notify)
    {
        User sender = context.users.get(notification.getSenderID());
        user.getProfile().getRequestsNotifications().remove((Integer)notification.getId());
        user.getProfile().getRequested().remove((Integer)sender.getId()); //TODO


        if(notify)
        {
            logger.info("user : " + user.getId() + " denied the notification : " + notification.getId() + " with notify");

            Notification senderNotification = Notification.newDenied(user.getId());
            senderNotification.setId(context.notifications.lastID()+1);
            sender.getProfile().getSystemNotifications().add(senderNotification.getId());

            logger.warn("Notification : " + notification.getId() + " was created");
            context.notifications.add(senderNotification);
        }

        else
        {
            logger.info("user : " + user.getId() + " denied the notification : " + notification.getId() + " without notify");
        }

        context.users.update(sender);
        context.users.update(user);
    }
}
