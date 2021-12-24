package apps.page.messenger.lists.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import utilities.Config;

import java.io.IOException;
import java.util.Objects;

public class ListsViewPane {
    private Pane pane ;
    private final FXMLLoader loader;

    public ListsViewPane(){
        loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Config.getConfig("FXMLDirectories").getProperty(String.class, "listsViewPane")))); //TODO config directory

        try {
            pane = loader.load();

        }

        catch (IOException e) {
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
