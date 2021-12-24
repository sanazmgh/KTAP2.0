package apps.page.messenger.listener;

import apps.page.messenger.compose.controller.ComposeController;
import apps.page.messenger.compose.listener.ComposeListener;
import apps.page.messenger.compose.view.ComposeFXMLController;
import apps.page.messenger.compose.view.ComposePane;
import apps.page.messenger.controller.MessengerController;
import apps.page.messenger.lists.controller.ViewListsController;
import apps.page.messenger.lists.view.MessengerListsFXMLController;
import apps.page.messenger.lists.view.MessengerListsPane;
import apps.page.messenger.newGroup.controller.NewListController;
import apps.page.messenger.newGroup.listener.NewListListener;
import apps.page.messenger.newGroup.view.NewListFXMLController;
import apps.page.messenger.newGroup.view.NewListPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MessengerListener {

    private final MessengerListsPane messengerListsPane;
    private final MessengerListsFXMLController messengerListsFXML;

    private final ComposePane composePane;

    private final NewListPane newListPane;

    public MessengerListener(MessengerController controller)
    {
        this.messengerListsPane = new MessengerListsPane();
        FXMLLoader loader1 = messengerListsPane.getLoader();
        this.messengerListsFXML = loader1.getController();
        messengerListsFXML.setController(new ViewListsController(controller.getUser()));

        this.composePane = new ComposePane();
        FXMLLoader loader2 = composePane.getLoader();
        ComposeFXMLController composeFXML = loader2.getController();
        composeFXML.setListener(new ComposeListener(new ComposeController(controller.getUser())));

        this.newListPane = new NewListPane();
        FXMLLoader loader3 = newListPane.getLoader();
        NewListFXMLController newListFXML = loader3.getController();
        newListFXML.setListener(new NewListListener(new NewListController(controller.getUser())));
        newListFXML.setType(1);
    }

    public void eventOccurred(Pane chatPane , String string)
    {
        if(string.equals("Lists"))
        {
            messengerListsFXML.setData();

            chatPane.getChildren().clear();
            chatPane.getChildren().add(messengerListsPane.getPane());
        }

        if(string.equals("Compose"))
        {
            chatPane.getChildren().clear();
            chatPane.getChildren().add(composePane.getPane());
        }

        if(string.equals("New group"))
        {
            chatPane.getChildren().clear();
            chatPane.getChildren().add(newListPane.getPane());
        }

    }
}
