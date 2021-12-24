package apps.page.settings.listeners;

import apps.page.settings.controller.SettingsController;
import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SettingsListener {
    SettingsController controller;
    static private final Logger logger = LogManager.getLogger(SettingsListener.class);


    public SettingsListener(SettingsController controller)
    {
        this.controller = controller;
    }

    public void eventOccurred(String string)
    {
        if(string.equals("Public"))
            controller.setPrivacy(false);

        if(string.equals("Private"))
            controller.setPrivacy(true);

        if(string.equals("Nobody"))
            controller.setLastSeen(0);

        if(string.equals("Everybody"))
            controller.setLastSeen(1);

        if(string.equals("Followers"))
            controller.setLastSeen(1);

        if(string.equals("Log out"))
        {
            logger.warn("User : " + controller.getUser().getId() + " logged out");
            Platform.exit();
        }

    }

    public int eventOccurred(String string , String pass)
    {
        if(string.equals("Delete account"))
            return controller.deleteAccount(pass);

        else if(string.equals("Deactive account"))
            return controller.deactiveAccount(pass);

        return 0;
    }
}
