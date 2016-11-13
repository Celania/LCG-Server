package effects.base;

import java.util.List;

import effects.base.GeneralEffect;
import game.PlayerInformation;
import manager.EffectManager;
import units.Card;
import units.PlayedUnit;
import units.info.EffectTargetable;

public class SpellEffect {
    // manual overrides for targetRestriction?
    // For Example: SpellEffectGroup takes the target: abstractEffect one handles target and abstractEffect two handles the target
    // only if an additional criteria is met. Is the additional space requirement worth the minor improvement to
    // target selection?

    // private UnitRestriction targetRestriction;
    private GeneralEffect effect;
    private int    param1, param2;
    // TODO move to group?
    private int    repeat;

    public SpellEffect(GeneralEffect effect, /* UnitRestriction targetRestriction, */ int param1, int param2, int repeat) {
        this.effect = effect;
        // this.targetRestriction = targetRestriction;
        this.param1 = param1;
        this.param2 = param2;
        this.repeat = repeat;
    }

    public void apply(EffectManager effectManager, PlayerInformation caster, List<PlayedUnit> targets) {
        // if (targetRestriction.isValidUnit(target, caster))
        for (int i = 0; i < repeat; i++) {
            for (PlayedUnit target : targets)
                effect.apply(effectManager, target, param1, param2);
            // TODO handle reactionary events based off of the current iteration of spellEffect
        }
    }

    public void apply(EffectManager effectManager, PlayerInformation caster, PlayedUnit target){
        for (int i = 0; i < repeat; i++)
           effect.apply(effectManager, target, param1, param2);

    }

    public void apply(EffectManager effectManager, PlayerInformation caster, PlayerInformation target) {

        // if (targetRestriction.isValidUnit(target, caster))
        for (int i = 0; i < repeat; i++)
            effect.apply(effectManager, target, param1, param2);

    }

    public void apply(EffectManager effectManager, PlayerInformation caster, Card target) {
        // if (targetRestriction.isValidUnit(target, caster))
        for (int i = 0; i < repeat; i++)
            effect.apply(effectManager, target, param1, param2);

    }

    public void apply(EffectManager effectManager, PlayerInformation caster, EffectTargetable target){
        for (int i = 0; i < repeat; i++)
            effect.apply(effectManager, target, param1, param2);
    }

}