package events.game.interop.CreatureDamageTaken;

import events.interop.base.InteropEvent;
import events.interop.base.InteropEventHandler;

/**
 * Created by Lukas on 28.10.2016.
 */
public class CreatureDamageTakenInterop implements InteropEvent {

    private int dmg;
    private int playerID;
    private int creaturePos;

    public void handleInteropEvent(InteropEventHandler interopEventHandler) {
        interopEventHandler.handleInteropEvent(this);
    }

    public CreatureDamageTakenInterop(int playerID, int dmg, int creaturePos){
        this.dmg = dmg;
        this.playerID =playerID;
        this.creaturePos =creaturePos;
    }


}
