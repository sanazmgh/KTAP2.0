package apps.page.messenger.compose.controller;

import apps.authentication.model.User;
import apps.page.messenger.chatPane.message.model.Message;
import apps.page.messenger.compose.event.ComposeEvent;
import apps.page.messenger.models.Group;
import apps.page.messenger.models.List;
import apps.page.messenger.models.Messenger;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class ComposeController {
    private final User user;
    private final Messenger messenger;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(ComposeController.class);

    public ComposeController(User user)
    {
        this.user = user;
        this.messenger = user.getMessenger();
    }

    public void compose(ComposeEvent event)
    {
        logger.info("user " + user.getId() + " is composing a message");

        Message message = new Message(user.getId() , event.getText(), event.getAttachmentPath(), event.isForwarded(), event.getExtraInfo());

        LinkedList<Group> groups = new LinkedList<>();

        for(String name : event.getNames())
        {
            User currentUser = context.users.getByUsername(name);

            List currentList = user.getMessenger().getListByName(name);
            Group currentGroup = context.groups.getByName(name , user.getId());

            if(currentUser != null)
            {
                logger.debug("could find " + name + " as a user to compose, by user : " + user.getId());


                if(!currentUser.getProfile().getBlocked().contains(user.getId()) &&
                        !user.getProfile().getBlocked().contains(currentUser.getId()) &&
                        user.getProfile().getFollowings().contains(currentUser.getId()))
                {
                    if (currentGroup == null) {
                        logger.debug("creating a group with " + name + "to compose, by user : " + user.getId());


                        currentGroup = new Group(name, user.getId(), currentUser.getId());
                        currentGroup.setId(context.groups.lastID() + 1);

                        messenger.getGroups().add(currentGroup.getId());
                        currentUser.getMessenger().getGroups().add(currentGroup.getId());

                        context.groups.add(currentGroup);
                        context.users.update(user);
                        context.users.update(currentUser);
                    }

                    else
                    {
                        logger.debug("a private chat already existed with " + name + "to compose, by user : " + user.getId());
                    }

                    if (!groups.contains(currentGroup))
                        groups.add(currentGroup);
                }
            }

            else
            {
                logger.debug("couldn't find " + name + " as a user to compose, by user : " + user.getId());
            }

            if(currentList != null)
            {
                logger.debug("could find " + name + " as a list to compose, by user : " + user.getId());
                for(int i=0 ; i<currentList.getMembers().size() ; i++)
                {
                    User tempUser = context.users.get(currentList.getMembers().get(i));

                    if(tempUser != null)
                    {
                        Group tempGroup = context.groups.getByName(tempUser.getUsername() , user.getId());
                        if(tempGroup == null)
                        {
                            tempGroup = new Group(tempUser.getUsername() , user.getId() , tempUser.getId());
                            tempGroup.setId(context.groups.lastID()+1);

                            messenger.getGroups().add(tempGroup.getId());
                            tempUser.getMessenger().getGroups().add(tempGroup.getId());

                            context.groups.add(tempGroup);
                            context.users.update(user);
                            context.users.update(tempUser);
                        }

                        if(!groups.contains(tempGroup))
                            groups.add(tempGroup);
                    }
                }
            }

            else
            {
                logger.debug("couldn't find " + name + " as a list to compose, by user : " + user.getId());
            }

            if(currentGroup != null)
            {
                logger.debug("could find " + name + " as a group to compose, by user : " + user.getId());

                if(!groups.contains(currentGroup))
                    groups.add(currentGroup);
            }

            else
            {
                logger.debug("couldn't find " + name + " as a group to compose, by user : " + user.getId());
            }
        }

        for(Group group : groups)
        {
            group.newMessage(message);
            context.groups.update(group);
        }
    }
}
