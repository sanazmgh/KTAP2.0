package apps.page.profile.edit.view;

import apps.authentication.event.UserInfoFormEvent;
import apps.page.profile.edit.controller.EditController;
import apps.page.profile.edit.listener.ChangeInfoListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import listeners.StringListener;

import java.io.File;
import java.net.MalformedURLException;

public class EditFXMLController {

    private ChangeInfoListener formListener;
    private StringListener stringListener;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField pass1PasswordField;
    @FXML
    private PasswordField pass2PasswordField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField bioTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private CheckBox dateVisibleCheck;
    @FXML
    private TextField phoneTextField;
    @FXML
    private CheckBox phoneVisibleCheck;
    @FXML
    private TextField emailTextField;
    @FXML
    private CheckBox emailVisibleCheck;
    @FXML
    private Text usernameError;
    @FXML
    private Text passError;
    @FXML
    private Text emailError;
    @FXML
    private TextField imagePath;
    @FXML
    private ImageView profileImageView;

    public void setFormListener(ChangeInfoListener formListener) {
        this.formListener = formListener;
    }
    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void setData()
    {
        try {
            profileImageView.setImage(new Image(new File(EditController.getEditController().getPic()).toURI().toURL().toExternalForm()));
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void submitInfo()
    {
        UserInfoFormEvent userInfoFormEvent = new UserInfoFormEvent(
                usernameTextField.getText(),
                nameTextField.getText(),
                lastNameTextField.getText(),
                pass1PasswordField.getText(),
                pass2PasswordField.getText(),
                emailTextField.getText(),
                emailVisibleCheck.isSelected(),
                phoneTextField.getText(),
                phoneVisibleCheck.isSelected(),
                bioTextField.getText(),
                datePicker.getValue(),
                dateVisibleCheck.isSelected(),
                imagePath.getText()
        );

        int condition = formListener.eventOccurred(userInfoFormEvent);

        if(condition == 0)
        {
            stringListener.eventOccurred("Enter");
        }

        else if(condition == 1)
        {
            passError.setVisible(false);
            emailError.setVisible(false);
            usernameError.setVisible(true);
        }

        else if(condition == 2)
        {
            passError.setVisible(true);
            emailError.setVisible(false);
            usernameError.setVisible(false);
        }

        else if(condition == 3)
        {
            passError.setVisible(false);
            emailError.setVisible(true);
            usernameError.setVisible(false);
        }

        else
        {
            passError.setVisible(false);
            emailError.setVisible(false);
            usernameError.setVisible(false);
        }
    }

    public void back()
    {
        stringListener.eventOccurred("Back");
    }

}
