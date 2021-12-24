package apps.page.messenger.view;

import apps.page.messenger.chatPane.controller.ChatController;
import apps.page.messenger.chatPane.listener.ChatPreViewListener;
import apps.page.messenger.chatPane.view.ChatPreViewFXMLController;
import apps.page.messenger.chatPane.view.ChatPreViewPane;
import apps.page.messenger.controller.MessengerController;
import apps.page.messenger.listener.MessengerListener;
import apps.page.messenger.models.Group;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MessengerFXMLController {

    private MessengerListener listener;
    private MessengerController controller;
    private int lastShownChat;

    @FXML
    private Pane chat0Pane;
    @FXML
    private Pane chat1Pane;
    @FXML
    private Pane chat2Pane;
    @FXML
    private Pane chat3Pane;
    @FXML
    private Pane chat4Pane;
    @FXML
    private Pane chat5Pane;
    @FXML
    private Pane chat6Pane;
    @FXML
    private Pane chat7Pane;
    @FXML
    private Pane chat8Pane;
    @FXML
    private Pane chat9Pane;
    @FXML
    private Pane chat10Pane;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;
    @FXML
    private Pane chatPane;

    public void setController(MessengerController controller) {
        this.controller = controller;
    }

    public void setListener(MessengerListener listener)
    {
        this.listener = listener;
    }

    public void setData()
    {
        ChatPreViewPane chatPreViewPane = new ChatPreViewPane();
        FXMLLoader loader = chatPreViewPane.getLoader();
        ChatPreViewFXMLController chatPreViewFXML = loader.getController();
        ChatController chatController = new ChatController(controller.getUser() , controller.getUser().getMessenger().getSavedMessages());
        chatPreViewFXML.setController(chatController);
        chatPreViewFXML.setListener(new ChatPreViewListener(chatPane , chatController));
        chatPreViewFXML.setData();
        chat0Pane.getChildren().add(chatPreViewPane.getPane());

        controller.rearrangeChats();
        lastShownChat = controller.chatSize();

        setChatLists(lastShownChat);
    }

    public void setChatLists(int ind)
    {
        chat1Pane.getChildren().clear();
        chat2Pane.getChildren().clear();
        chat3Pane.getChildren().clear();
        chat4Pane.getChildren().clear();
        chat5Pane.getChildren().clear();
        chat6Pane.getChildren().clear();
        chat7Pane.getChildren().clear();
        chat8Pane.getChildren().clear();
        chat9Pane.getChildren().clear();
        chat10Pane.getChildren().clear();

        for(int i=1 ; i<=10 ; i++)
        {
            Group group = controller.getGroup(ind - 1);

            if(group != null)
            {
                ChatPreViewPane chatPreViewPane = new ChatPreViewPane();
                FXMLLoader loader = chatPreViewPane.getLoader();
                ChatPreViewFXMLController chatPreViewFXML = loader.getController();
                ChatController chatController = new ChatController(controller.getUser(), group);
                chatPreViewFXML.setController(chatController);
                chatPreViewFXML.setListener(new ChatPreViewListener(chatPane , chatController));
                chatPreViewFXML.setData();

                if(i == 1)
                    chat1Pane.getChildren().add(chatPreViewPane.getPane());

                if(i == 2)
                    chat2Pane.getChildren().add(chatPreViewPane.getPane());

                if(i == 3)
                    chat3Pane.getChildren().add(chatPreViewPane.getPane());

                if(i == 4)
                    chat4Pane.getChildren().add(chatPreViewPane.getPane());

                if(i == 5)
                    chat5Pane.getChildren().add(chatPreViewPane.getPane());

                if(i == 6)
                    chat6Pane.getChildren().add(chatPreViewPane.getPane());

                if(i == 7)
                    chat7Pane.getChildren().add(chatPreViewPane.getPane());

                if(i == 8)
                    chat8Pane.getChildren().add(chatPreViewPane.getPane());

                if(i == 9)
                    chat9Pane.getChildren().add(chatPreViewPane.getPane());

                if(i == 10)
                    chat10Pane.getChildren().add(chatPreViewPane.getPane());

                ind--;
            }
        }

        lastShownChat = ind;

        backButton.setDisable(!controller.backAvailable(lastShownChat));
        nextButton.setDisable(!controller.nextAvailable(lastShownChat));
    }

    public void nextPage()
    {
        setChatLists(controller.prevPageInd(lastShownChat));
    }

    public void backPage()
    {
        setChatLists(lastShownChat);
    }

    public void lists()
    {
        listener.eventOccurred(chatPane , "Lists");
    }

    public void compose()
    {
        listener.eventOccurred(chatPane , "Compose");
    }

    public void newGroup()
    {
        listener.eventOccurred(chatPane , "New group");
        setData();
    }
}
