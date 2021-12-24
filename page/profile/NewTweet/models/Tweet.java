package apps.page.profile.NewTweet.models;

import apps.authentication.model.User;
import db.Context;
import models.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;

public class Tweet extends Model {
    /**
     * comments are counted as tweets
     */

    private final int userId;
    private int retweetFromUserId;
    private final String tweetText;
    private final String tweetImagePath;
    private final Date dateTweeted;
    private final boolean commented;
    private int noOfReports;
    private final static int REPORT = 10;
    private final Context context = Context.getContext();
    private final LinkedList<Integer> comments = new LinkedList<>(); //List of tweets ID
    private final LinkedList<Integer> likes = new LinkedList<>(); //List of Users ID
    private final LinkedList<Integer> retweets = new LinkedList<>(); //List of Users ID
    static private final Logger logger = LogManager.getLogger(Tweet.class);

    public Tweet(int userId , int retweetFromUserId, String tweetText, String tweetImagePath, boolean commented) {
        this.userId = userId;
        this.retweetFromUserId = retweetFromUserId;
        this.tweetText = tweetText;
        this.tweetImagePath = tweetImagePath;
        dateTweeted = new Date();
        this.commented = commented;
    }

    public int getUserId() {
        return userId;
    }

    public int getRetweetFromUserId() {
        return retweetFromUserId;
    }

    public void setRetweetFromUserId(int retweetFromUserID) {
        this.retweetFromUserId = retweetFromUserID;
    }

    public String getTweetText() {
        return tweetText;
    }

    public String getTweetImagePath() {
        if(tweetImagePath.equals(""))
            return tweetImagePath;

        File file = new File(tweetImagePath);
        if(!file.exists())
            return "";

        return tweetImagePath;
    }

    public Date getDateTweeted() {
        return dateTweeted;
    }

    public boolean isCommented() {
        return commented;
    }

    public LinkedList<Integer> getComments() {
        return comments;
    }

    public void setComments(int comment) {
        this.comments.add(comment);
    }

    public LinkedList<Integer> getLikes() {
        return likes;
    }

    public void setLikes(int likeUserID) {
        if(!likes.contains(likeUserID))
        {
            logger.info("user : " + userId + " liked tweet : " + this.getId());
            likes.add(likeUserID);
        }
    }

    public LinkedList<Integer> getRetweets() {
        return retweets;
    }

    public void addOnReports()
    {
        this.noOfReports++;
        logger.info("someone reported tweet : " + this.getId());

        if(this.noOfReports >= REPORT)
        {
            logger.info("reports of tweet : " + this.getId() + " was too much so it was deleted");
            User user = context.users.get(this.getUserId());
            user.getProfile().getTweets().remove((Integer) this.id);
            context.users.update(user);
        }

        context.tweets.update(this);
    }

}
