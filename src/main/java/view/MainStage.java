package view;

import apps.authentication.controller.AuthenticationController;
import apps.authentication.listener.EnterListener;
import apps.authentication.listener.LoginFormListener;
import apps.authentication.listener.UserInfoListener;
import apps.authentication.view.LoginFXMLController;
import apps.authentication.view.LoginScene;
import apps.authentication.view.SignUpFXMLController;
import apps.authentication.view.SignUpScene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import listeners.SceneShifterListener;

import java.io.IOException;

public class MainStage {

    public MainStage(Stage stage) throws IOException {
        SignUpScene signUpScene = new SignUpScene();
        LoginScene loginScene = new LoginScene();

        AuthenticationController controller = AuthenticationController.getAuthenticationController();

        FXMLLoader loginFXMLLoader = loginScene.getLoader();
        LoginFXMLController loginFXMLController = loginFXMLLoader.getController();
        loginFXMLController.setFormListener(new LoginFormListener(controller));
        loginFXMLController.setStringListener(new SceneShifterListener(stage , loginScene , signUpScene));
        loginFXMLController.setEnterListener(new EnterListener(stage));

        FXMLLoader signUpFXMLLoader = signUpScene.getLoader();
        SignUpFXMLController signUpFXMLController = signUpFXMLLoader.getController();
        signUpFXMLController.setFormListener(new UserInfoListener(controller));
        signUpFXMLController.setStringListener(new SceneShifterListener(stage , loginScene , signUpScene));
        signUpFXMLController.setEnterListener(new EnterListener(stage));

        stage.setTitle("KTAP");
        stage.setScene(loginScene.getScene());
        stage.setResizable(false);
        stage.show();
    }
}
