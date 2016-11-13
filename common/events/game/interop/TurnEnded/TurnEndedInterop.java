package events.game.interop.TurnEnded;

import events.interop.base.InteropEvent;
import events.interop.base.InteropEventHandler;

/**
 * Created by Lukas on 11.11.2016.
 */
public class TurnEndedInterop implements InteropEvent{

    int turnEndedPlayerID;

    public TurnEndedInterop(int turnEndedPlayerID){
        this.turnEndedPlayerID = turnEndedPlayerID;
    }

    @Override
    public void handleInteropEvent(InteropEventHandler eventIOHandler) {
        eventIOHandler.handleInteropEvent(this);
    }
}
