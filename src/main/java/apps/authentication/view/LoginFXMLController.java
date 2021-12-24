package apps.authentication.view;

import apps.authentication.event.LoginFormEvent;
import apps.authentication.listener.EnterListener;
import apps.authentication.listener.LoginFormListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import listeners.StringListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginFXMLController {
    @FXML
    private TextField usernameTextField ;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Text WarningText;

    private LoginFormListener formListener;
    private StringListener stringListener;
    private EnterListener enterListener;

    static private final Logger logger = LogManager.getLogger(LoginFXMLController.class);


    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public void setFormListener(LoginFormListener formListener) {
        this.formListener = formListener;
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void setEnterListener(EnterListener enterListener) {
        this.enterListener = enterListener;
    }

    public void submitInfo()
    {
        logger.info("info submitted to login");

        LoginFormEvent loginFormEvent =
                new LoginFormEvent(getUsername(), getPassword());

        boolean condition = formListener.eventOccurred(loginFormEvent);

        if(condition)
            enterListener.eventOccurred();

        else
            WarningText.setVisible(true);
    }

    public void signUp()
    {
        stringListener.eventOccurred("Sign up");
    }
}
