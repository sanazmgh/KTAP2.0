package apps.authentication.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import utilities.Config;

import java.io.IOException;
import java.util.Objects;

public class LoginScene{
    private final Scene scene ;
    private final FXMLLoader loader;

    public LoginScene(){
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Config.getConfig("FXMLDirectories").getProperty(String.class, "loginScene"))));//TODO config directory
        Parent root = null;
        try
        {
            root = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.scene = new Scene(root);
    }

    public Scene getScene() {
        return this.scene;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
