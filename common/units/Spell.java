package units;

import java.util.List;

import effects.base.AbstractEffect;
import effects.base.SpellEffectGroup;
import events.Error;
import events.ErrorType;
import events.game.SpellPlayed;
import game.PlayerInformation;
import manager.EffectManager;
import manager.EventSource;
import units.BaseCard.CardType;
import units.info.EffectTargetable;
import units.info.SpellTargetable;

public class Spell extends Card implements EffectTargetable, SpellInfo, EventSource {

    BaseSpell baseSpell;

    public Spell(PlayerInformation owner, BaseSpell baseSpell) {
        super(owner);
        this.baseSpell = baseSpell;
    }

    @Override
    public int getCardType() {
        return CardType.Spell;
    }

    @Override
    public int getManaCost() {
        return baseSpell.getManaCost() + this.modifyMana;
    }

    @Override
    public int getBaseID() {
        return baseSpell.getBaseID();
    }

    @Override
    public String getName() {
        return baseSpell.getName();
    }

    @Override
    public String getDescription() {
        return baseSpell.getDescription();
    }

    @Override
    public List<SpellEffectGroup> getEffects() {
        return baseSpell.effects;
    }

    @Override
    public boolean isTargeted() {
        return baseSpell.isTargeted;
    }

    public boolean isValidTarget(PlayedUnit target) {
        return baseSpell.isValidTarget(target, getOwner());
    }

    public boolean isValidTarget(PlayerInformation target) {
        return baseSpell.isValidTarget(target, getOwner());
    }

    public void cast(EffectManager effectManager, SpellTargetable target, PlayerInformation enemy) {
        // TODO sourceRestriction check

        if (isTargeted()) {
            if (target == null)
                effectManager.fireEvent(new Error(ErrorType.NeedsTarget));
            else if (!target.isValidForSpell(this))
                effectManager.fireEvent(new Error(ErrorType.InvalidTarget));
            else target.applySpell(this);
        } else undirectedApply(effectManager, enemy);

    }

    public void directedApply(EffectManager effectManager, PlayedUnit target) {
        effectManager.fireEvent(new SpellPlayed(this, null));
        // TODO if the spell has been cancelled, dont add the effects

        for (SpellEffectGroup spelleffects : baseSpell.effects) {
            spelleffects.apply(effectManager, target, getOwner());
        }
    }

    public void directedApply(EffectManager effectManager, PlayerInformation target) {

        effectManager.fireEvent(new SpellPlayed(this, null));
        // TODO if the spell has been cancelled, dont add the effects

        for (SpellEffectGroup spelleffects : baseSpell.effects) {
            spelleffects.apply(effectManager, target, getOwner());
        }
    }

    public void undirectedApply(EffectManager effectManager, PlayerInformation enemy){
        for (SpellEffectGroup spellGroup : getEffects()){
            spellGroup.undirectedApply(effectManager, baseSpell.targetRestriction, getOwner(), enemy);
        }
    }

    @Override
    public void applyEffect(EffectManager effectManager, AbstractEffect abstractEffect, int param1, int param2) {

    }

    @Override
    public void undo(EffectManager effectManager, AbstractEffect abstractEffect, int param1, int param2) {

    }
}
