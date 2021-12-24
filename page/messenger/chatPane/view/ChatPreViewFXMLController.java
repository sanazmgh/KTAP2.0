package apps.page.messenger.chatPane.view;

import apps.page.messenger.chatPane.controller.ChatController;
import apps.page.messenger.chatPane.listener.ChatPreViewListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChatPreViewFXMLController {
    private ChatController controller;
    private ChatPreViewListener listener;

    @FXML
    Label groupNameLabel;

    public void setController(ChatController controller) {
        this.controller = controller;
    }

    public void setListener(ChatPreViewListener listener) {
        this.listener = listener;
    }

    public void setData()
    {
        groupNameLabel.setText(controller.getName() + " (" + controller.getUnread() +")");
    }

    public void view()
    {
        listener.eventOccurred("View");
        setData();
    }

}
