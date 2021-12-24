package apps.authentication.model;

import models.Model;

import java.util.LinkedList;

public class Profile extends Model {

    private final LinkedList<Integer> tweets = new LinkedList<>(); //List of tweInteger
    private final LinkedList<Integer> requested = new LinkedList<>(); //List of Users ID
    private final LinkedList<Integer> followers = new LinkedList<>(); //List of Users ID
    private final LinkedList<Integer> followings = new LinkedList<>(); //List of Users ID
    private final LinkedList<Integer> blocked = new LinkedList<>(); //List of Users ID
    private final LinkedList<Integer> muted = new LinkedList<>(); //List of Users ID

    private final LinkedList<Integer> systemNotifications = new LinkedList<>();
    private final LinkedList<Integer> requestsNotifications = new LinkedList<>();

    public Profile () {}

    public LinkedList<Integer> getTweets() {
        return tweets;
    }

    public void setTweets(int tweet) {
        tweets.add(tweet);
    }

    public LinkedList<Integer> getRequested() {
        return requested;
    }

    public LinkedList<Integer> getFollowers() {
        return followers;
    }

    public LinkedList<Integer> getFollowings() {
        return followings;
    }

    public LinkedList<Integer> getBlocked() {
        return blocked;
    }

    public LinkedList<Integer> getMuted() {
        return muted;
    }

    public void setMuted(int muted) {
        this.muted.add(muted);
    }

    public LinkedList<Integer> getSystemNotifications() {
        return systemNotifications;
    }

    public void setSystemNotifications(Integer notification) {
        this.systemNotifications.add(notification);
    }

    public LinkedList<Integer> getRequestsNotifications() {
        return requestsNotifications;
    }

    public void setRequestsNotifications(Integer notification) {
        this.requestsNotifications.add(notification);
    }

}
