package apps.page.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import utilities.Config;

import java.io.IOException;
import java.util.Objects;

public class MainPageScene {
    private final Scene scene ;
    private final FXMLLoader loader;

    public MainPageScene(){
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Config.getConfig("FXMLDirectories").getProperty(String.class, "mainPageScene")))); //TODO config directory
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


