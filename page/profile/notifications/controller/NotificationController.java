package apps.page.profile.notifications.controller;

import apps.authentication.model.User;
import apps.page.profile.notifications.model.Notification;
import db.Context;

public class NotificationController {

    private final User user;
    private final Context context = Context.getContext();

    public User getUser() {
        return user;
    }

    public NotificationController(User user)
    {
        this.user = user;
    }

    public User getSenderUser(Notification notification)
    {
        return context.users.get(notification.getSenderID());
    }

    public int requestsSize()
    {
        return user.getProfile().getRequestsNotifications().size();
    }

    public int systemNotificationsSize()
    {
        return user.getProfile().getSystemNotifications().size();
    }

    public Notification getNotif(int ind , int type)
    {
        if(ind < 0)
            return null;

        if(type == 0)
        {
            if(ind >= user.getProfile().getRequestsNotifications().size())
                return null;

            else
                return context.notifications.get(user.getProfile().getRequestsNotifications().get(ind));
        }

        else
        {
            if(ind >= user.getProfile().getSystemNotifications().size())
                return null;

            else
                return context.notifications.get(user.getProfile().getSystemNotifications().get(ind));
        }
    }

    public boolean backAvailable(int ind , int type)
    {
        if(type == 0)
            return ind < user.getProfile().getRequestsNotifications().size() - 4;

        else
            return ind < user.getProfile().getSystemNotifications().size() - 4;
    }

    public boolean nextAvailable(int ind)
    {
        return ind != 0;
    }

    public int prevPageInd(int ind , int type)
    {
        if(ind != 0)
            return ind+8;

        else
        {
            int length;
            if(type == 0)
                length = user.getProfile().getRequestsNotifications().size();

            else
                length = user.getProfile().getSystemNotifications().size();

            if(length%4 == 0) {
                return ind + 8;
            }

            else
                return ind + (length%4) + 4;
        }
    }
}
