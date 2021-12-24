package apps.page.timeline.profileView.listeners;

import apps.page.timeline.profileView.controller.ProfileViewController;
import apps.page.timeline.profileView.view.ProfileViewFXMLController;
import apps.page.timeline.profileView.view.ProfileViewPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import utilities.Serialize;

public class PreViewListener {
    private final ProfileViewController controller;
    private final Pane middlePane;
    private final Serialize serialize = Serialize.getSerialize();

    public PreViewListener( ProfileViewController controller , Pane middlePane)
    {
        this.controller = controller;
        this.middlePane = middlePane;
    }

    public void eventOccurred(String string)
    {
        if(string.equals("View profile"))
        {
            ProfileViewPane profileViewPane = new ProfileViewPane();
            FXMLLoader loader = profileViewPane.getLoader();
            ProfileViewFXMLController profileViewFXML = loader.getController();
            profileViewFXML.setController(controller);
            profileViewFXML.setListener(new ProfileListener(controller , middlePane));
            profileViewFXML.setMiddlePane(middlePane);
            profileViewFXML.setData();

            middlePane.getChildren().add(profileViewPane.getPane());
            serialize.addStep(profileViewPane , "UsersProfile");
        }

        if(string.equals("Block"))
        {
            controller.changeBlockOwner();
        }

        if(string.equals("Mute"))
        {
            controller.changeMuteOwner();
        }
    }

}
