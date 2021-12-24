package apps.page.timeline.tweetView.listeners;

import apps.page.profile.NewTweet.models.Tweet;
import apps.page.profile.NewTweet.view.NewTweetFXMLController;
import apps.page.profile.NewTweet.view.NewTweetPane;
import apps.page.timeline.commentsView.controller.CommentsController;
import apps.page.timeline.commentsView.view.ViewComments;
import apps.page.timeline.commentsView.view.ViewCommentsFXMLController;
import apps.page.timeline.tweetView.controller.ViewTweetController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import listeners.StringListener;
import utilities.Serialize;

public class ViewTweetStringListener {
    private final ViewTweetController controller = ViewTweetController.getViewTweetController();
    private final Serialize serialize = Serialize.getSerialize();
    private final Pane middlePane;
    private final ViewComments comments = new ViewComments();


    public ViewTweetStringListener(Pane middlePane)
    {
        this.middlePane = middlePane;
    }

    public void eventOccurred(String string , Tweet tweet)
    {
        if(string.equals("Like"))
            controller.likeTweet(tweet);

        if(string.equals("Retweet"))
            controller.retweet(tweet);

        if(string.equals("View comments"))
        {
            FXMLLoader loader = comments.getLoader();
            ViewCommentsFXMLController commentsFXML = loader.getController();
            commentsFXML.setListener(new StringListener() {
                @Override
                public void eventOccurred(String string) {
                    if(string.equals("Previous step"))
                    {
                        middlePane.getChildren().add(serialize.lastMove());
                    }
                }
            });
            commentsFXML.setController(new CommentsController(controller.getUser() , tweet));
            commentsFXML.setData(middlePane);

            middlePane.getChildren().clear();
            middlePane.getChildren().add(comments.getPane());
            serialize.addStep(comments , "Comments");
        }

        if(string.equals("Add comment"))
        {
            NewTweetPane newTweet = new NewTweetPane();
            FXMLLoader loader = newTweet.getLoader();
            NewTweetFXMLController FXMLNewTweetController = loader.getController();
            FXMLNewTweetController.setStringListener(new StringListener() {
                @Override
                public void eventOccurred(String string) {
                    if(string.equals("Back"))
                    {
                        middlePane.getChildren().add(serialize.lastMove());
                    }
                }
            });
            CommentingListener commentingListener = new CommentingListener();
            commentingListener.setTweet(tweet);
            FXMLNewTweetController.setTweetListener(commentingListener);

            middlePane.getChildren().clear();
            middlePane.getChildren().add(newTweet.getPane());
            serialize.addStep(comments , "Comments");
        }

        if(string.equals("Report"))
        {
            controller.report(tweet);
        }

        if(string.equals("Save"))
        {
            controller.save(tweet);
        }
    }

    public void eventOccurred(Tweet tweet, String names)
    {
        controller.forward(tweet , names);
    }
}
