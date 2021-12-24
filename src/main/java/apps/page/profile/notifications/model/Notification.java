package apps.page.profile.notifications.model;

import models.Model;

public class Notification extends Model {

    private final int senderID;
    private final String text;


    public Notification (int senderID, String text)
    {
        this.senderID = senderID;
        this.text = text;
    }

    public int getSenderID() {
        return senderID;
    }

    public String getText() {
        return text;
    }

    public static Notification newRequest(int senderID)
    {
        return new Notification(senderID , "This user requested to follow you.");
    }

    public static Notification newFollowing(int senderID)
    {
        return new Notification(senderID , "You started following this user.");
    }

    public static Notification newDenied(int senderID)
    {
        return new Notification(senderID , "This user denied your follow request.");
    }

    public static Notification newFollower(int senderID)
    {
        return new Notification(senderID , "This user stated following you.");
    }

    public static Notification newUnfollow(int senderID)
    {
        return new Notification(senderID , "This user stopped following you");
    }
}
