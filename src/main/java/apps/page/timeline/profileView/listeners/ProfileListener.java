package apps.page.timeline.profileView.listeners;

import apps.page.timeline.profileView.controller.ProfileViewController;
import javafx.scene.layout.Pane;
import utilities.Serialize;

public class ProfileListener {
    private final ProfileViewController controller;
    private final Serialize serialize = Serialize.getSerialize();
    private final Pane middlePane;

    public ProfileListener(ProfileViewController controller , Pane middlePane)
    {
        this.controller = controller;
        this.middlePane = middlePane;
    }

    public void eventOccurred(String string)
    {
        if(string.equals("Change status"))
            controller.changeStatus();

        if(string.equals("Block"))
            controller.changeBlockOwner();

        if(string.equals("Mute"))
            controller.changeMuteOwner();

        if(string.equals("Prev step"))
        {
            middlePane.getChildren().clear();
            middlePane.getChildren().add(serialize.lastMove());
        }
    }
}
