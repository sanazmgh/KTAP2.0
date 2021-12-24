package apps.page.messenger.models;

import models.Model;

import java.util.LinkedList;

public class List extends Model {
    private final String name;
    private final LinkedList<Integer> members = new LinkedList<>();

    public List(String name , int userId)
    {
        this.name = name;
        members.add(userId);
    }

    public String getName() {
        return name;
    }

    public LinkedList<Integer> getMembers() {
        return members;
    }
}
