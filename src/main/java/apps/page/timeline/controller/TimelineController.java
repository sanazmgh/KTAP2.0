package apps.page.timeline.controller;

import apps.authentication.model.User;
import apps.page.profile.NewTweet.models.Tweet;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.LinkedList;

public class TimelineController {
    private User user;
    private final Context context = Context.getContext();
    public static TimelineController timelineController;
    private final LinkedList<Integer> timeline = new LinkedList<>();
    private final LinkedList<Integer> explorer = new LinkedList<>();
    static private final Logger logger = LogManager.getLogger(TimelineController.class);

    private TimelineController(){}

    public static TimelineController getTimelineController()
    {
        if(timelineController == null)
            timelineController = new TimelineController();

        return timelineController;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int listSize(String type) {
        return (type.equals("Timeline") ? timeline.size() : explorer.size());
    }

    public void createTimeLine()
    {
        logger.warn("created timeline for : " + user.getId());
        timeline.clear();

        for(int i=0 ; i<user.getProfile().getFollowings().size() ; i++)
        {
            User currentUser = context.users.get(user.getProfile().getFollowings().get(i));

            if(!user.getProfile().getMuted().contains(currentUser.getId()) && currentUser.isActive())
                timeline.addAll(currentUser.getProfile().getTweets());
        }

        timeline.addAll(user.getProfile().getTweets());
        Collections.sort(timeline);
    }

    public void createExplorer()
    {
        logger.warn("created explorer for : " + user.getId());

        explorer.clear();

        for(User currentUser : context.users.all())
        {
            if(!user.getProfile().getFollowings().contains(currentUser.getId()) &&
                    !user.getProfile().getMuted().contains(currentUser.getId()) &&
                    currentUser.getId() != user.getId() && currentUser.isActive() &&
                    !user.getProfile().getBlocked().contains(currentUser.getId()) &&
                    !currentUser.isPrivate())
                explorer.addAll(currentUser.getProfile().getTweets());
        }

        Collections.sort(explorer);
    }

    public User search(String username)
    {
        return context.users.getByUsername(username);
    }

    public Tweet getTweet(int ind , String type)
    {
        if(type.equals("Timeline")) {
            if (timeline.size() <= ind || ind < 0)
                return null;

            return context.tweets.get(timeline.get(ind));
        }

        else{
            if (explorer.size() <= ind || ind < 0)
                return null;

            return context.tweets.get(explorer.get(ind));
        }
    }

    public boolean backAvailable(int ind , String type)
    {
        return ind < (type.equals("Timeline") ? timeline.size() : explorer.size()) - 3;
    }

    public boolean nextAvailable(int ind)
    {
        return ind != 0;
    }

    public int prevPageInd(int ind , String type)
    {
        if(ind != 0)
            return ind+6;

        else
        {
            if((type.equals("Timeline") ? timeline.size() : explorer.size())%3 == 0) {
                return ind + 6;
            }

            else
                return ind + ((type.equals("Timeline") ? timeline.size() : explorer.size())%3) + 3;
        }
    }

    public User findUser(int id)
    {
        return context.users.get(id);
    }
}
