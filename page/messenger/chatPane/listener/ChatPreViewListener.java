package apps.page.messenger.chatPane.listener;

import apps.page.messenger.chatPane.controller.ChatController;
import apps.page.messenger.chatPane.view.ChatFXMLController;
import apps.page.messenger.chatPane.view.ChatPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class ChatPreViewListener {
    private final Pane messengerPane;
    private final ChatPane chatPane;
    private final ChatFXMLController chatFXML;
    private final ChatController controller;

    public ChatPreViewListener(Pane messengerPane ,ChatController controller )
    {
        this.messengerPane = messengerPane;
        this.controller = controller;
        this.chatPane = new ChatPane();
        FXMLLoader loader = chatPane.getLoader();
        this.chatFXML = loader.getController();
        chatFXML.setController(controller);
        chatFXML.setListener(new ChatListener(controller));
    }

    public void eventOccurred(String string)
    {
        if(string.equals("View"))
        {
            controller.seenMessages();
            chatFXML.setData();
            messengerPane.getChildren().clear();
            messengerPane.getChildren().add(chatPane.getPane());
        }
    }
}
