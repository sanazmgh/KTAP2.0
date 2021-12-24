package apps.page.messenger.lists.controller;

import apps.authentication.model.User;
import apps.page.messenger.models.List;

public class ViewListsController {

    private final User user;

    public User getUser() {
        return user;
    }

    public ViewListsController(User user)
    {
        this.user = user;
    }

    public int listSize()
    {
        return user.getMessenger().getLists().size();
    }

    public List getList(int ind)
    {
        if(ind<0 || ind>=user.getMessenger().getLists().size())
            return null;

        return user.getMessenger().getLists().get(ind);
    }

    public boolean backAvailable(int ind)
    {
        return ind < user.getMessenger().getLists().size() - 5;
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
            if(user.getMessenger().getLists().size()%5 == 0) {
                return ind + 10;
            }

            else
                return ind + (user.getMessenger().getLists().size()%5) + 5;
        }
    }
}
