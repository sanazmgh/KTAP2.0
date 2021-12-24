package apps.page.messenger.newGroup.listener;

import apps.page.messenger.newGroup.controller.NewListController;

public class NewListListener {
    NewListController controller;

    public NewListListener(NewListController controller)
    {
        this.controller = controller;
    }

    public void eventOccurred(String gpName , String username , int type)
    {
        if(type == 0)
            controller.newList(gpName , username);

        else
            controller.newGroup(gpName , username);
    }
}
