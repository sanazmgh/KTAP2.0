package apps.page.profile.edit.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import utilities.Config;

import java.io.IOException;
import java.util.Objects;

public class EditPane {
    private Pane pane ;
    private final FXMLLoader loader;

    public EditPane(){
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Config.getConfig("FXMLDirectories").getProperty(String.class, "editPane")))); //TODO config directory

        try
        {
            pane = loader.load();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Pane getPane() {
        return pane;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
