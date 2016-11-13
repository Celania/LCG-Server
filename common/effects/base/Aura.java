package effects.base;

import communication.ActionEnum;
import effects.base.SpellEffectGroup;
import units.info.TargetRestriction;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by Lukas on 15.06.2016.
 */
public class Aura {

    private final EnumSet<ActionEnum> relevantEvents;
    private List<SpellEffectGroup> spellEffects;
    private TargetRestriction targetRestriction;

    public Aura(EnumSet<ActionEnum> relevantEvents){
        this.relevantEvents = relevantEvents;
    }
}


