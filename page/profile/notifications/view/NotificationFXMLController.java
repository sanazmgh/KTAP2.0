package apps.page.profile.notifications.view;

import apps.page.profile.notifications.controller.NotifViewController;
import apps.page.profile.notifications.controller.NotificationController;
import apps.page.profile.notifications.listeners.NotifViewListener;
import apps.page.profile.notifications.model.Notification;
import apps.page.timeline.profileView.controller.ProfileViewController;
import apps.page.timeline.profileView.listeners.PreViewListener;
import apps.page.timeline.profileView.view.PreViewFXMLController;
import apps.page.timeline.profileView.view.PreViewPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class NotificationFXMLController {
    private int type;
    private int lastShownNotif;
    private NotificationController controller;
    private Pane middlePane;

    @FXML
    Pane preView1Pane;
    @FXML
    Pane preView2Pane;
    @FXML
    Pane preView3Pane;
    @FXML
    Pane preView4Pane;
    @FXML
    Pane notification1Pane;
    @FXML
    Pane notification2Pane;
    @FXML
    Pane notification3Pane;
    @FXML
    Pane notification4Pane;
    @FXML
    Button nextButton;
    @FXML
    Button backButton;

    public void setController(NotificationController controller) {
        this.controller = controller;
    }

    public void setMiddlePane(Pane middlePane) {
        this.middlePane = middlePane;
    }

    public void showRequests()
    {
        lastShownNotif = controller.requestsSize();
        type = 0;
        setNotif(lastShownNotif);
    }

    public void showSystemNotifications()
    {
        lastShownNotif = controller.systemNotificationsSize();
        type = 1;
        setNotif(lastShownNotif);
    }

    public void setNotif(int ind)
    {
        preView1Pane.getChildren().clear();
        preView2Pane.getChildren().clear();
        preView3Pane.getChildren().clear();
        preView4Pane.getChildren().clear();

        notification1Pane.getChildren().clear();
        notification2Pane.getChildren().clear();
        notification3Pane.getChildren().clear();
        notification4Pane.getChildren().clear();

        Notification[] notifications = new Notification[4];
        notifications[0] = controller.getNotif(ind-1 , type);
        notifications[1] = controller.getNotif(ind-2 , type);
        notifications[2] = controller.getNotif(ind-3 , type);
        notifications[3] = controller.getNotif(ind-4 , type);

        for(int i=0 ; i<4 ; i++)
        {
            if(notifications[i] != null) {
                PreViewPane preView = new PreViewPane();
                FXMLLoader preViewLoader = preView.getLoader();
                PreViewFXMLController preViewFXMLController = preViewLoader.getController();
                ProfileViewController profileViewController = new ProfileViewController(controller.getUser(), controller.getSenderUser(notifications[i]));
                preViewFXMLController.setController(profileViewController);
                preViewFXMLController.setListener(new PreViewListener(profileViewController, middlePane));
                preViewFXMLController.setData();

                NotifViewPane notifView = new NotifViewPane();
                FXMLLoader notifViewLoader = notifView.getLoader();
                NotifViewFXMLController notifViewFXML = notifViewLoader.getController();
                NotifViewController notifViewController = new NotifViewController(notifications[i] , type , controller.getUser());
                notifViewFXML.setController(notifViewController);
                notifViewFXML.setListener(new NotifViewListener(notifViewController));
                notifViewFXML.setData();

                if (i == 0) {
                    preView1Pane.getChildren().add(preView.getPane());
                    notification1Pane.getChildren().add(notifView.getPane());
                    ind--;
                }

                if (i == 1) {
                    preView2Pane.getChildren().add(preView.getPane());
                    notification2Pane.getChildren().add(notifView.getPane());
                    ind--;
                }

                if (i == 2) {
                    preView3Pane.getChildren().add(preView.getPane());
                    notification3Pane.getChildren().add(notifView.getPane());
                    ind--;
                }

                if (i == 3) {
                    preView4Pane.getChildren().add(preView.getPane());
                    notification4Pane.getChildren().add(notifView.getPane());
                    ind--;
                }
            }

        }

        lastShownNotif = ind;

        backButton.setDisable(!controller.backAvailable(lastShownNotif , type));
        nextButton.setDisable(!controller.nextAvailable(lastShownNotif));
    }

    public void next()
    {
        setNotif(controller.prevPageInd(lastShownNotif , type));
    }

    public void back()
    {
        setNotif(lastShownNotif);
    }

}
