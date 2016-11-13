package events.game;

import communication.ActionEnum;
import events.ExternalEventAnon;
import events.game.interop.CardDrawn.CardDrawnInterop;
import events.game.interop.CardDrawn.CardDrawnInteropAnon;
import events.interop.base.InteropEvent;
import game.PlayerInformation;
import units.Card;

/**
 * Created by Lukas on 21.06.2016.
 */
public class CardDrawn extends ExternalEventAnon {

    @Override
    protected InteropEvent makeOwnerIOEvent() {
        return new CardDrawnInterop(card.getBaseID());
    }

    @Override
    protected InteropEvent makeEnemyIOEvent() {
        return new CardDrawnInteropAnon(card.getOwner().getPlayerID());
    }

    private Card card;
    private PlayerInformation player;

    public CardDrawn(Card card, PlayerInformation player){
        super(ActionEnum.CARD_DRAW);
        this.card = card;
    }

}
