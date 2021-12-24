package apps.page.profile.lists.controller;

import apps.authentication.model.User;

import java.util.LinkedList;

public class ListsController {
    User user ;

    public ListsController(User user)
    {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public LinkedList<Integer> getFollowers()
    {
        return user.getProfile().getFollowers();
    }

    public LinkedList<Integer> getFollowings()
    {
        return user.getProfile().getFollowings();
    }

    public LinkedList<Integer> getMuted()
    {
        return user.getProfile().getMuted();
    }

    public LinkedList<Integer> getBlocked()
    {
        return user.getProfile().getBlocked();
    }
}
