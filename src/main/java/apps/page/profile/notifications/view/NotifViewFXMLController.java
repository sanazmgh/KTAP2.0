package apps.page.profile.notifications.view;

import apps.page.profile.notifications.controller.NotifViewController;
import apps.page.profile.notifications.listeners.NotifViewListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class NotifViewFXMLController {
    private NotifViewController controller;
    private NotifViewListener listener;

    @FXML
    Text notificationText;
    @FXML
    Button acceptButton;
    @FXML
    Button notifiedDenyButton;
    @FXML
    Button nonNotifiedDenyButton;

    public void setController(NotifViewController controller)
    {
        this.controller = controller;
    }

    public void setListener(NotifViewListener listener) {
        this.listener = listener;
    }

    public void setData()
    {
        notificationText.setText(controller.getText());

        if(controller.getType() == 0)
        {
            acceptButton.setVisible(true);
            nonNotifiedDenyButton.setVisible(true);
            notifiedDenyButton.setVisible(true);

        }

        else
        {
            acceptButton.setVisible(false);
            nonNotifiedDenyButton.setVisible(false);
            notifiedDenyButton.setVisible(false);
        }

    }

    public void accept()
    {
        listener.eventOccurred("Accept");
        acceptButton.setVisible(false);
        nonNotifiedDenyButton.setVisible(false);
        notifiedDenyButton.setVisible(false);
    }

    public void notifiedDeny()
    {
        listener.eventOccurred("NotifiedDeny");
        acceptButton.setVisible(false);
        nonNotifiedDenyButton.setVisible(false);
        notifiedDenyButton.setVisible(false);
    }

    public void nonNotifiedDeny()
    {
        listener.eventOccurred("NonNotifiedDeny");
        acceptButton.setVisible(false);
        nonNotifiedDenyButton.setVisible(false);
        notifiedDenyButton.setVisible(false);
    }
}
