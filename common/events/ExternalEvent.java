package events;

import communication.ActionEnum;
import events.interop.base.InteropEvent;

/**
 * Created by Lukas on 24.06.2016.
 */
public abstract class ExternalEvent extends InternalEvent {

    public ExternalEvent(ActionEnum eventID){
        super(eventID);
    }

    abstract public InteropEvent makeIOEvent(int activePlayer, int playerPerspective);
}
