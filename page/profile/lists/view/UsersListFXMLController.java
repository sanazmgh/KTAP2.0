package apps.page.profile.lists.view;

import apps.authentication.model.User;
import apps.page.profile.lists.controller.UserListController;
import apps.page.timeline.profileView.controller.ProfileViewController;
import apps.page.timeline.profileView.listeners.PreViewListener;
import apps.page.timeline.profileView.view.PreViewFXMLController;
import apps.page.timeline.profileView.view.PreViewPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;


public class UsersListFXMLController {

    private UserListController controller;
    private Pane middlePane;
    private int lastShownUser;

    @FXML
    private Pane preView1Pane;
    @FXML
    private Pane preView2Pane;
    @FXML
    private Pane preView3Pane;
    @FXML
    private Pane preView4Pane;
    @FXML
    private Pane preView5Pane;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;

    public void setController(UserListController controller) {
        this.controller = controller;
    }

    public void setMiddlePane(Pane middlePane) {
        this.middlePane = middlePane;
    }

    public void setData()
    {
        lastShownUser = controller.listSize();
        setUsers(lastShownUser);
    }

    public void setUsers(int ind)
    {
        preView1Pane.getChildren().clear();
        preView2Pane.getChildren().clear();
        preView3Pane.getChildren().clear();
        preView4Pane.getChildren().clear();
        preView5Pane.getChildren().clear();

        User[] users = new User[5];
        users[0] = controller.getUserList(ind - 1);
        users[1] = controller.getUserList(ind - 2);
        users[2] = controller.getUserList(ind - 3);
        users[3] = controller.getUserList(ind - 4);
        users[4] = controller.getUserList(ind - 5);

        for (int i = 0; i < 5; i++)
        {
            if(users[i] != null && users[i].isActive()) {
                PreViewPane preView = new PreViewPane();
                FXMLLoader preViewLoader = preView.getLoader();
                PreViewFXMLController preViewFXMLController = preViewLoader.getController();
                ProfileViewController profileViewController = new ProfileViewController(controller.getUser(), users[i]);
                preViewFXMLController.setController(profileViewController);
                preViewFXMLController.setListener(new PreViewListener(profileViewController, middlePane));
                preViewFXMLController.setData();

                if (i == 0)
                    preView1Pane.getChildren().add(preView.getPane());
                if (i == 1)
                    preView2Pane.getChildren().add(preView.getPane());
                if (i == 2)
                    preView3Pane.getChildren().add(preView.getPane());
                if (i == 3)
                    preView4Pane.getChildren().add(preView.getPane());
                if (i == 4)
                    preView5Pane.getChildren().add(preView.getPane());

                ind--;
            }
        }

        lastShownUser = ind;

        backButton.setDisable(!controller.backAvailable(lastShownUser));
        nextButton.setDisable(!controller.nextAvailable(lastShownUser));
    }

    public void backPage()
    {
        setUsers(controller.prevPageInd(lastShownUser));
    }

    public void nextPage()
    {
        setUsers(lastShownUser);
    }
}
