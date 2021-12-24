package apps.page.timeline.profileView.view;

import apps.page.timeline.profileView.controller.ProfileViewController;
import apps.page.timeline.profileView.listeners.PreViewListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;


public class PreViewFXMLController {

    ProfileViewController controller;
    PreViewListener listener;

    @FXML
    Label nameLabel;
    @FXML
    Label usernameLabel;
    @FXML
    ImageView prePicImageView;
    @FXML
    Button blockButton;
    @FXML
    Button muteButton;
    @FXML
    Button viewProfileButton;

    public void setController(ProfileViewController controller) {
        this.controller = controller;
    }

    public void setListener(PreViewListener listener) {
        this.listener = listener;
    }

    public void setData() {
        nameLabel.setText(controller.getName());
        usernameLabel.setText(controller.getUsername());

        if(controller.getOwner().getId() == controller.getUser().getId())
        {
            blockButton.setVisible(false);
            muteButton.setVisible(false);
            viewProfileButton.setVisible(false);
        }

        else
        {
            blockButton.setVisible(true);
            muteButton.setVisible(true);
            viewProfileButton.setVisible(true);
        }

        try {
            prePicImageView.setImage(new Image(new File(controller.getPic()).toURI().toURL().toExternalForm()));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void ViewProfile()
    {
        listener.eventOccurred("View profile");
    }

    public void blockUser()
    {
        listener.eventOccurred("Block");
    }

    public void muteUser()
    {
        listener.eventOccurred("Mute");
    }
}
