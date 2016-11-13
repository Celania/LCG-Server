package events;

import communication.Action;

import java.util.List;

/**
 * Created by Lukas on 08.02.2016.
 */
public class ActionResult {

    private List<Action> actions;
    private List<Event> events;

    public ActionResult(List<Action> actions, List<Event> events){
        this.actions = actions;
        this.events = events;
    }

    public List<Action> getActions(){
        return actions;
    }

    public List<Event> getEvents(){
        return events;
    }
}
