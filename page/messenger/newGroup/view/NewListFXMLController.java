package apps.page.messenger.newGroup.view;

import apps.page.messenger.newGroup.listener.NewListListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewListFXMLController {
    private NewListListener listener;
    private int type;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField usernameTextField;

    public void setListener(NewListListener listener)
    {
        this.listener = listener;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void create()
    {
        listener.eventOccurred(nameTextField.getText() , usernameTextField.getText() , type);
    }
}
