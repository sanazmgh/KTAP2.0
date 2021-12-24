package apps.page.profile.lists.view;

import apps.page.profile.lists.controller.ListsController;
import apps.page.profile.lists.controller.UserListController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

public class ListsFXMLController {

    private ListsController controller;
    private Pane middlePane;

    @FXML
    private Pane usersListPane;

    public void setController(ListsController controller) {
        this.controller = controller;
    }

    public void setMiddlePane(Pane middlePane) {
        this.middlePane = middlePane;
    }

    public void showFollowers()
    {
        setList(controller.getFollowers());

    }

    public void showFollowings()
    {
        setList(controller.getFollowings());

    }

    public void showMuted()
    {
        setList(controller.getMuted());

    }

    public void showBlocked()
    {
        setList(controller.getBlocked());
    }

    public void setList(LinkedList<Integer> list)
    {
        UsersListPane listPane = new UsersListPane();
        FXMLLoader loader = listPane.getLoader();
        UsersListFXMLController listFXML = loader.getController();
        listFXML.setController(new UserListController(controller.getUser() , list));
        listFXML.setMiddlePane(middlePane);
        listFXML.setData();

        usersListPane.getChildren().clear();
        usersListPane.getChildren().add(listPane.getPane());
    }

    public void prevStep()
    {

    }
}
