package listeners;

import javafx.stage.Stage;

public class EnterMainPageListener implements StringListener {

    Stage stage;
    public EnterMainPageListener (Stage stage)
    {
        this.stage = stage;

    }

    @Override
    public void eventOccurred(String string) {

    }
}
