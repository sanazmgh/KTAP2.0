package apps.page.messenger.chatPane.controller;

import apps.authentication.model.User;
import apps.page.messenger.chatPane.message.model.Message;
import apps.page.messenger.models.Group;
import db.Context;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatController {
    private final User user;
    private final Group group;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(ChatController.class);

    public ChatController(User user , Group group)
    {
        this.user = user;
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public int getUnread()
    {
        return group.getUnread(user.getId());
    }

    public String getName()
    {
        return group.getName(user.getUsername());
    }

    public String getUserNames()
    {
        logger.info("creating group: " + group.getId() + " members");
        StringBuilder str = new StringBuilder();
        for(int i=0 ; i<group.getMembers().size() ; i++)
        {
            User currentUser = context.users.get(group.getMembers().get(i).getKey());
            str.append(currentUser.getName()).append(", ");
        }

        return str.substring(0 , str.length()-2);
    }

    public boolean canAddTo()
    {
        return !group.getSavedName().equals("Saved messages");
    }

    public boolean canText()
    {
        logger.info("check if texting is available in group: " + group.getId());

        if(group.getMembers().size() != 2)
            return true;

        User otherUser;

        if(group.getMembers().get(0).getKey() == user.getId())
            otherUser = context.users.get(group.getMembers().get(1).getKey());

        else
            otherUser = context.users.get(group.getMembers().get(0).getKey());

        return !otherUser.getProfile().getBlocked().contains(user.getId()) &&
                !user.getProfile().getBlocked().contains(otherUser.getId());
    }

    public int messageSize()
    {
        return group.getMessages().size();
    }

    public Message getMessage(int ind)
    {
        logger.info("getting messages in group: " + group.getId());

        if(ind<0  || ind>=group.getMessages().size())
            return null;

        return context.messagesDB.get(group.getMessages().get(ind));
    }

    public boolean backAvailable(int ind)
    {
        return ind < group.getMessages().size() - 5;
    }

    public boolean nextAvailable(int ind)
    {
        return ind != 0;
    }

    public int prevPageInd(int ind)
    {
        if(ind != 0)
            return ind+10;

        else
        {
            if(group.getMessages().size()%5 == 0) {
                return ind + 10;
            }

            else
                return ind + (group.getMessages().size()%5) + 5;
        }
    }

    public void addMember(String username)
    {
        User user = context.users.getByUsername(username);

        if(user != null)
        {
            logger.warn("adding user " + user.getId() + " to group " + group.getId());

            if(!group.contains(user.getId()))
            {
                group.getMembers().add(new Pair<>(user.getId(), 0));
                user.getMessenger().getGroups().add(group.getId());
            }
            context.users.update(user);
        }

        logger.warn("couldn't adding user " + username + " to group " + group.getId() + " : null user");

        context.groups.update(group);
    }

    public void newMessage(Message message)
    {
        group.newMessage(message);
    }

    public void seenMessages()
    {
        logger.info(user.getId() + " seen the messages in group : " + group.getId());
        group.removeNotif(user.getId());
    }
}
