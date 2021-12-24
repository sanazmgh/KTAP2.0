package db;

import apps.authentication.model.User;
import apps.page.messenger.models.Group;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.Config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class GroupsDB implements DBSet<Group> {

    private static final File gsonFolder = new File(Config.getConfig("GsonDirectories").getProperty(String.class, "groupsDirectory")); //TODO config directory
    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("MM dd, yyyy").setPrettyPrinting().create();
    static private final Logger logger = LogManager.getLogger(GroupsDB.class);

    @Override
    public Group get(int id) {
        logger.warn("getting group : " + id + " from DB");
        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                if(file.getName().equals(Integer.toString(id))) {
                    FileReader fileReader = new FileReader(file.getCanonicalPath());
                    Group group = gson.fromJson(fileReader, Group.class);
                    fileReader.close();

                    return group;
                }
            }
        }

        catch (Exception e)
        {
            logger.error("failed to load group by ID");
        }

        return null;
    }

    public Group getByName(String name , int userId)
    {
        logger.warn("getting group : " + name + " from DB");

        try {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles())) {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                Group group = gson.fromJson(fileReader, Group.class);

                if(group.getMembers().size() == 2)
                {
                    User user1 = Context.getContext().users.get(group.getMembers().get(0).getKey());
                    User user2 = Context.getContext().users.get(group.getMembers().get(1).getKey());

                    if((user1.getUsername().equals(name) || user2.getUsername().equals(name)) && group.contains(userId))
                    {
                        fileReader.close();
                        return group;
                    }
                }

                fileReader.close();
                if (group.getSavedName().equals(name) && group.contains(userId))
                    return group;
            }
        }

        catch (IOException e) {
            logger.error("failed to load group by name");
        }
        return null;
    }


    @Override
    public LinkedList<Group> all() {
        logger.warn("getting all the groups from DB : ");

        LinkedList<Group> groups = new LinkedList<>();

        for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
        {
            try {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                Group group = gson.fromJson(fileReader, Group.class);

                if(!group.isDeleted())
                    groups.add(group);

                fileReader.close();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        return groups;
    }

    @Override
    public void add(Group group) {
        logger.warn("adding group : " + group.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + group.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(group , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            logger.error("failed to add group");
        }
    }

    @Override
    public void remove(Group group) {

    }

    @Override
    public void update(Group group) {
        logger.warn("updating group : " + group.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + group.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(group , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            logger.error("failed to update group");
        }
    }

    @Override
    public int lastID() {
        logger.warn("getting last ID of groups from DB");
        int lastID = 0;

        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                Group group = gson.fromJson(fileReader, Group.class);
                fileReader.close();

                lastID = Math.max(lastID , group.getId());
            }
        }

        catch (Exception e)
        {
            logger.error("failed to load lastID");
        }

        return lastID;
    }
}
