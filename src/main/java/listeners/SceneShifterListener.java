package listeners;

import apps.authentication.view.LoginScene;
import apps.authentication.view.SignUpScene;
import javafx.stage.Stage;

public class SceneShifterListener implements StringListener {

    private final Stage stage;
    private final LoginScene loginScene;
    private final SignUpScene signUpScene;
    //private final MainPageScene mainPageScene;

    public SceneShifterListener(Stage stage , LoginScene loginScene , SignUpScene signUpScene)
    {
        this.stage = stage;
        this.loginScene = loginScene;
        this.signUpScene = signUpScene;
/*
        this.mainPageScene = new MainPageScene();
        FXMLLoader mainPageFXMLLoader = mainPageScene.getLoader();
        MainPageFXMLController mainPageFXMLController = mainPageFXMLLoader.getController();
        mainPageFXMLController.setListener(new MainPageListener(mainPageScene));

 */
    }

    @Override
    public void eventOccurred(String string) {
        if (string.equals("Sign up")) {
            stage.setScene(signUpScene.getScene());
            stage.show();
        }

        if(string.equals("Login")) {
            stage.setScene(loginScene.getScene());
            stage.show();
        }
    }
}

