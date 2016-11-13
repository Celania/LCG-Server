package events.game.interop.CardDrawn;

import events.interop.base.InteropEvent;
import events.interop.base.InteropEventHandler;

public class CardDrawnInteropAnon implements InteropEvent {

    private int playerID;

    public CardDrawnInteropAnon(int playerID) {
        this.playerID = playerID;
    }

    public void handleInteropEvent(InteropEventHandler interopEventHandler) {
        interopEventHandler.handleInteropEvent(this);
    }

}