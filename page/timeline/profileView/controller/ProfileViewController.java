package apps.page.timeline.profileView.controller;

import apps.authentication.model.User;
import apps.page.profile.NewTweet.models.Tweet;
import apps.page.profile.notifications.model.Notification;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfileViewController {
    private final User user;
    private final User owner;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(ProfileViewController.class);


    public ProfileViewController(User user , User owner)
    {
        this.user = user;
        this.owner = owner;
    }

    public User getUser() {
        return user;
    }

    public User getOwner() {
        return owner;
    }

    public String getName()
    {
        return owner.getName() + " " + owner.getLastName();
    }

    public String getUsername()
    {
        return owner.getUsername();
    }

    public String getLastSeen()
    {
        if(owner.getLastSeenMode() == 0)
            return "Last seen recently";

        if((owner.getLastSeenMode() == 2 && owner.getProfile().getFollowers().contains(user.getId())) || owner.getLastSeenMode() == 1)
            return (owner.getLastSeen() == null ? "Last seen recently" : owner.getLastSeen().toString());

        return "Last seen recently";
    }

    public String getBio()
    {
        return owner.getBio();
    }

    public String getInformation()
    {
        String informationStr = "";

        if (!owner.getEmail().equals("") && owner.isVisibleEmail())
            informationStr += owner.getEmail();

        if (!owner.getPhone().equals("") && owner.isVisiblePhone())
            informationStr += " . " + owner.getPhone();

        if (owner.getDateOfBirth() != null && owner.isVisibleDateOfBirth()) {
            informationStr += " . " +  owner.getDateOfBirth();
        }

        return informationStr;
    }

    public String getStatus()
    {
        if(owner.getProfile().getFollowers().contains(user.getId()))
            return "Following";

        if(owner.getProfile().getRequested().contains(user.getId()))
            return "Requested";

        return "Follow";
    }

    public String getMuted()
    {
        if(user.getProfile().getMuted().contains(owner.getId()))
            return "Unmute";

        return "Mute";
    }

    public String getBlocked()
    {
        if(user.getProfile().getBlocked().contains(owner.getId()))
            return "Unblock";

        return "Block";
    }

    public String getPic()
    {
        return owner.getImagePath();
    }

    public boolean visibleTweets()
    {
        if(owner.getProfile().getBlocked().contains(user.getId()))
            return false;

        return !owner.isPrivate() || owner.getProfile().getFollowers().contains(user.getId());
    }

    public void changeStatus()
    {
        if(owner.getProfile().getFollowers().contains(user.getId()))
        {
            owner.getProfile().getFollowers().remove((Integer) user.getId());
            user.getProfile().getFollowings().remove((Integer)owner.getId());

            Notification notification = Notification.newUnfollow(user.getId());
            notification.setId(context.notifications.lastID()+1);
            logger.warn("Notification : " + notification.getId() + " was created");

            owner.getProfile().getSystemNotifications().add(notification.getId());

            context.notifications.add(notification);
        }

        else if(!owner.getProfile().getRequested().contains(user.getId()))
        {
            if(owner.isPrivate())
            {
                owner.getProfile().getRequested().add(user.getId());
                Notification notification = Notification.newRequest(user.getId());
                notification.setId(context.notifications.lastID()+1);
                owner.getProfile().getRequestsNotifications().add(notification.getId());

                logger.warn("Notification : " + notification.getId() + " was created");
                context.notifications.add(notification);
            }

            else
            {
                owner.getProfile().getFollowers().add(user.getId());
                user.getProfile().getFollowings().add(owner.getId());

                int lastId = context.notifications.lastID();
                Notification ownerNotification = Notification.newFollower(user.getId());
                Notification userNotification = Notification.newFollowing(owner.getId());
                ownerNotification.setId(lastId + 1);
                userNotification.setId(lastId + 2);

                owner.getProfile().getSystemNotifications().add(ownerNotification.getId());
                user.getProfile().getSystemNotifications().add(userNotification.getId());

                logger.warn("Notification : " + userNotification.getId() + " was created");
                logger.warn("Notification : " + ownerNotification.getId() + " was created");

                context.notifications.add(ownerNotification);
                context.notifications.add(userNotification);
            }

        }

        context.users.update(owner);
        context.users.update(user);
    }

    public Tweet getTweet(int id)
    {
        if(owner.getProfile().getTweets().size() <= id || id<0)
            return null;

        return context.tweets.get(owner.getProfile().getTweets().get(id));
    }

    public int ownerTweetSize()
    {
        return owner.getProfile().getTweets().size();
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

    public void changeBlockOwner()
    {
        logger.info("user : " + user.getId() + " wants to change the block status of user : " + owner.getId());

        if(user.getProfile().getBlocked().contains(owner.getId()))
            user.getProfile().getBlocked().remove((Integer)owner.getId());

        else
        {
            user.getProfile().getBlocked().add(owner.getId());

            user.getProfile().getFollowers().remove((Integer)owner.getId());
            user.getProfile().getFollowings().remove((Integer)owner.getId());

            owner.getProfile().getFollowings().remove((Integer)user.getId());
            owner.getProfile().getFollowers().remove((Integer)user.getId());
        }

        context.users.update(user);
    }

    public void changeMuteOwner()
    {
        logger.info("user : " + user.getId() + " wants to change the mute status of user : " + owner.getId());

        if(user.getProfile().getMuted().contains(owner.getId()))
            user.getProfile().getMuted().remove((Integer) owner.getId());

        else
            user.getProfile().getMuted().add(owner.getId());

        context.users.update(user);
    }
}
