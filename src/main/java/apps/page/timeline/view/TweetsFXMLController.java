package apps.page.timeline.view;

import apps.authentication.model.User;
import apps.page.profile.NewTweet.models.Tweet;
import apps.page.timeline.profileView.controller.ProfileViewController;
import apps.page.timeline.profileView.listeners.PreViewListener;
import apps.page.timeline.profileView.view.PreViewFXMLController;
import apps.page.timeline.profileView.view.PreViewPane;
import apps.page.timeline.tweetView.listeners.ViewTweetStringListener;
import apps.page.timeline.tweetView.view.ViewTweetsFXMLController;
import apps.page.timeline.tweetView.view.ViewTweetsPane;
import apps.page.timeline.controller.TimelineController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;


public class TweetsFXMLController{
    private final TimelineController controller = TimelineController.getTimelineController();
    private int lastShownTweet;
    private Pane middlePane;
    private String type;

    @FXML
    Pane preView1Pane;
    @FXML
    Pane preView2Pane;
    @FXML
    Pane preView3Pane;
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

    public void setData(Pane middlePane , String type)
    {
        this.middlePane = middlePane;
        this.type = type;
        controller.createTimeLine();
        controller.createExplorer();
        lastShownTweet = controller.listSize(type);

        setTweets(lastShownTweet);
    }

    private void setTweets(int ind)
    {
        preView1Pane.getChildren().clear();
        preView2Pane.getChildren().clear();
        preView3Pane.getChildren().clear();

        tweet1Pane.getChildren().clear();
        tweet2Pane.getChildren().clear();
        tweet3Pane.getChildren().clear();

        Tweet[] tweets = new Tweet[4];
        tweets[0] = controller.getTweet(ind-1 , type);
        tweets[1] = controller.getTweet(ind-2 , type);
        tweets[2] = controller.getTweet(ind-3 , type);

        for(int i=0 ; i<3 ; i++)
        {
            if (tweets[i] != null)
            {
                User currentUser = controller.findUser(tweets[i].getUserId());

                if(currentUser.isActive())
                {
                    PreViewPane preView = new PreViewPane();
                    FXMLLoader preViewLoader = preView.getLoader();
                    PreViewFXMLController preViewFXMLController = preViewLoader.getController();
                    ProfileViewController profileViewController = new ProfileViewController(controller.getUser(), currentUser);
                    preViewFXMLController.setController(profileViewController);
                    preViewFXMLController.setListener(new PreViewListener(profileViewController, middlePane));
                    preViewFXMLController.setData();

                    ViewTweetsPane viewTweet = new ViewTweetsPane();
                    FXMLLoader viewTweetLoader = viewTweet.getLoader();
                    ViewTweetsFXMLController viewTweetsFXMLController = viewTweetLoader.getController();
                    viewTweetsFXMLController.setData(tweets[i]);
                    viewTweetsFXMLController.setListener(new ViewTweetStringListener(middlePane));

                    if (i == 0) {
                        preView1Pane.getChildren().add(preView.getPane());
                        tweet1Pane.getChildren().add(viewTweet.getPane());
                    } else if (i == 1) {
                        preView2Pane.getChildren().add(preView.getPane());
                        tweet2Pane.getChildren().add(viewTweet.getPane());
                    } else {
                        preView3Pane.getChildren().add(preView.getPane());
                        tweet3Pane.getChildren().add(viewTweet.getPane());
                    }

                    ind--;
                }
            }
        }

        lastShownTweet = ind;

        backButton.setDisable(!controller.backAvailable(lastShownTweet , type));
        nextButton.setDisable(!controller.nextAvailable(lastShownTweet));
    }

    public void back()
    {
        setTweets(controller.prevPageInd(lastShownTweet , type));
    }

    public void next()
    {
        setTweets(lastShownTweet);
    }
}
