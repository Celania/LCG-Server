package events;

import communication.ActionEnum;
import events.interop.base.InteropEvent;

/**
 * Created by Lukas on 24.06.2016.
 */
public abstract class ExternalEventAnon extends ExternalEvent {

    public ExternalEventAnon(ActionEnum eventID) {
        super(eventID);
    }

    abstract protected InteropEvent makeOwnerIOEvent();
    abstract protected InteropEvent makeEnemyIOEvent();


    public InteropEvent makeIOEvent(int activePlayer, int playerPerspective){
        if (activePlayer == playerPerspective)
            return makeOwnerIOEvent();
        else
            return makeEnemyIOEvent();
    }


}
