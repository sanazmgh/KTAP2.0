package apps.page.profile.lists.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import utilities.Config;

import java.io.IOException;
import java.util.Objects;

public class ListsPane{
    private Pane pane ;
    private final FXMLLoader loader;

    public ListsPane(){
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Config.getConfig("FXMLDirectories").getProperty(String.class, "listsPane")))); //TODO config directory

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
