package apps.page.messenger.controller;

import apps.authentication.model.User;
import apps.page.messenger.models.Group;
import apps.page.messenger.models.Messenger;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class MessengerController {
    private final User user;
    private final Messenger messenger;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(MessengerController.class);

    public MessengerController(User user)
    {
        this.user = user;
        this.messenger = user.getMessenger();
    }

    public User getUser() {
        return user;
    }

    public int chatSize()
    {
        return user.getMessenger().getGroups().size();
    }

    public Group getGroup(int ind)
    {
        if(ind<0 || ind >= user.getMessenger().getGroups().size())
            return null;

        return context.groups.get(user.getMessenger().getGroups().get(ind));
    }

    public void rearrangeChats()
    {
        logger.info("arrange the chats of user : " + user.getId());
        LinkedList<Integer> newArrange = new LinkedList<>();

        for(int i=0 ; i<messenger.getGroups().size() ; i++)
        {
            Group group = context.groups.get(messenger.getGroups().get(i));

            if(group.getUnread(user.getId()) > 0)
                newArrange.addFirst(group.getId());

            else
                newArrange.addLast(group.getId());
        }

        messenger.getGroups().clear();
        messenger.getGroups().addAll(newArrange);
    }


    public boolean backAvailable(int ind)
    {
        return ind < messenger.getGroups().size() - 10;
    }

    public boolean nextAvailable(int ind)
    {
        return ind != 0;
    }

    public int prevPageInd(int ind)
    {
        if(ind != 0)
            return ind+20;

        else
        {
            if(messenger.getGroups().size()%10 == 0) {
                return ind + 20;
            }

            else
                return ind + (messenger.getGroups().size()%10) + 10;
        }
    }

}
