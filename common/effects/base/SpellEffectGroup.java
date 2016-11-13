package effects.base;

import java.util.ArrayList;
import java.util.List;

import effects.base.SpellEffect;
import events.Event;
import game.PlayerInformation;
import manager.EffectManager;
import units.Card;
import units.PlayedUnit;
import units.info.SpellTargetable;
import units.info.TargetRestriction;
import units.info.EffectTargetable;

public class SpellEffectGroup {

    private List<SpellEffect> spellEffects;
    // TargetRestriction must always be set as the parent
    private TargetRestriction targetRestriction;
    // spellEffectMask with isTargeted/Prolif/ProlifAmount
    private int               spellEffectMask;
    public final boolean      isTargeted;

    public SpellEffectGroup(List<SpellEffect> spellEffects, int spellEffectMask, TargetRestriction targetRestriction,
            boolean isTargeted) {
        this.spellEffects = spellEffects;
        this.spellEffectMask = spellEffectMask;
        this.targetRestriction = targetRestriction;
        this.isTargeted = isTargeted;
    }

    public List<SpellEffect> getEffects() {
        return spellEffects;
    }

    public void apply(EffectManager effectManager, PlayedUnit target, PlayerInformation caster) {
        // check whether the target is valid for this group. return null if not
        if (!targetRestriction.isValidUnit(target, caster)) return;

        for (SpellEffect spellEffect : spellEffects) {
            spellEffect.apply(effectManager, caster, target);
        }
    }

    public void apply(EffectManager effectManager, PlayerInformation target, PlayerInformation caster) {
        // check whether the target is valid for this group. return null if not
        if (!targetRestriction.isValidUnit(target, caster)) return;

        List<Event> result = new ArrayList<>();
        for (SpellEffect spellEffect : spellEffects) {
            spellEffect.apply(effectManager, caster, target);
        }
    }

    // TODO is the spells target restriction required?
    public void undirectedApply(EffectManager effectManager, List<? extends SpellTargetable> possibleTargets,
            PlayerInformation caster, PlayerInformation enemy) {

        // possibleTargets is always non-empty
        if (targetRestriction == null) {
            // the SpellGroup inherits its restriction from its parent spell
            for (SpellTargetable target : possibleTargets)
                for (SpellEffect spellEffect : spellEffects) {
                    target.applySpellEffect(spellEffect, caster);
                }

        } else switch (targetRestriction.targetMask & TargetRestriction.Unit.MASK) {
            case TargetRestriction.Unit.UNIT:
                List<PlayedUnit> unitTargets = targetRestriction.getUnitTargets(caster, enemy);

                for (SpellEffect spellEffect : spellEffects) {
                    spellEffect.apply(effectManager, caster, unitTargets);
                }
                break;
            case TargetRestriction.Unit.PLAYER:
                List<PlayerInformation> playerTargets = new ArrayList<>();

                for (SpellEffect spellEffect : spellEffects) {
                    for (PlayerInformation playerTarget : playerTargets)
                        spellEffect.apply(effectManager, caster, playerTarget);
                }
                break;
            default:
//            case TargetRestriction.Unit.:
//                Card cardTarget = null;
//
//                for (SpellEffect spellEffect : spellEffects) {
//                    spellEffect.apply(effectManager, caster, cardTarget);
//                }
//                break;
        }
    }

    public void undirectedApply(EffectManager effectManager, TargetRestriction parentTargetRestriction,
                                       PlayerInformation caster, PlayerInformation enemy){

        TargetRestriction usedTargetRestriction = null;

        if (targetRestriction != null)
            usedTargetRestriction = targetRestriction;
        else
        {
            if (parentTargetRestriction != null)
                usedTargetRestriction = parentTargetRestriction;
            else
                System.out.println("Should not happen. we need either a parent restriction or a direct one");
        }

        List<Event> result = new ArrayList<>();
        List<EffectTargetable> targets = usedTargetRestriction.getTargets(caster, enemy);

        for (EffectTargetable target : targets){
            for (SpellEffect spellEffect : spellEffects){
                spellEffect.apply(effectManager, caster, target);
            }
        }
    }

    public void undirectedApply(EffectManager effectManager, PlayerInformation caster,
                                       PlayerInformation enemy){

    }
}
