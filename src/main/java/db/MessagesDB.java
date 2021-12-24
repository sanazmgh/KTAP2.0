package db;

import apps.page.messenger.chatPane.message.model.Message;
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

public class MessagesDB implements DBSet<Message>
{
    private static final File gsonFolder = new File(Config.getConfig("GsonDirectories").getProperty(String.class, "messagesDirectory")); //TODO config directory
    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("MM dd, yyyy").setPrettyPrinting().create();
    static private final Logger logger = LogManager.getLogger(MessagesDB.class);

    @Override
    public Message get(int id) {
        logger.warn("getting message : " + id + " from DB");

        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                if(file.getName().equals(Integer.toString(id))) {
                    FileReader fileReader = new FileReader(file.getCanonicalPath());
                    Message message = gson.fromJson(fileReader, Message.class);
                    fileReader.close();
                    return message;
                }
            }
        }

        catch (Exception e)
        {
            logger.error("failed to load Message by ID");
        }

        return null;
    }

    @Override
    public LinkedList<Message> all() {
        logger.warn("getting all the messages from DB : ");

        LinkedList<Message> messages = new LinkedList<>();

        for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
        {
            try {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                messages.add(gson.fromJson(fileReader, Message.class));
                fileReader.close();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        return messages;
    }

    @Override
    public void add(Message message) {
        logger.warn("adding message : " + message.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + message.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(message , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            logger.error("failed to add message");
        }
    }

    @Override
    public void remove(Message message) {

    }

    @Override
    public void update(Message message) {
        logger.warn("updating message : " + message.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + message.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(message , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            logger.error("failed to update message");
        }
    }

    @Override
    public int lastID() {
        logger.warn("getting last ID of messages from DB");

        int lastID = 0;

        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                Message message = gson.fromJson(fileReader, Message.class);
                fileReader.close();

                lastID = Math.max(lastID , message.getId());
            }
        }

        catch (Exception e)
        {
            System.err.println("failed to load Last ID");
        }

        return lastID;
    }
}
