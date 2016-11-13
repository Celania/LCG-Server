package events.game.interop.CreatureStatusGainedInterop;

import events.interop.base.InteropEvent;
import events.interop.base.InteropEventHandler;

/**
 * Created by Lukas on 28.10.2016.
 */
public class CreatureStatusGainedInterop implements InteropEvent {

    private int creaturePos;
    private int playerID;
    private int status;

    public CreatureStatusGainedInterop(int playedID, int creaturePos, int status){
        this.creaturePos =creaturePos;
        this.playerID =playedID;
        this.status =status;
    }

    @Override
    public void handleInteropEvent(InteropEventHandler eventIOHandler) {
        eventIOHandler.handleInteropEvent(this);
    }

}