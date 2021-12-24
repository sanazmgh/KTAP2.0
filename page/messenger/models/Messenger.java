package apps.page.messenger.models;

import apps.authentication.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class Messenger {
    private final int userId;
    private final LinkedList<Integer> groups = new LinkedList<>();
    private final LinkedList<List> lists = new LinkedList<>();
    private final Group savedMessages;
    static private final Logger logger = LogManager.getLogger(Messenger.class);

    public Messenger(User user)
    {
        this.userId = user.getId();
        this.savedMessages = new Group("Saved messages" , userId);
        //savedMessages.getMembers().add(new Pair<>(this.userId , 0));
    }

    public int getUserId() {
        return userId;
    }

    public LinkedList<Integer> getGroups() {
        return groups;
    }

    public LinkedList<List> getLists() {
        return lists;
    }

    public Group getSavedMessages() {
        return savedMessages;
    }

    public List getListByName(String name)
    {
        logger.warn("searching for list : " + name + " in lists of user : " + userId);
        for(List list : lists)
        {
            if(list.getName().equals(name))
            {
                logger.debug("found the list : " + name + " in lists of user : " + userId);
                return list;
            }
        }

        logger.debug("couldn't find the list : " + name + " in lists of user : " + userId);
        return null;
    }
}
