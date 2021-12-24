package apps.authentication.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import utilities.Config;

import java.io.IOException;

public class SignUpScene{
    private final Scene scene ;
    private final FXMLLoader loader;

    public SignUpScene(){
        this.loader = new FXMLLoader(getClass().getResource(Config.getConfig("FXMLDirectories").getProperty(String.class, "signUpScene"))); //TODO config directory
        Parent root = null;
        try
        {
            root = loader.load();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
        scene = new Scene(root);
    }

    public Scene getScene() {
        return scene;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
