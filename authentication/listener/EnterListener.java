package apps.authentication.listener;

import apps.authentication.controller.AuthenticationController;
import apps.authentication.model.User;
import apps.page.listeners.MainPageListener;
import apps.page.view.MainPageFXMLController;
import apps.page.view.MainPageScene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class EnterListener {
    private final Stage stage;

    public EnterListener(Stage stage)
    {
        this.stage = stage;
    }

    public void eventOccurred() {
        AuthenticationController controller = AuthenticationController.getAuthenticationController();
        User user = controller.getUser();
        MainPageScene mainPageScene = new MainPageScene();
        FXMLLoader mainPageFXMLLoader = mainPageScene.getLoader();
        MainPageFXMLController mainPageFXMLController = mainPageFXMLLoader.getController();
        mainPageFXMLController.setListener(new MainPageListener(mainPageScene , user));

        stage.setScene(mainPageScene.getScene());
        stage.show();
    }

}
