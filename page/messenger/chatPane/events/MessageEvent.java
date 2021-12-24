package apps.page.messenger.chatPane.events;

public class MessageEvent {

    private final String text;
    private final String attachment;

    public MessageEvent(String text , String attachment)
    {
        this.text = text;
        this.attachment = attachment;
    }

    public String getText() {
        return text;
    }

    public String getAttachment() {
        return attachment;
    }
}
