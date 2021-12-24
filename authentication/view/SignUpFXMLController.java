package apps.authentication.view;

import apps.authentication.event.UserInfoFormEvent;
import apps.authentication.listener.EnterListener;
import apps.authentication.listener.UserInfoListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import listeners.StringListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SignUpFXMLController {

    private UserInfoListener formListener;
    private StringListener stringListener;
    private EnterListener enterListener;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField password1TextField;
    @FXML
    private TextField password2TextField;
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

    static private final Logger logger = LogManager.getLogger(SignUpFXMLController.class);

    public void setFormListener(UserInfoListener formListener) {
        this.formListener = formListener;
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void setEnterListener(EnterListener enterListener) {
        this.enterListener = enterListener;
    }

    public void login()
    {
        stringListener.eventOccurred("Login");
    }

    public void submitInfo()
    {
        logger.info("info submitted to sign up");

        UserInfoFormEvent userInfoFormEvent = new UserInfoFormEvent(
                usernameTextField.getText(),
                nameTextField.getText(),
                lastNameTextField.getText(),
                password1TextField.getText(),
                password2TextField.getText(),
                emailTextField.getText(),
                emailVisibleCheck.isSelected(),
                phoneTextField.getText(),
                phoneVisibleCheck.isSelected(),
                bioTextField.getText(),
                datePicker.getValue(),
                dateVisibleCheck.isSelected(),
                ""
        );

        int condition = formListener.eventOccurred(userInfoFormEvent);

        if(condition == 0)
        {
            enterListener.eventOccurred();
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
}
