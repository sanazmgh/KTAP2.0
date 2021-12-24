package apps.page.timeline.tweetView.controller;

import apps.authentication.model.User;
import apps.page.events.NewTweetEvent;
import apps.page.messenger.chatPane.message.model.Message;
import apps.page.messenger.compose.controller.ComposeController;
import apps.page.messenger.compose.event.ComposeEvent;
import apps.page.profile.NewTweet.models.Tweet;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewTweetController {

    private static ViewTweetController viewTweetController;
    private User user;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(ViewTweetController.class);

    private ViewTweetController() {}

    public static ViewTweetController getViewTweetController() {
        if(viewTweetController == null)
            viewTweetController = new ViewTweetController();

        return viewTweetController;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTweetInfo(Tweet tweet)
    {
        String str = "";

        if(tweet.getRetweetFromUserId() != 0)
        {
            User user = context.users.get(tweet.getRetweetFromUserId());
            str += "retweeted from @" + user.getUsername() ;
        }

        else if(tweet.isCommented())
            str += " commented";

        else
            str += " tweeted";

        str += " at : " + tweet.getDateTweeted();

        return str;
    }

    public String getPic(Tweet tweet)
    {
        return tweet.getTweetImagePath();
    }

    public String likeCounts(Tweet tweet)
    {
        return Integer.toString(tweet.getLikes().size());
    }

    public String retweetCounts(Tweet tweet)
    {
        return Integer.toString(tweet.getRetweets().size());
    }

    public String commentCounts(Tweet tweet){
        return Integer.toString(tweet.getComments().size());
    }

    public void likeTweet(Tweet tweet)
    {
        if(!tweet.getLikes().contains(user.getId()))
            tweet.getLikes().add(user.getId());

        logger.info("user : " + user.getId() + " liked : " + tweet.getId());
        context.tweets.update(tweet);
    }

    public void retweet(Tweet tweet)
    {
        if(!tweet.getRetweets().contains(user.getId()))
        {
            Tweet retweet = new Tweet(user.getId(), tweet.getUserId(), tweet.getTweetText() , tweet.getTweetImagePath() , false);
            retweet.setId(context.tweets.lastID()+1);
            user.getProfile().getTweets().add(retweet.getId());
            tweet.getRetweets().add(user.getId());

            logger.info("user : " + user.getId() + " retweeted : " + retweet.getId());
            context.users.update(user);
            context.tweets.add(retweet);
            context.tweets.update(tweet);
        }
    }

    public void report(Tweet tweet)
    {
        tweet.addOnReports();
    }

    public void newComment(NewTweetEvent tweetEvent , Tweet tweet)
    {
        Tweet comment = new Tweet(user.getId(), 0 , tweetEvent.getTweetSTR() , tweetEvent.getTweetIMG() , true);
        comment.setId(context.tweets.lastID()+1);
        user.getProfile().getTweets().add(comment.getId());
        tweet.getComments().add(comment.getId());

        logger.info("user : " + user.getId() + " add a comment : " + comment.getId() + " under tweet : "  + tweet.getId());
        context.tweets.add(comment);
        context.users.update(user);
        context.tweets.update(tweet);
    }

    public void save(Tweet tweet)
    {
        logger.info("user : " + user.getId() + " saved tweet : " + tweet.getId() );
        User currentUser = context.users.get(tweet.getUserId());
        Message message = new Message(user.getId(), tweet.getTweetText(), tweet.getTweetImagePath(), false, currentUser.getUsername());
        user.getMessenger().getSavedMessages().newMessage(message);
        context.users.update(user);
    }

    public void forward(Tweet tweet , String names)
    {
        logger.info("user : " + user.getId() + " wants to forward tweet : " + tweet.getId() + " to : " + names);
        User currentUser = context.users.get(tweet.getUserId());
        ComposeEvent composeEvent = new ComposeEvent(tweet.getTweetText() , tweet.getTweetImagePath(), names, false , currentUser.getUsername());
        ComposeController composeController = new ComposeController(user);
        composeController.compose(composeEvent);
    }
}

