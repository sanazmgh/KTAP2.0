package apps.page.timeline.commentsView.controller;

import apps.authentication.model.User;
import apps.page.profile.NewTweet.models.Tweet;
import db.Context;


public class CommentsController {
    private final User user;
    private final Tweet tweet;
    private final Context context = Context.getContext();

    public CommentsController(User user , Tweet tweet)
    {
        this.tweet = tweet;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int commentsSize()
    {
        return tweet.getComments().size();
    }

    public Tweet getComment(int ind)
    {
        if(tweet.getComments().size() <= ind || ind<0)
            return null;

        return context.tweets.get(tweet.getComments().get(ind));
    }

    public boolean backAvailable(int ind)
    {
        return ind < tweet.getComments().size() - 3;
    }

    public boolean nextAvailable(int ind)
    {
        return ind != 0;
    }

    public int prevPageInd(int ind)
    {
        if(ind != 0)
            return ind+6;

        else
        {
            if(tweet.getComments().size()%3 == 0) {
                return ind + 6;
            }

            else
                return ind + (tweet.getComments().size()%3) + 3;
        }
    }

    public User findUser(int id)
    {
        return context.users.get(id);
    }
}
