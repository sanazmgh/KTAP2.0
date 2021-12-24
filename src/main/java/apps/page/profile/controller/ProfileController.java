package apps.page.profile.controller;

import apps.authentication.model.User;
import apps.page.profile.NewTweet.models.Tweet;
import db.Context;

public class ProfileController {
    public static ProfileController profileController;
    private final Context context = Context.getContext();
    private User user;

    private ProfileController() {}

    public static ProfileController getProfileController()
    {
        if(profileController == null)
            profileController = new ProfileController();

        return profileController;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Tweet getTweet(int id)
    {
        if(user.getProfile().getTweets().size() <= id || id<0)
            return null;

        return context.tweets.get(user.getProfile().getTweets().get(id));
    }

    public String getPic()
    {
        return user.getImagePath();
    }

    public boolean backAvailable(int ind)
    {
        return ind < user.getProfile().getTweets().size() - 3;
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
            if(user.getProfile().getTweets().size()%3 == 0) {
                return ind + 6;
            }

            else
                return ind + (user.getProfile().getTweets().size()%3) + 3;
        }
    }
}
