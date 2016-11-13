package units.info;


import effects.base.AbstractEffect;
import manager.EffectManager;

public interface EffectTargetable {

    void applyEffect(EffectManager effectManager, AbstractEffect abstractEffect, int param1, int param2);

    void undo(EffectManager effectManager, AbstractEffect abstractEffect, int param1, int param2);
}
