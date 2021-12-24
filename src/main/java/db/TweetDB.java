package db;

import apps.page.profile.NewTweet.models.Tweet;
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

public class TweetDB implements DBSet<Tweet>{

    private static final File gsonFolder = new File(Config.getConfig("GsonDirectories").getProperty(String.class, "tweetsDirectory")); //TODO config directory
    private static final Gson gson = new GsonBuilder().serializeNulls().setDateFormat("MM dd, yyyy").setPrettyPrinting().create();
    static private final Logger logger = LogManager.getLogger(TweetDB.class);

    @Override
    public Tweet get(int id) {
        logger.warn("getting tweet : " + id + " from DB");

        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                if(file.getName().equals(Integer.toString(id))) {
                    FileReader fileReader = new FileReader(file.getCanonicalPath());
                    Tweet tweet = gson.fromJson(fileReader, Tweet.class);
                    fileReader.close();
                    return tweet;
                }
            }
        }

        catch (Exception e)
        {
            System.err.println("failed to load Tweet by ID");
        }

        return null;
    }

    @Override
    public LinkedList<Tweet> all() {
        logger.warn("getting all the tweets from DB : ");
        LinkedList<Tweet> tweets = new LinkedList<>();

        for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
        {
            try {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                tweets.add(gson.fromJson(fileReader, Tweet.class));
                fileReader.close();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        return tweets;
    }

    @Override
    public void add(Tweet tweet) {
        logger.warn("adding tweet : " + tweet.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + tweet.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(tweet , fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }

        catch (Exception e)
        {
            System.err.println("failed to update user");
        }
    }

    @Override
    public void remove(Tweet tweet) {

    }

    @Override
    public void update(Tweet tweet) {
        logger.warn("updating tweet : " + tweet.getId() + " to DB");

        try {
            File file = new File(gsonFolder.getPath() + "/" + tweet.getId());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(tweet , fileWriter);
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
        logger.warn("getting last ID of tweets from DB");

        int lastID = 0;

        try
        {
            for (File file : Objects.requireNonNull(gsonFolder.listFiles()))
            {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                Tweet tweet = gson.fromJson(fileReader, Tweet.class);
                fileReader.close();

                lastID = Math.max(lastID , tweet.getId());
            }
        }

        catch (Exception e)
        {
            System.err.println("failed to load Last ID");
        }

        return lastID;
    }
}
