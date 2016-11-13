package trigger;

import communication.ActionEnum;
import events.Event;
import game.PlayerInformation;
import units.PlayedUnit;

/**
 * Created by Lukas on 22.02.2016.
 */
public abstract class Trigger {

    final ActionEnum eventID;

    public Trigger(ActionEnum eventID){
        this.eventID =eventID;
    }

    public ActionEnum getEventID(){
        return eventID;
    }

    public abstract boolean doesTrigger(Event event, PlayerInformation effectSourceOwner);


}
