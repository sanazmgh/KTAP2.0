package apps.page.profile.listeners;

import apps.page.listeners.NewTweetListener;
import apps.page.profile.NewTweet.controller.TweetingController;
import apps.page.profile.NewTweet.view.NewTweetFXMLController;
import apps.page.profile.NewTweet.view.NewTweetPane;
import apps.page.profile.controller.ProfileController;
import apps.page.profile.edit.controller.EditController;
import apps.page.profile.edit.listener.ChangeInfoListener;
import apps.page.profile.edit.view.EditFXMLController;
import apps.page.profile.edit.view.EditPane;
import apps.page.profile.lists.controller.ListsController;
import apps.page.profile.lists.view.ListsFXMLController;
import apps.page.profile.lists.view.ListsPane;
import apps.page.profile.notifications.controller.NotificationController;
import apps.page.profile.notifications.view.NotificationFXMLController;
import apps.page.profile.notifications.view.NotificationPane;
import apps.page.profile.view.ProfileFXMLController;
import apps.page.profile.view.ProfilePane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import listeners.StringListener;

public class ProfileStringListener implements StringListener {

    ProfileController controller = ProfileController.getProfileController();
    Pane middlePane;
    ProfilePane profilePane;
    ProfileFXMLController profileFXML;
    NewTweetPane newTweetPane;
    NewTweetFXMLController newTweetFXML;
    EditPane editPane;
    EditFXMLController editFXML;
    NotificationPane notificationPane;
    NotificationFXMLController notificationFXML;
    ListsPane listsPane;
    ListsFXMLController listFXML;

    public ProfileStringListener (Pane middlePane , ProfilePane profilePane)
    {
        this.middlePane = middlePane;

        this.profilePane = profilePane;
        FXMLLoader profileLoader = profilePane.getLoader();
        profileFXML = profileLoader.getController();

        newTweetPane = new NewTweetPane();
        FXMLLoader newTweetFXMLLoader = newTweetPane.getLoader();
        newTweetFXML = newTweetFXMLLoader.getController();
        newTweetFXML.setTweetListener(new NewTweetListener(middlePane , profilePane));
        newTweetFXML.setStringListener(new StringListener() {
            @Override
            public void eventOccurred(String string) {
                if(string.equals("Back"))
                {
                    middlePane.getChildren().clear();
                    middlePane.getChildren().add(profilePane.getPane());
                }
            }
        });
        TweetingController.getTweetingController().setUser(controller.getUser());

        editPane = new EditPane();
        EditController.getEditController().setUser(controller.getUser());
        FXMLLoader editFXMLLoader = editPane.getLoader();
        editFXML = editFXMLLoader.getController();
        editFXML.setFormListener(new ChangeInfoListener());
        editFXML.setStringListener(new StringListener() {
            @Override
            public void eventOccurred(String string) {
                if(string.equals("Back"))
                {
                    middlePane.getChildren().clear();
                    middlePane.getChildren().add(profilePane.getPane());
                }

                if(string.equals("Enter"))
                {
                    profileFXML.setData();
                    middlePane.getChildren().clear();
                    middlePane.getChildren().add(profilePane.getPane());
                }
            }
        });

        notificationPane = new NotificationPane();
        FXMLLoader notificationLoader = notificationPane.getLoader();
        notificationFXML = notificationLoader.getController();
        notificationFXML.setController(new NotificationController(controller.getUser()));
        notificationFXML.setMiddlePane(middlePane);

        listsPane = new ListsPane();
        FXMLLoader listLoader = listsPane.getLoader();
        listFXML = listLoader.getController();
        listFXML.setController(new ListsController(controller.getUser()));
        listFXML.setMiddlePane(middlePane);
    }

    @Override
    public void eventOccurred(String string) {
        if(string.equals("New Tweet"))
        {
            middlePane.getChildren().clear();
            middlePane.getChildren().add(newTweetPane.getPane());
        }

        if(string.equals("Edit"))
        {
            middlePane.getChildren().clear();
            middlePane.getChildren().add(editPane.getPane());
        }

        if(string.equals("Notifications"))
        {
            middlePane.getChildren().clear();
            middlePane.getChildren().add(notificationPane.getPane());
        }

        if(string.equals("Lists"))
        {
            middlePane.getChildren().clear();
            middlePane.getChildren().add(listsPane.getPane());
        }
    }
}
