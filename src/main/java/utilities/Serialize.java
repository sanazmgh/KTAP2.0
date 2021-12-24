package utilities;

import apps.page.profile.view.ProfilePane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.LinkedList;

public class Serialize {
    public static Serialize serialize;
    private final LinkedList<Pair<Serializable, String>> steps = new LinkedList<>();

    private Serialize() {};

    public static Serialize getSerialize()
    {
        if(serialize == null)
            serialize = new Serialize();

        return serialize;
    }

    public Pane lastMove()
    {
        steps.removeLast();
        return steps.getLast().getKey().getPane();
    }

    public void addStep(Serializable serializable , String type)
    {
        steps.add(new Pair(serializable , type));
    }

    public void clear()
    {
        steps.clear();
    }
}
