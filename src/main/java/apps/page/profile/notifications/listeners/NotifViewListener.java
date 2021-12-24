package apps.page.profile.notifications.listeners;

import apps.page.profile.notifications.controller.NotifViewController;

public class NotifViewListener {
    private final NotifViewController controller;

    public NotifViewListener(NotifViewController controller) {
        this.controller = controller;
    }

    public void eventOccurred(String string)
    {
        if(string.equals("Accept"))
            controller.accept();

        if(string.equals("NotifiedDeny"))
            controller.deny(true);

        if(string.equals("NonNotifiedDeny"))
            controller.deny(false);
    }
}
