package apps.page.view;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import listeners.StringListener;

public class MainPageFXMLController {

    @FXML
    Pane middlePane;

    StringListener listener;

    public Pane getMiddlePane() {
        return middlePane;
    }

    public void setListener(StringListener listener) {
        this.listener = listener;
    }

    public void viewProfile()
    {
        listener.eventOccurred("Profile");
    }

    public void viewTimeline()
    {
        listener.eventOccurred("Timeline");
    }

    public void viewExplorer()
    {
        listener.eventOccurred("Explorer");
    }

    public void viewMessenger()
    {
        listener.eventOccurred("Messenger");
    }

    public void viewSettings()
    {
        listener.eventOccurred("Settings");
    }
}
