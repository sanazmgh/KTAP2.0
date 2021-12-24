package apps.page.events;

public class NewTweetEvent {
    private final String tweetSTR;
    private final String tweetIMG;

    public NewTweetEvent(String tweetSTR, String tweetIMG )
    {
        this.tweetSTR = tweetSTR;
        this.tweetIMG = tweetIMG ;
    }

    public String getTweetSTR() {
        return tweetSTR;
    }

    public String getTweetIMG() {
        return tweetIMG;
    }
}
