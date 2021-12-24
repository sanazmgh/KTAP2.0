package apps.page.messenger.lists.view;

import apps.page.messenger.lists.controller.ListsController;
import apps.page.messenger.lists.controller.ViewListsController;
import apps.page.messenger.lists.listener.ViewListListener;
import apps.page.messenger.models.List;
import apps.page.messenger.newGroup.controller.NewListController;
import apps.page.messenger.newGroup.listener.NewListListener;
import apps.page.messenger.newGroup.view.NewListFXMLController;
import apps.page.messenger.newGroup.view.NewListPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MessengerListsFXMLController {

    private ViewListsController controller;
    private int lastShownList;

    @FXML
    private Pane newListPane;
    @FXML
    private Pane list1Pane;
    @FXML
    private Pane list2Pane;
    @FXML
    private Pane list3Pane;
    @FXML
    private Pane list4Pane;
    @FXML
    private Pane list5Pane;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;

    public void setController(ViewListsController controller) {
        this.controller = controller;
    }

    public void setData()
    {
        NewListPane newList = new NewListPane();
        FXMLLoader loader = newList.getLoader();
        NewListFXMLController newListFXML = loader.getController();
        newListFXML.setListener(new NewListListener(new NewListController(controller.getUser())));

        newListPane.getChildren().add(newList.getPane());

        lastShownList = controller.listSize();
        setLists(lastShownList);
    }

    public void setLists(int ind)
    {
        list1Pane.getChildren().clear();
        list2Pane.getChildren().clear();
        list3Pane.getChildren().clear();
        list4Pane.getChildren().clear();
        list5Pane.getChildren().clear();

        for(int i=1 ; i<=5 ; i++)
        {
            List list = controller.getList(ind-i);

            if(list != null)
            {
                ListsViewPane listsView = new ListsViewPane();
                FXMLLoader loader = listsView.getLoader();
                ListsViewFXMLController listsViewFXML = loader.getController();
                ListsController listsController = new ListsController(controller.getUser() , list);
                listsViewFXML.setController(listsController);
                listsViewFXML.setListListener(new ViewListListener(listsController));
                listsViewFXML.setData();

                if(i == 1)
                    list1Pane.getChildren().add(listsView.getPane());

                if(i == 2)
                    list2Pane.getChildren().add(listsView.getPane());

                if(i == 3)
                    list3Pane.getChildren().add(listsView.getPane());

                if(i == 4)
                    list4Pane.getChildren().add(listsView.getPane());

                if(i == 5)
                    list5Pane.getChildren().add(listsView.getPane());

                ind--;
            }
        }

        lastShownList = ind;

        backButton.setDisable(!controller.backAvailable(lastShownList));
        nextButton.setDisable(!controller.nextAvailable(lastShownList));
    }


    public void backPage()
    {
        setLists(controller.prevPageInd(lastShownList));
    }

    public void nextPage()
    {
        setLists(lastShownList);
    }
}
