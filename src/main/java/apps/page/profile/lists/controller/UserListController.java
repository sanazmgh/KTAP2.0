package apps.page.profile.lists.controller;

import apps.authentication.model.User;
import db.Context;

import java.util.LinkedList;

public class UserListController {
    private final User user;
    private final LinkedList<Integer> list ;
    private final Context context = Context.getContext();

    public UserListController(User user , LinkedList<Integer> list)
    {
        this.user = user;
        this.list = list;
    }

    public User getUser() {
        return user;
    }


    public int listSize()
    {
        return list.size();
    }

    public User getUserList(int ind)
    {
        if(ind < 0 || ind >= list.size())
            return null;

        return context.users.get(list.get(ind));
    }

    public boolean backAvailable(int ind)
    {
        return ind < list.size() - 5;
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
            if(list.size()%5 == 0) {
                return ind + 10;
            }

            else
                return ind + (list.size()%5) + 5;
        }
    }

}
