package apps.page.messenger.lists.controller;

import apps.authentication.model.User;
import apps.page.messenger.models.List;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ListsController {
    private final User user;
    private final List list;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(ListsController.class);

    public ListsController(User user , List list)
    {
        this.user = user;
        this.list = list;
    }

    public void add(String username)
    {
        User addedUser = context.users.getByUsername(username);

        if(addedUser != null)
        {
            logger.warn("user : " + addedUser.getId() + " added to list : " + list.getName() + " by user : " + user.getId());

            list.getMembers().add(addedUser.getId());

            context.users.update(user);
        }

        else
        {
            logger.warn("user : " + username + " couldn't be added to list : " + list.getName() + " by user : " + user.getId() + " : null user");
        }
    }

    public void remove(String username)
    {
        User removedUser = context.users.getByUsername(username);

        if(removedUser != null)
        {
            logger.warn("user : " + removedUser.getId() + " removed from list : " + list.getName() + " by user : " + user.getId());

            list.getMembers().remove((Integer)removedUser.getId());

            context.users.update(user);
        }

        else
        {
            logger.warn("user : " + username + " couldn't be removed from list : " + list.getName() + " by user : " + user.getId() + " : null user");
        }
    }

    public String getName()
    {
        return list.getName();
    }

    public String getMembers()
    {
        StringBuilder str = new StringBuilder();
        for(int i=0 ; i<list.getMembers().size() ; i++)
        {
            User currentUser = context.users.get(list.getMembers().get(i));
            str.append(currentUser.getName()).append(", ");
        }

        if(!str.toString().equals(""))
            return str.substring(0 , str.length()-2);

        return str.toString();
    }
}
