package apps.page.timeline.tweetView.listeners;

import apps.page.events.NewTweetEvent;
import apps.page.listeners.NewTweetListener;
import apps.page.profile.NewTweet.models.Tweet;
import apps.page.timeline.tweetView.controller.ViewTweetController;

public class CommentingListener extends NewTweetListener {
    private final ViewTweetController controller = ViewTweetController.getViewTweetController();
    private Tweet tweet;

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    @Override
    public void eventOccurred(NewTweetEvent newTweetEvent) {
        controller.newComment(newTweetEvent , tweet);
    }
}
