package effects;

import effects.base.AbstractEffect;
import effects.base.AuraEffect;
import effects.base.GeneralEffect;
import game.PlayerInformation;
import manager.EffectManager;
import units.Card;
import units.PlayedUnit;

public class Gain extends AuraEffect implements AbstractEffect.TargetsUnits{

    private static Gain instance = new Gain();

    public Gain getInstance() {
        return instance;
    }

    private Gain() {
    }

    @Override
    public void _apply(EffectManager effectManager, PlayedUnit target, int param1, int param2) {
        target.alterAttack(param1);
        target.alterMaxHealth(param2);
    }

    @Override
    public void _undo(EffectManager effectManager, PlayedUnit target, int param1, int param2) {

    }

    @Override
    public String getDescription(int param1, int param2) {
        return ("Target Creature gains (+" + param1 + "/+" + param2 + ")!");
    }

}
