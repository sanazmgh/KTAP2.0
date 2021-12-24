package apps.page.timeline.Listeners;

import apps.authentication.model.User;
import apps.page.timeline.controller.TimelineController;
import apps.page.timeline.profileView.controller.ProfileViewController;
import apps.page.timeline.profileView.listeners.PreViewListener;
import apps.page.timeline.profileView.view.PreViewFXMLController;
import apps.page.timeline.profileView.view.PreViewPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchListener {
    private final TimelineController controller = TimelineController.getTimelineController();
    private final Pane middlePane;
    static private final Logger logger = LogManager.getLogger(SearchListener.class);


    public SearchListener(Pane middlePane)
    {
        this.middlePane = middlePane;
    }

    public int eventOccurred(String username , Pane viewUser)
    {
        User foundUser = controller.search(username);
        logger.info("searched for : " + username);
        if(foundUser != null)
        {
            PreViewPane preView = new PreViewPane();
            FXMLLoader preViewLoader = preView.getLoader();
            PreViewFXMLController preViewFXMLController = preViewLoader.getController();
            ProfileViewController profileViewController = new ProfileViewController(controller.getUser(), foundUser);
            preViewFXMLController.setController(profileViewController);
            preViewFXMLController.setListener(new PreViewListener(profileViewController , middlePane));
            preViewFXMLController.setData();

            viewUser.getChildren().clear();
            viewUser.getChildren().add(preView.getPane());

            return 0;
        }

        logger.warn("searching for : " + username + " wasn't successful : null user");
        return 1;
    }
}
