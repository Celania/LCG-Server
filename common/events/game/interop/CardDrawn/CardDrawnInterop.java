package events.game.interop.CardDrawn;

import events.interop.base.InteropEvent;
import events.interop.base.InteropEventHandler;
import units.Card;

public class CardDrawnInterop implements InteropEvent {

    public final int cardID;

    public CardDrawnInterop(int cardID){
        this.cardID = cardID;
    }

    public void handleInteropEvent(InteropEventHandler interopEventHandler) {
        interopEventHandler.handleInteropEvent(this);
    }

}
