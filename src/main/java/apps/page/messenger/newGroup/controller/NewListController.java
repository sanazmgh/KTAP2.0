package apps.page.messenger.newGroup.controller;

import apps.authentication.model.User;
import apps.page.messenger.models.Group;
import apps.page.messenger.models.List;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewListController {
    private final User user;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(NewListController.class);


    public NewListController(User user)
    {
        this.user = user;
    }

    public void newList(String listName , String username)
    {
        logger.warn("creating a new list with name : " + listName + " and user : " + username + " bu user : " + user.getId());
        User addedUser = context.users.getByUsername(username);

        if(addedUser != null)
        {
            if(user.getProfile().getFollowings().contains(addedUser.getId()))
            {
                logger.debug("created the list : " + listName + " by user : " + user.getId());
                List list = new List(listName, addedUser.getId());
                user.getMessenger().getLists().add(list);
                context.users.update(user);
            }
        }
    }

    public void newGroup(String gpName , String username)
    {
        logger.warn("creating a new group with name : " + gpName + " and user : " + username + " by user : " + user.getId());

        User addedUser = context.users.getByUsername(username);

        if(addedUser != null)
        {
            if(user.getProfile().getFollowings().contains(addedUser.getId()))
            {
                Group group = new Group(gpName, addedUser.getId(), user.getId());
                group.setId(context.groups.lastID() + 1);
                user.getMessenger().getGroups().add(group.getId());
                addedUser.getMessenger().getGroups().add(group.getId());

                logger.debug("created the group : " + group.getId() + " by user : " + user.getId());

                context.users.update(addedUser);
                context.users.update(user);
                context.groups.add(group);
            }
        }
    }
}
