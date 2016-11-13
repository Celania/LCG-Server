package events.game;

import communication.ActionEnum;
import events.ExternalEvent;
import events.interop.base.InteropEvent;
import units.Spell;
import units.info.SpellTargetable;

/**
 * Created by Lukas on 07.03.2016.
 */
public class SpellPlayed extends ExternalEvent {

    Spell spell;
    SpellTargetable target;

    public SpellPlayed(Spell spell, SpellTargetable target){
        super(ActionEnum.SPELL_PLAY);
        this.spell = spell;
        this.target = target;
    }

    @Override
    public InteropEvent makeIOEvent(int activePlayer, int playerPerspective) {
        return null;
    }
}
