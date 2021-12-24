package apps.page.timeline.profileView.view;

import apps.page.profile.NewTweet.models.Tweet;
import apps.page.timeline.profileView.controller.ProfileViewController;
import apps.page.timeline.profileView.listeners.ProfileListener;
import apps.page.timeline.tweetView.listeners.ViewTweetStringListener;
import apps.page.timeline.tweetView.view.ViewTweetsFXMLController;
import apps.page.timeline.tweetView.view.ViewTweetsPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.MalformedURLException;

public class ProfileViewFXMLController {
    private ProfileViewController controller;
    private ProfileListener listener;
    private int lastShownTweet;
    private Pane middlePane;

    @FXML
    Label nameLabel;
    @FXML
    Label usernameLabel;
    @FXML
    Label lastSeenLabel;
    @FXML
    Label bioLabel;
    @FXML
    Label informationLabel;
    @FXML
    Pane tweet1Pane;
    @FXML
    Pane tweet2Pane;
    @FXML
    Pane tweet3Pane;
    @FXML
    Button backButton;
    @FXML
    Button nextButton;
    @FXML
    Button statusButton;
    @FXML
    Button muteButton;
    @FXML
    Button blockButton;
    @FXML
    ImageView profileImageView;

    public void setController(ProfileViewController controller) {
        this.controller = controller;
    }

    public void setListener(ProfileListener listener) {
        this.listener = listener;
    }

    public void setMiddlePane(Pane middlePane) {
        this.middlePane = middlePane;
    }

    public void setData()
    {
        nameLabel.setText(controller.getName());
        usernameLabel.setText(controller.getUsername());
        lastSeenLabel.setText(controller.getLastSeen());
        bioLabel.setText(controller.getBio());
        informationLabel.setText(controller.getInformation());
        lastShownTweet = controller.ownerTweetSize();
        muteButton.setText(controller.getMuted());
        blockButton.setText(controller.getBlocked());
        statusButton.setText(controller.getStatus());

        try {
            profileImageView.setImage(new Image(new File(controller.getPic()).toURI().toURL().toExternalForm()));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(controller.visibleTweets())
            setTweets(lastShownTweet);
    }

    private void setTweets(int ind)
    {
        tweet1Pane.getChildren().clear();
        tweet2Pane.getChildren().clear();
        tweet3Pane.getChildren().clear();

        Tweet tweet1 = controller.getTweet(ind-1);
        Tweet tweet2 = controller.getTweet(ind-2);
        Tweet tweet3 = controller.getTweet(ind-3);

        if(tweet1 != null)
        {
            ind--;
            ViewTweetsPane tweetPane = new ViewTweetsPane();
            FXMLLoader loader = tweetPane.getLoader();
            ViewTweetsFXMLController FXMLController = loader.getController();
            FXMLController.setListener(new ViewTweetStringListener(middlePane));
            FXMLController.setData(tweet1);

            tweet1Pane.getChildren().add(tweetPane.getPane());
        }

        if(tweet2 != null)
        {
            ind--;
            ViewTweetsPane tweetPane = new ViewTweetsPane();
            FXMLLoader loader = tweetPane.getLoader();
            ViewTweetsFXMLController FXMLController = loader.getController();
            FXMLController.setListener(new ViewTweetStringListener(middlePane));
            FXMLController.setData(tweet2);
            tweet2Pane.getChildren().add(tweetPane.getPane());
        }

        if(tweet3 != null)
        {
            ind--;
            ViewTweetsPane tweetPane = new ViewTweetsPane();
            FXMLLoader loader = tweetPane.getLoader();
            ViewTweetsFXMLController FXMLController = loader.getController();
            FXMLController.setListener(new ViewTweetStringListener(middlePane));
            FXMLController.setData(tweet3);
            tweet3Pane.getChildren().add(tweetPane.getPane());
        }

        lastShownTweet = ind;

        backButton.setDisable(!controller.backAvailable(lastShownTweet));
        nextButton.setDisable(!controller.nextAvailable(lastShownTweet));
    }

    public void backPage()
    {
        setTweets(controller.prevPageInd(lastShownTweet));
    }

    public void nextPage()
    {
        setTweets(lastShownTweet);
    }

    public void prevStep()
    {
        listener.eventOccurred("Prev step");
    }

    public void block()
    {
        listener.eventOccurred("Block");
        setData();
    }

    public void mute()
    {
        listener.eventOccurred("Mute");
        setData();
    }

    public void changeStatus()
    {
        listener.eventOccurred("Change status");
        setData();
    }

}
