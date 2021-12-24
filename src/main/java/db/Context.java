package db;

public class Context {

    public UserDB users;
    public TweetDB tweets;
    public NotificationDB notifications;
    public GroupsDB groups;
    public MessagesDB messagesDB;
    public static Context context;

    private Context()
    {
        this.users = new UserDB();
        this.tweets = new TweetDB();
        this.notifications = new NotificationDB();
        this.groups = new GroupsDB();
        this.messagesDB = new MessagesDB();
    }

    public static Context getContext()
    {
        if(context == null)
            context = new Context();

        return context;
    }
}
