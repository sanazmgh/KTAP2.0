package apps.page.messenger.models;

import apps.authentication.model.User;
import apps.page.messenger.chatPane.message.model.Message;
import db.Context;
import javafx.util.Pair;
import models.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class Group extends Model {
    private final String name;
    private final LinkedList<Pair<Integer , Integer>> members = new LinkedList<>(); //List of <userId , noOfUnread>
    private final LinkedList<Integer> messages = new LinkedList<>();
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(Group.class);

    public Group (String name , int user1Id , int user2Id)
    {
        this.name = name;
        members.add(new Pair<>(user1Id , 0));
        members.add(new Pair<>(user2Id , 0));
    }

    public Group(String name, int userId)
    {
        this.name = name;
        members.add(new Pair<>(userId , 0));
    }

    public String getName(String receiverUsername)
    {
        if(members.size() == 2)
        {
            User user1 = context.users.get(members.get(0).getKey());
            User user2 = context.users.get(members.get(1).getKey());

            return (receiverUsername.equals(user1.getUsername()) ? user2.getUsername() : user1.getUsername());
        }
        return name;
    }

    public String getSavedName()
    {
        return name;
    }

    public LinkedList<Pair<Integer, Integer>> getMembers() {
        return members;
    }

    public LinkedList<Integer> getMessages() {
        return messages;
    }

    public int getUnread(int id)
    {
        for (Pair<Integer, Integer> member : members)
            if (member.getKey() == id)
                return member.getValue();

        return 0;
    }

    public boolean contains(int Id)
    {
        for(Pair <Integer , Integer> pair : members )
        {
            if(pair.getKey() == Id)
                return true;
        }

        return false;
    }

    public void newMessage(Message message)
    {
        logger.info("New message : " + message.getId() + " added to group : " + this.getId());

        for(int i=0 ; i<members.size() ; i++)
        {
            Pair<Integer, Integer> pair = members.get(i);

            if(pair.getKey() != message.getSenderId())
            {
                members.set(i, new Pair<>(pair.getKey(), pair.getValue()+1));
            }
        }
        messages.add(message.getId());

        if(!name.equals("Saved messages"))
            context.groups.update(this);

        else
        {
            User user = context.users.get(message.getSenderId());
            user.getMessenger().getSavedMessages().messages.add(message.getId());
            context.users.update(user);
        }
        context.messagesDB.add(message);
    }

    public void removeNotif(int userId)
    {
        for(int i=0 ; i<members.size() ; i++)
        {
            Pair<Integer, Integer> pair = members.get(i);

            if(pair.getKey() == userId)
            {
                members.set(i, new Pair<>(pair.getKey(), 0));
            }
        }

        if(!name.equals("Saved messages"))
            context.groups.update(this);
    }
}
