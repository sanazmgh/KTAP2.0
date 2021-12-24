package apps.page.messenger.compose.listener;

import apps.page.messenger.compose.controller.ComposeController;
import apps.page.messenger.compose.event.ComposeEvent;

public class ComposeListener {
    private final ComposeController controller;

    public ComposeListener(ComposeController controller)
    {
        this.controller = controller;
    }

    public void eventOccurred(ComposeEvent event)
    {
        controller.compose(event);
    }
}
