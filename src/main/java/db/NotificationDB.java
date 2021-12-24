package db;

import apps.page.profile.notifications.model.Notification;
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

public class NotificationDB implements DBSet<Notification> {

    private static final File gsonFolder = new File(Config.getConfig("GsonDirectories").getProperty(String.class, "notificationsDirectory")); //TODO config directory
    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("MM dd, yyyy").setPrettyPrinting().create();
    static private final Logger logger = LogManager.getLogger(NotificationDB.class);


    @Override
    public Notification get(int id) {
        logger.warn("getting notification : " + id + " from DB");

        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                if(file.getName().equals(Integer.toString(id))) {
                    FileReader fileReader = new FileReader(file.getCanonicalPath());
                    Notification notification = gson.fromJson(fileReader, Notification.class);
                    fileReader.close();
                    return notification;
                }
            }
        }

        catch (Exception e)
        {
            System.err.println("failed to load Notification by ID");
        }

        return null;
    }

    @Override
    public LinkedList<Notification> all() {
        logger.warn("getting all the notifications from DB : ");

        LinkedList<Notification> notifications = new LinkedList<>();

        for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
        {
            try {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                notifications.add(gson.fromJson(fileReader, Notification.class));
                fileReader.close();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        return notifications;
    }

    @Override
    public void add(Notification notification) {
        logger.warn("adding notification : " + notification.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + notification.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(notification , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            System.err.println("failed to update user");
        }
    }

    @Override
    public void remove(Notification notification) {

    }

    @Override
    public void update(Notification notification) {
        logger.warn("updating notification : " + notification.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + notification.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(notification , fileWriter);
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
        logger.warn("getting last ID of notifications from DB");

        int lastID = 0;

        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                Notification notification = gson.fromJson(fileReader, Notification.class);
                fileReader.close();

                lastID = Math.max(lastID , notification.getId());
            }
        }

        catch (Exception e)
        {
            System.err.println("failed to load Last ID");
        }

        return lastID;
    }
}
