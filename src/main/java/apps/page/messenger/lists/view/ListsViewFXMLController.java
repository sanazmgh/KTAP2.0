package apps.page.messenger.lists.view;

import apps.page.messenger.lists.controller.ListsController;
import apps.page.messenger.lists.listener.ViewListListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ListsViewFXMLController {

    private ViewListListener listListener;
    private ListsController controller;

    @FXML
    private Text listNameText;
    @FXML
    private Text usersNameText;
    @FXML
    private TextField addTextField;
    @FXML
    private TextField removeTextField;

    public void setListListener(ViewListListener listListener) {
        this.listListener = listListener;
    }

    public void setController(ListsController controller) {
        this.controller = controller;
    }

    public void setData()
    {
        listNameText.setText(controller.getName());
        usersNameText.setText(controller.getMembers());
    }

    public void add()
    {
        listListener.eventOccurred("Add" , addTextField.getText());
    }

    public void remove()
    {
        listListener.eventOccurred("Remove" , removeTextField.getText());
    }
}
