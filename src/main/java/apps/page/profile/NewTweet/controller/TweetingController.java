package apps.page.profile.NewTweet.controller;

import apps.authentication.model.User;
import apps.page.events.NewTweetEvent;
import apps.page.profile.NewTweet.models.Tweet;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TweetingController {

    private final Context context = Context.getContext();
    public static TweetingController tweetingController;
    private User user;
    static private final Logger logger = LogManager.getLogger(TweetingController.class);

    private TweetingController () {}

    public static TweetingController getTweetingController()
    {
        if(tweetingController == null)
            tweetingController = new TweetingController();

        return tweetingController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void newTweet(NewTweetEvent tweetEvent)
    {
        Tweet tweet = new Tweet(user.getId() , 0 , tweetEvent.getTweetSTR() , tweetEvent.getTweetIMG() , false);
        tweet.setId(context.tweets.lastID()+1);

        logger.warn("a new tweet : " + tweet.getId() + " bu user : " + user.getId());

        context.tweets.add(tweet);
        user.getProfile().setTweets(tweet.getId());
        context.users.update(user);
    }
}
