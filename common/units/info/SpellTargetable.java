package units.info;

import effects.base.SpellEffect;
import events.Event;
import game.PlayerInformation;
import units.Spell;

import java.util.List;

/**
 * Created by Lukas on 12.06.2016.
 */
public interface SpellTargetable {

    void applySpell(Spell spell);

    void applySpellEffect(SpellEffect spellEffect, PlayerInformation caster);

    boolean isValidForSpell(Spell spell);

}
