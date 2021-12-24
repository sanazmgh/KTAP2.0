package apps.page.timeline.tweetView.view;

import apps.page.profile.NewTweet.models.Tweet;
import apps.page.timeline.tweetView.controller.ViewTweetController;
import apps.page.timeline.tweetView.listeners.ViewTweetStringListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class ViewTweetsFXMLController {
    private Tweet tweet;
    private final ViewTweetController controller = ViewTweetController.getViewTweetController();
    private ViewTweetStringListener listener;

    @FXML
    Label informationLabel;
    @FXML
    Label tweetTextLabel;
    @FXML
    Button likeButton;
    @FXML
    Button retweetButton;
    @FXML
    Button viewCommentsButton;
    @FXML
    Button submitButton;
    @FXML
    TextField forwardTextField;
    @FXML
    ImageView tweetImageView;

    public void setListener(ViewTweetStringListener viewTweetStringListener) {
        this.listener = viewTweetStringListener;
    }

    public void setData(Tweet tweet)
    {
        this.tweet = tweet;
        informationLabel.setText(controller.getTweetInfo(tweet));
        tweetTextLabel.setText(tweet.getTweetText());

        likeButton.setText("Like(" + controller.likeCounts(tweet) + ")");
        retweetButton.setText("Retweet(" + controller.retweetCounts(tweet) + ")");
        viewCommentsButton.setText("View comments(" + controller.commentCounts(tweet) + ")");

        if(!controller.getPic(tweet).equals("")) {
            try {
                tweetImageView.setImage(new Image(new File(controller.getPic(tweet)).toURI().toURL().toExternalForm()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public void like()
    {
        listener.eventOccurred("Like" , tweet);
        likeButton.setText("Like(" + controller.likeCounts(tweet) + ")");
    }

    public void retweet()
    {
        listener.eventOccurred("Retweet" , tweet);
        retweetButton.setText("Retweet(" + controller.retweetCounts(tweet) + ")");
    }

    public void save()
    {
        listener.eventOccurred("Save" , tweet);
    }

    public void forward()
    {
        forwardTextField.setVisible(true);
        submitButton.setVisible(true);
    }

    public void submit()
    {
        listener.eventOccurred(tweet , forwardTextField.getText());
        forwardTextField.setVisible(false);
        submitButton.setVisible(false);
    }

    public void viewComments()
    {
        listener.eventOccurred("View comments" , tweet);
    }

    public void addComment()
    {
        listener.eventOccurred("Add comment" , tweet);
    }

    public void report()
    {
        listener.eventOccurred("Report" , tweet);
    }
}
