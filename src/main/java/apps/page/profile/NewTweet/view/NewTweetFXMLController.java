package apps.page.profile.NewTweet.view;

import apps.page.events.NewTweetEvent;
import apps.page.listeners.NewTweetListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import listeners.StringListener;


public class NewTweetFXMLController {
    @FXML
    private TextField tweetingTextField;
    @FXML
    private TextField imageTextField;

    private NewTweetListener tweetListener;
    private StringListener stringListener;

    public void setTweetListener(NewTweetListener tweetListener) {
        this.tweetListener = tweetListener;
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void submit()
    {
        NewTweetEvent tweet = new NewTweetEvent(tweetingTextField.getText() , imageTextField.getText() );
        tweetListener.eventOccurred(tweet);
        back();
    }

    public void back()
    {
        stringListener.eventOccurred("Back");
    }
}
