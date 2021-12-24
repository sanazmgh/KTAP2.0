package apps.page.messenger.chatPane.message.controller;

import apps.authentication.model.User;
import apps.page.messenger.chatPane.message.model.Message;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageController {
    private final Message message;
    private final User user;
    private final Context context = Context.getContext();
    static private final Logger logger = LogManager.getLogger(MessageController.class);

    public MessageController(Message message , User user)
    {
        this.message = message;
        this.user = user;
    }

    public String getName()
    {
        User sender = context.users.get(message.getSenderId());
        return sender.getName() + " " + sender.getLastName();
    }

    public String getInfo()
    {
        String str = "";

        if(!message.getExtraInfo().equals(""))
            str += (message.isForwarded() ? "Forwarded from : " : "A tweet from : ") + message.getExtraInfo();

        return str;
    }

    public String getText()
    {
        return message.getText();
    }

    public String getProfile()
    {
        return user.getImagePath();
    }

    public String getTweetImage()
    {
        return message.getImagePath();
    }

    public void editText(String string)
    {
        logger.warn(user.getId() + " edited message : " + message.getId());

        message.setText(string);
        context.messagesDB.update(message);
    }

    public boolean isEditable()
    {
        return (user.getId() == message.getSenderId() && message.getExtraInfo().equals(""));
    }

    public boolean isDeleted()
    {
        return message.isDeleted();
    }

    public void deleteMessage()
    {
        logger.warn(user.getId() + " deleted message : " + message.getId());

        message.setDeleted(true);
        context.messagesDB.update(message);
    }
}
