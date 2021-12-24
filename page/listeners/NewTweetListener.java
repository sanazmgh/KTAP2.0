package apps.page.listeners;

import apps.page.events.NewTweetEvent;
import apps.page.profile.NewTweet.controller.TweetingController;
import apps.page.profile.view.ProfileFXMLController;
import apps.page.profile.view.ProfilePane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class NewTweetListener {
    TweetingController controller = TweetingController.getTweetingController();
    Pane middlePane;
    ProfilePane profilePane;
    ProfileFXMLController profileFXML;

    public NewTweetListener() {}
    public NewTweetListener(Pane middlePane , ProfilePane profilePane)
    {
        this.middlePane = middlePane;
        this.profilePane = profilePane;
        FXMLLoader loader = profilePane.getLoader();
        this.profileFXML = loader.getController();
    }

    public void eventOccurred (NewTweetEvent newTweetEvent)
    {
        controller.newTweet(newTweetEvent);
        profileFXML.setData();
        /*
        middlePane.getChildren().clear();
        middlePane.getChildren().add(profilePane.getPane());
         */
    }
}
