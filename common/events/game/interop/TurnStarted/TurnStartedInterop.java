package events.game.interop.TurnStarted;

import events.game.TurnStarted;
import events.interop.base.InteropEvent;
import events.interop.base.InteropEventHandler;

/**
 * Created by Lukas on 11.11.2016.
 */
public class TurnStartedInterop implements InteropEvent {

    public final int turnStartedPlayerID;

    public TurnStartedInterop(final int turnStartedPlayerID){
        this.turnStartedPlayerID = turnStartedPlayerID;
    }

    @Override
    public void handleInteropEvent(InteropEventHandler eventIOHandler) {
        eventIOHandler.handleInteropEvent(this);
    }
}
