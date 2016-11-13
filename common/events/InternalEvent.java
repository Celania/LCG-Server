package events;

import communication.ActionEnum;

/**
 * Created by Lukas on 15.06.2016.
 */
public class InternalEvent implements Event{

    final ActionEnum eventID;

    public InternalEvent(ActionEnum eventID){
        this.eventID = eventID;
    }

    public ActionEnum getEventID(){
        return eventID;
    }

}
