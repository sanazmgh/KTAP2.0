package apps.page.timeline.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import utilities.Config;
import utilities.Serializable;

import java.io.IOException;
import java.util.Objects;

public class TimelinePane implements Serializable {
    private Pane pane ;
    private final FXMLLoader loader;

    public TimelinePane(){
        loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Config.getConfig("FXMLDirectories").getProperty(String.class, "timelinePane")))); //TODO config directory

        try {
            pane = loader.load();

        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    @Override
    public FXMLLoader getLoader() {
        return loader;
    }
}
