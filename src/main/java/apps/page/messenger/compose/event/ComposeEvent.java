package apps.page.messenger.compose.event;

import java.util.Arrays;
import java.util.LinkedList;

public class ComposeEvent {
    private final String text;
    private final String attachmentPath;
    private final LinkedList<String> names = new LinkedList<>();
    private final boolean forwarded;
    private final String extraInfo;

    public ComposeEvent(String text , String attachmentPath , String names)
    {
        this.text = text;
        this.attachmentPath = attachmentPath;
        String[] parts = names.split("-");
        this.names.addAll(Arrays.asList(parts));
        this.forwarded = false;
        this.extraInfo = "";
    }

    public ComposeEvent(String text, String attachmentPath, String names, boolean forwarded, String extraInfo) {
        this.text = text;
        this.attachmentPath = attachmentPath;
        String[] parts = names.split("-");
        this.names.addAll(Arrays.asList(parts));
        this.forwarded = forwarded;
        this.extraInfo = extraInfo;
    }

    public String getText() {
        return text;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public LinkedList<String> getNames() {
        return names;
    }

    public boolean isForwarded() {
        return forwarded;
    }

    public String getExtraInfo() {
        return extraInfo;
    }
}
