package events.game;

import communication.ActionEnum;
import events.ExternalEvent;
import events.game.interop.TurnStarted.TurnStartedInterop;
import events.interop.base.InteropEvent;
import game.PlayerInformation;

/**
 * Created by Lukas on 11.11.2016.
 */
public class TurnStarted extends ExternalEvent {

    PlayerInformation activePlayer;

    public TurnStarted(PlayerInformation activePlayer){
        super(ActionEnum.TURN_START);
        this.activePlayer = activePlayer;
    }


    @Override
    public InteropEvent makeIOEvent(int activePlayer, int playerPerspective) {
        return new TurnStartedInterop();
    }
}
