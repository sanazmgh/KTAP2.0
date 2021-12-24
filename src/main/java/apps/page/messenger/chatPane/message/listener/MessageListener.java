package apps.page.messenger.chatPane.message.listener;

import apps.page.messenger.chatPane.message.controller.MessageController;

public class MessageListener {
    private final MessageController controller;

    public MessageListener(MessageController controller)
    {
        this.controller = controller;
    }

    public void eventOccurred(String string)
    {
        if(string.equals("Deleted message"))
        {
            controller.editText(string);
            controller.deleteMessage();
        }

        else
        {
            controller.editText(string);
        }
    }
}
