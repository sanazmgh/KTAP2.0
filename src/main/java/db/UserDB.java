package db;

import apps.authentication.model.User;
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

public class UserDB implements DBSet<User> {

    private static final File gsonFolder = new File(Config.getConfig("GsonDirectories").getProperty(String.class, "usersDirectory")); //TODO config directory
    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("MM dd, yyyy").setPrettyPrinting().create();
    static private final Logger logger = LogManager.getLogger(UserDB.class);

    @Override
    public User get(int id) {
        logger.warn("getting user : " + id + " from DB");
        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                if(file.getName().equals(Integer.toString(id))) {
                    FileReader fileReader = new FileReader(file.getCanonicalPath());
                    User user = gson.fromJson(fileReader, User.class);
                    fileReader.close();

                    if(!user.isDeleted())
                        return user;
                }
            }
        }

        catch (Exception e)
        {
            System.err.println("failed to load user by ID");
        }

        return null;
    }

    public User getByUsername(String username) {
        logger.warn("getting user : " + username + " from DB");

        try {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles())) {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                User user = gson.fromJson(fileReader, User.class);
                fileReader.close();
                if (user.getUsername().equals(username) && !user.isDeleted())
                    return user;
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  User getByEmail(String email) {
        logger.warn("getting user by email : " + email + " from DB");

        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                User user = gson.fromJson(fileReader, User.class);
                fileReader.close();

                if(user.getEmail().equals(email) && !user.isDeleted())
                    return user;
            }
        }

        catch (Exception e)
        {
            System.err.println("failed to load user by Email");
        }

        return null;
    }

    @Override
    public LinkedList<User> all() {
        logger.warn("getting all the users from DB : ");

        LinkedList<User> users = new LinkedList<>();

        for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
        {
            try {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                User user = gson.fromJson(fileReader, User.class);

                if(!user.isDeleted())
                    users.add(user);

                fileReader.close();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        return users;
    }

    @Override
    public void add(User user) {
        logger.warn("adding user : " + user.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + user.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(user , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            System.err.println("failed to update user");
        }
    }

    @Override
    public void remove(User user) {

    }

    @Override
    public void update(User user) {
        logger.warn("updating user: " + user.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + user.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(user , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            System.err.println("failed to update user");
        }
    }

    @Override
    public int lastID() {
        logger.warn("getting last ID of users from DB");

        int lastID = 0;

        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                User user = gson.fromJson(fileReader, User.class);
                fileReader.close();

                lastID = Math.max(lastID , user.getId());
            }
        }

        catch (Exception e)
        {
            System.err.println("failed to load Last ID");
        }

        return lastID;
    }


}
