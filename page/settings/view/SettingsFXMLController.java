package apps.page.settings.view;

import apps.page.settings.listeners.SettingsListener;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

public class SettingsFXMLController {
    private SettingsListener listener;

    @FXML
    private RadioButton publicRadioButton;
    @FXML
    private RadioButton everyoneRadioButton;
    @FXML
    private RadioButton nobodyRadioButton;
    @FXML
    private PasswordField deletePasswordField;
    @FXML
    private PasswordField deactivePasswordField;
    @FXML
    private Text deleteErrorText;
    @FXML
    private Text deactiveErrorText;

    public void setListener(SettingsListener listener) {
        this.listener = listener;
    }

    public void privacy()
    {
        listener.eventOccurred(publicRadioButton.isSelected() ? "Public" : "Private");
    }

    public void lastSeen()
    {
        if(nobodyRadioButton.isSelected())
            listener.eventOccurred("Nobody");

        else
            listener.eventOccurred(everyoneRadioButton.isSelected() ? "Everybody" : "Followers");
    }

    public void deleteAccount()
    {
        int condition = listener.eventOccurred("Delete account" , deletePasswordField.getText());

        if(condition == 1)
            deleteErrorText.setVisible(true);

        logOut();
    }

    public void deactiveAccount()
    {
        int condition = listener.eventOccurred("Deactive account" , deactivePasswordField.getText());

        if(condition == 1)
            deactiveErrorText.setVisible(true);

        logOut();
    }

    public void logOut()
    {
        listener.eventOccurred("Log out");
    }
}
