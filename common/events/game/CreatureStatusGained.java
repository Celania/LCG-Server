package events.game;

import communication.ActionEnum;
import events.ExternalEvent;
import events.game.interop.CreatureStatusGainedInterop.CreatureStatusGainedInterop;
import events.interop.base.InteropEvent;
import units.PlayedUnit;

/**
 * Created by Lukas on 10.02.2016.
 */
public class CreatureStatusGained extends ExternalEvent {

    private PlayedUnit target;
    private Object source;
    public final int status;

    public CreatureStatusGained(PlayedUnit target, Object source, final int status){
        super(ActionEnum.CREATURE_STATUS_ADD);
        this.target =target;
        this.source =source;
        this.status =status;
    }


    @Override
    public InteropEvent makeIOEvent(int activePlayer, int playerPerspective) {
        return new CreatureStatusGainedInterop(target.getOwner().getPlayerID(), 0, status);
    }
}
