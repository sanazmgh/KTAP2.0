package apps.page.messenger.chatPane.view;

import apps.page.messenger.chatPane.controller.ChatController;
import apps.page.messenger.chatPane.message.controller.MessageController;
import apps.page.messenger.chatPane.events.MessageEvent;
import apps.page.messenger.chatPane.listener.ChatListener;
import apps.page.messenger.chatPane.message.listener.MessageListener;
import apps.page.messenger.chatPane.message.model.Message;
import apps.page.messenger.chatPane.message.view.MessageFXMLController;
import apps.page.messenger.chatPane.message.view.MessagePane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ChatFXMLController {

    private ChatController controller ;
    private int lastShownMessage;
    private ChatListener listener;

    @FXML
    private Text groupText;
    @FXML
    private Text membersText;
    @FXML
    private TextField addUserTextField;
    @FXML
    private Pane message1Pane;
    @FXML
    private Pane message2Pane;
    @FXML
    private Pane message3Pane;
    @FXML
    private Pane message4Pane;
    @FXML
    private Pane message5Pane;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;
    @FXML
    private TextField messageTextField;
    @FXML
    private TextField attachmentTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button submitButton;

    public void setController(ChatController controller) {
        this.controller = controller;
    }

    public void setListener(ChatListener listener)
    {
        this.listener = listener;
    }

    public void setData()
    {
        groupText.setText(controller.getName());
        addButton.setVisible(controller.canAddTo());
        addUserTextField.setVisible(controller.canAddTo());
        membersText.setText(controller.getUserNames());
        lastShownMessage = controller.messageSize();

        submitButton.setDisable(!controller.canText());
        messageTextField.setDisable(!controller.canText());
        attachmentTextField.setDisable(!controller.canText());

        setMessages(lastShownMessage);
    }

    public void setMessages(int ind)
    {
        message1Pane.getChildren().clear();
        message2Pane.getChildren().clear();
        message3Pane.getChildren().clear();
        message4Pane.getChildren().clear();
        message5Pane.getChildren().clear();

        for(int i=1 ; i<=5 ; i++)
        {
            Message message = controller.getMessage(ind-1);

            if(message != null)
            {
                MessagePane messagePane = new MessagePane();
                FXMLLoader loader = messagePane.getLoader();
                MessageFXMLController messageFXML = loader.getController();
                MessageController messageController = new MessageController(message , controller.getUser());
                messageFXML.setController(messageController);
                messageFXML.setListener(new MessageListener(messageController));
                messageFXML.setData();

                if(i == 1)
                    message1Pane.getChildren().add(messagePane.getPane());
                if(i == 2)
                    message2Pane.getChildren().add(messagePane.getPane());
                if(i == 3)
                    message3Pane.getChildren().add(messagePane.getPane());
                if(i == 4)
                    message4Pane.getChildren().add(messagePane.getPane());
                if(i == 5)
                    message5Pane.getChildren().add(messagePane.getPane());

                ind--;
            }
        }

        lastShownMessage = ind;

        backButton.setDisable(!controller.backAvailable(lastShownMessage));
        nextButton.setDisable(!controller.nextAvailable(lastShownMessage));
    }

    public void next()
    {
        setMessages(lastShownMessage);
    }

    public void back()
    {
        setMessages(controller.prevPageInd(lastShownMessage));
    }

    public void addMember()
    {
        listener.eventOccurred(addUserTextField.getText());
        setData();
    }

    public void submitMessage()
    {
        listener.eventOccurred(new MessageEvent(messageTextField.getText() , attachmentTextField.getText()));
        setData();
    }

}
