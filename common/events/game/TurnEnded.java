package events.game;

import communication.ActionEnum;
import events.ExternalEvent;
import events.game.interop.TurnEnded.TurnEndedInterop;
import events.interop.base.InteropEvent;
import game.PlayerInformation;

/**
 * Created by Lukas on 11.11.2016.
 */
public class TurnEnded extends ExternalEvent {

    PlayerInformation activePlayer;

    public TurnEnded(PlayerInformation activePlayer){
        super(ActionEnum.TURN_END);
        this.activePlayer = activePlayer;
    }

    @Override
    public InteropEvent makeIOEvent(int activePlayer, int playerPerspective) {
        return new TurnEndedInterop(activePlayer);
    }
}
