package apps.page.messenger.chatPane.listener;

import apps.page.messenger.chatPane.controller.ChatController;
import apps.page.messenger.chatPane.events.MessageEvent;
import apps.page.messenger.chatPane.message.model.Message;

public class ChatListener {
    private final ChatController controller;

    public ChatListener(ChatController controller)
    {
        this.controller = controller;
    }

    public void eventOccurred(String string)
    {
        controller.addMember(string);
    }

    public void eventOccurred(MessageEvent event)
    {
        Message message = new Message(controller.getUser().getId() , event.getText() , event.getAttachment() , false , "");
        controller.newMessage(message);
    }
}
