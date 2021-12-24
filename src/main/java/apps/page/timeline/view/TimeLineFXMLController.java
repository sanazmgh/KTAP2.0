package apps.page.timeline.view;

import apps.page.timeline.Listeners.SearchListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class TimeLineFXMLController {

    private SearchListener listener;

    @FXML
    Pane tweetsPane;
    @FXML
    Text typeText;
    @FXML
    TextField searchTextField;
    @FXML
    Button searchButton;
    @FXML
    Pane preView1Pane;
    @FXML
    Text errorText;

    public void setListener(SearchListener listener) {
        this.listener = listener;
    }

    public void setData(Pane middlePane , String type)
    {
        preView1Pane.getChildren().clear();
        preView1Pane.setVisible(false);
        errorText.setVisible(false);

        if(type.equals("Explorer"))
        {
            typeText.setText("Explorer");

            searchButton.setVisible(true);
            searchTextField.setVisible(true);
        }

        else
        {
            typeText.setText("Timeline");

            searchButton.setVisible(false);
            searchTextField.setVisible(false);
        }

        TweetsPane tweets = new TweetsPane();
        FXMLLoader loader = tweets.getLoader();
        TweetsFXMLController FXMLController = loader.getController();
        FXMLController.setData(middlePane , type);
        tweetsPane.getChildren().add(tweets.getPane());
    }

    public void search()
    {
        tweetsPane.getChildren().clear();
        preView1Pane.getChildren().clear();
        preView1Pane.setVisible(true);
        errorText.setVisible(false);
        String str = searchTextField.getText();
        if(listener.eventOccurred(str , preView1Pane) == 1)
        {
            errorText.setVisible(true);
        }
    }
}
