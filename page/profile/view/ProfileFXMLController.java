package apps.page.profile.view;

import apps.page.profile.NewTweet.models.Tweet;
import apps.page.profile.controller.ProfileController;
import apps.page.timeline.tweetView.controller.ViewTweetController;
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
import listeners.StringListener;

import java.io.File;
import java.net.MalformedURLException;

public class ProfileFXMLController {
    private StringListener listener;
    private int LastShownTweetInd = 0;
    private final ProfileController controller = ProfileController.getProfileController();
    private Pane mainPane;

    @FXML
    Label nameLabel;

    @FXML
    Label usernameLabel;

    @FXML
    Label bioLabel;

    @FXML
    Label informationLabel;

    @FXML
    ImageView profileImageView;

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

    public void setListener(StringListener listener) {
        this.listener = listener;
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

    public void setData()
    {
        nameLabel.setText(controller.getUser().getName() + " " + controller.getUser().getLastName());
        usernameLabel.setText("@" + controller.getUser().getUsername());

        bioLabel.setText(controller.getUser().getBio());

        String informationStr = "";

        if (!controller.getUser().getEmail().equals(""))
            informationStr += controller.getUser().getEmail();

        if (!controller.getUser().getPassword().equals(""))
            informationStr += " . " + controller.getUser().getPhone();

        if (controller.getUser().getDateOfBirth() != null) {
            informationStr += " . " +  controller.getUser().getDateOfBirth();
        }

        informationLabel.setText(informationStr);

        try {
            profileImageView.setImage(new Image(new File(controller.getPic()).toURI().toURL().toExternalForm()));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        LastShownTweetInd = controller.getUser().getProfile().getTweets().size();
        ViewTweetController.getViewTweetController().setUser(controller.getUser());
        setTweets(LastShownTweetInd);
    }

    private void setTweets(int ind)
    {
        tweet1Pane.getChildren().clear();
        tweet2Pane.getChildren().clear();
        tweet3Pane.getChildren().clear();

        Tweet tweet1 =  controller.getTweet(ind-1);
        Tweet tweet2 = controller.getTweet(ind-2);
        Tweet tweet3 = controller.getTweet(ind-3);

        if(tweet1 != null)
        {
            ind--;
            ViewTweetsPane tweetPane = new ViewTweetsPane();
            FXMLLoader loader = tweetPane.getLoader();
            ViewTweetsFXMLController FXMLController = loader.getController();
            FXMLController.setListener(new ViewTweetStringListener(mainPane));
            FXMLController.setData(tweet1);

            tweet1Pane.getChildren().add(tweetPane.getPane());
        }

        if(tweet2 != null)
        {
            ind--;
            ViewTweetsPane tweetPane = new ViewTweetsPane();
            FXMLLoader loader = tweetPane.getLoader();
            ViewTweetsFXMLController FXMLController = loader.getController();
            FXMLController.setListener(new ViewTweetStringListener(mainPane));
            FXMLController.setData(tweet2);
            tweet2Pane.getChildren().add(tweetPane.getPane());
        }

        if(tweet3 != null)
        {
            ind--;
            ViewTweetsPane tweetPane = new ViewTweetsPane();
            FXMLLoader loader = tweetPane.getLoader();
            ViewTweetsFXMLController FXMLController = loader.getController();
            FXMLController.setListener(new ViewTweetStringListener(mainPane));
            FXMLController.setData(tweet3);
            tweet3Pane.getChildren().add(tweetPane.getPane());
        }

        LastShownTweetInd = ind;

        backButton.setDisable(!controller.backAvailable(LastShownTweetInd));
        nextButton.setDisable(!controller.nextAvailable(LastShownTweetInd));
    }

    public void backPage()
    {
        setTweets(controller.prevPageInd(LastShownTweetInd));
    }

    public void nextPage()
    {
        setTweets(LastShownTweetInd);
    }

    public void newTweet()
    {
        listener.eventOccurred("New Tweet");
    }

    public void edit()
    {
        listener.eventOccurred("Edit");
    }

    public void lists()
    {
        listener.eventOccurred("Lists");
    }

    public void notifications()
    {
        listener.eventOccurred("Notifications");
    }

}
