package apps.page.messenger.compose.view;

import apps.page.messenger.compose.event.ComposeEvent;
import apps.page.messenger.compose.listener.ComposeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ComposeFXMLController {

    ComposeListener listener;

    @FXML
    private TextField messageTextField;
    @FXML
    private TextField attachmentTextField;
    @FXML
    private TextField composeTextField;

    public void setListener(ComposeListener listener) {
        this.listener = listener;
    }

    public void send()
    {
        listener.eventOccurred(new ComposeEvent(messageTextField.getText(), attachmentTextField.getText(), composeTextField.getText()));
    }
}
