package apps.page.timeline.commentsView.view;

import apps.page.profile.NewTweet.models.Tweet;
import apps.page.timeline.commentsView.controller.CommentsController;
import apps.page.timeline.profileView.controller.ProfileViewController;
import apps.page.timeline.profileView.listeners.PreViewListener;
import apps.page.timeline.profileView.view.PreViewFXMLController;
import apps.page.timeline.profileView.view.PreViewPane;
import apps.page.timeline.tweetView.listeners.ViewTweetStringListener;
import apps.page.timeline.tweetView.view.ViewTweetsFXMLController;
import apps.page.timeline.tweetView.view.ViewTweetsPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import listeners.StringListener;


public class ViewCommentsFXMLController{
    private CommentsController controller;
    private int lastShownComment;
    private Pane middlePane ;
    private StringListener listener;

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

    public void setData(Pane middlePane) {
        lastShownComment = controller.commentsSize();
        this.middlePane = middlePane;
        setTweets(lastShownComment);
    }

    public void setController(CommentsController controller) {
        this.controller = controller;
    }

    public void setListener(StringListener listener) {
        this.listener = listener;
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
        tweets[0] = controller.getComment(ind-1);
        tweets[1] = controller.getComment(ind-2);
        tweets[2] = controller.getComment(ind-3);

        for(int i=0 ; i<3 ; i++)
        {
            if (tweets[i] != null)
            {
                PreViewPane preView = new PreViewPane();
                FXMLLoader preViewLoader = preView.getLoader();
                PreViewFXMLController preViewFXMLController = preViewLoader.getController();
                ProfileViewController profileViewController = new ProfileViewController(controller.getUser() , controller.findUser(tweets[i].getUserId()));
                preViewFXMLController.setController(profileViewController);
                preViewFXMLController.setListener(new PreViewListener(profileViewController , middlePane));
                preViewFXMLController.setData();

                ViewTweetsPane viewTweet = new ViewTweetsPane();
                FXMLLoader viewTweetLoader = viewTweet.getLoader();
                ViewTweetsFXMLController viewTweetsFXMLController = viewTweetLoader.getController();
                viewTweetsFXMLController.setListener(new ViewTweetStringListener(middlePane));
                viewTweetsFXMLController.setData(tweets[i]);

                if(i==0)
                {
                    preView1Pane.getChildren().add(preView.getPane());
                    tweet1Pane.getChildren().add(viewTweet.getPane());
                }

                else if(i==1)
                {
                    preView2Pane.getChildren().add(preView.getPane());
                    tweet2Pane.getChildren().add(viewTweet.getPane());
                }

                else
                {
                    preView3Pane.getChildren().add(preView.getPane());
                    tweet3Pane.getChildren().add(viewTweet.getPane());
                }

                ind--;
            }
        }

        lastShownComment = ind;

        backButton.setDisable(!controller.backAvailable(lastShownComment));
        nextButton.setDisable(!controller.nextAvailable(lastShownComment));
    }

    public void back()
    {
        setTweets(controller.prevPageInd(lastShownComment));
    }

    public void next()
    {
        setTweets(lastShownComment);
    }

    public void prevStep()
    {
        listener.eventOccurred("Previous step");
    }
}
