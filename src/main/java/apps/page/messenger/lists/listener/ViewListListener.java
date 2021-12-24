package apps.page.messenger.lists.listener;

import apps.page.messenger.lists.controller.ListsController;

public class ViewListListener {
    private final ListsController controller;

    public ViewListListener(ListsController controller)
    {
        this.controller = controller;
    }

    public void eventOccurred(String string , String username)
    {
        if(string.equals("Add"))
        {
            controller.add(username);
        }

        if(string.equals("Remove"))
        {
            controller.remove(username);
        }
    }
}
