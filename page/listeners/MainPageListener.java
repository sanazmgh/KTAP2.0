package apps.page.listeners;

import apps.authentication.model.User;
import apps.page.messenger.controller.MessengerController;
import apps.page.messenger.listener.MessengerListener;
import apps.page.messenger.view.MessengerFXMLController;
import apps.page.messenger.view.MessengerPane;
import apps.page.profile.controller.ProfileController;
import apps.page.profile.listeners.ProfileStringListener;
import apps.page.profile.view.ProfileFXMLController;
import apps.page.profile.view.ProfilePane;
import apps.page.settings.controller.SettingsController;
import apps.page.settings.listeners.SettingsListener;
import apps.page.settings.view.SettingsFXMLController;
import apps.page.settings.view.SettingsPane;
import apps.page.timeline.Listeners.SearchListener;
import apps.page.timeline.controller.TimelineController;
import apps.page.timeline.view.TimeLineFXMLController;
import apps.page.timeline.view.TimelinePane;
import apps.page.view.MainPageFXMLController;
import apps.page.view.MainPageScene;
import javafx.fxml.FXMLLoader;
import listeners.StringListener;
import utilities.Serialize;

public class MainPageListener implements StringListener {
    private final MainPageFXMLController mainPageFXML;

    private final ProfilePane profilePane;
    private final ProfileFXMLController profileFXML;

    private final TimelinePane timelinePane;
    private final TimeLineFXMLController timeLineFXML;

    private final SettingsPane settingsPane;

    private final MessengerPane messengerPane;
    private final MessengerFXMLController messengerFXML;

    private final Serialize serialize = Serialize.getSerialize();

    public MainPageListener(MainPageScene mainPageScene , User user)
    {

        FXMLLoader mainPageFXMLLoader = mainPageScene.getLoader();
        mainPageFXML = mainPageFXMLLoader.getController();

        this.profilePane = new ProfilePane();
        FXMLLoader profileFXMLLoader = this.profilePane.getLoader();
        profileFXML = profileFXMLLoader.getController();
        ProfileController.getProfileController().setUser(user);
        profileFXML.setListener(new ProfileStringListener(mainPageFXML.getMiddlePane() , profilePane));
        profileFXML.setMainPane(mainPageFXML.getMiddlePane());

        this.timelinePane = new TimelinePane();
        FXMLLoader timeLineFXMLLoader = this.timelinePane.getLoader();
        timeLineFXML = timeLineFXMLLoader.getController();
        TimelineController.getTimelineController().setUser(user);
        timeLineFXML.setListener(new SearchListener(mainPageFXML.getMiddlePane()));

        this.settingsPane = new SettingsPane();
        FXMLLoader settingsLoader = this.settingsPane.getLoader();
        SettingsFXMLController settingsFXML = settingsLoader.getController();
        settingsFXML.setListener(new SettingsListener(new SettingsController(user)));

        this.messengerPane = new MessengerPane();
        FXMLLoader messengerLoader = this.messengerPane.getLoader();
        this.messengerFXML = messengerLoader.getController();
        MessengerController messengerController = new MessengerController(user);
        this.messengerFXML.setController(messengerController);
        messengerFXML.setListener(new MessengerListener(messengerController));
    }

    @Override
    public void eventOccurred(String string) {

        if(string.equals("Profile"))
        {
            profileFXML.setData();
            mainPageFXML.getMiddlePane().getChildren().clear();
            mainPageFXML.getMiddlePane().getChildren().add(profilePane.getPane());

            serialize.clear();
            serialize.addStep(profilePane , "My profile");
        }

        if(string.equals("Timeline"))
        {
            timeLineFXML.setData(mainPageFXML.getMiddlePane() , "Timeline");
            mainPageFXML.getMiddlePane().getChildren().clear();
            mainPageFXML.getMiddlePane().getChildren().add(timelinePane.getPane());

            serialize.clear();
            serialize.addStep(timelinePane , "Timeline");
        }

        if(string.equals("Explorer"))
        {
            timeLineFXML.setData(mainPageFXML.getMiddlePane() , "Explorer");
            mainPageFXML.getMiddlePane().getChildren().clear();
            mainPageFXML.getMiddlePane().getChildren().add(timelinePane.getPane());

            serialize.clear();
            serialize.addStep(timelinePane , "Explorer");
        }

        if(string.equals("Messenger"))
        {
            this.messengerFXML.setData();
            mainPageFXML.getMiddlePane().getChildren().clear();
            mainPageFXML.getMiddlePane().getChildren().add(messengerPane.getPane());
        }

        if(string.equals("Settings"))
        {
            mainPageFXML.getMiddlePane().getChildren().clear();
            mainPageFXML.getMiddlePane().getChildren().add(settingsPane.getPane());

            serialize.clear();
            serialize.addStep(settingsPane , "Explorer");
        }

    }
}
