package game;

import events.Event;
import events.ExternalEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 05.07.2016.
 */
public class EventResult {

    private List<ExternalEvent> events = new ArrayList<>();

    public void addEvents(List<ExternalEvent> events){
        this.events.addAll(events);
    }

    public void clear(){
        events.clear();
    }

    public List<ExternalEvent> getEvents(){
        return events;
    }

}
