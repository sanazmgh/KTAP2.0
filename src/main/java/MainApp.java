import javafx.application.Application;
import javafx.stage.Stage;
import view.MainStage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new MainStage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}