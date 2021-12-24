package apps.page.messenger.chatPane.message.view;

import apps.page.messenger.chatPane.message.controller.MessageController;
import apps.page.messenger.chatPane.message.listener.MessageListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;

public class MessageFXMLController {
    private MessageController controller;
    private MessageListener listener;

    @FXML
    private Text nameText;
    @FXML
    private Text sentFromText;
    @FXML
    private Label messageLabel;
    @FXML
    private ImageView profileImageView;
    @FXML
    private ImageView attachmentImageView;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    TextField editTextField;
    @FXML
    Button submitButton;

    public void setController(MessageController controller) {
        this.controller = controller;
    }

    public void setListener(MessageListener listener) {
        this.listener = listener;
    }

    public void setData()
    {
        nameText.setText(controller.getName());
        sentFromText.setText(controller.getInfo());
        deleteButton.setVisible(controller.isEditable());
        deleteButton.setDisable(controller.isDeleted());
        editButton.setDisable(controller.isDeleted());
        editButton.setVisible(controller.isEditable());
        messageLabel.setText(controller.getText());

        try {
            profileImageView.setImage(new Image(new File(controller.getProfile()).toURI().toURL().toExternalForm()));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(!controller.getTweetImage().equals("")) {
            try {
                attachmentImageView.setImage(new Image(new File(controller.getTweetImage()).toURI().toURL().toExternalForm()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public void edit()
    {
        submitButton.setVisible(true);
        editTextField.setVisible(true);
    }

    public void submit()
    {
        listener.eventOccurred(editTextField.getText());
        submitButton.setVisible(false);
        editTextField.setVisible(false);
        setData();
    }

    public void delete()
    {
        listener.eventOccurred("Deleted message");
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        setData();
    }

}
