package effects.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import communication.ActionEnum;
import events.Event;
import game.PlayerInformation;
import manager.EffectManager;
import trigger.Trigger;
import units.PlayedUnit;
import units.info.SourceRestriction;
import units.info.TargetRestriction;
import units.info.UnitRestriction;

public class PermEffect {

    private static Random random = new Random(System.currentTimeMillis());

    private Trigger trigger;
    private Optional<PlayedUnit> source;
    private PlayerInformation owner;


    private int effectMask;

    private List<SpellEffectGroup> spellEffects;
    private UnitRestriction        sourceRestriction;
    private TargetRestriction      targetRestriction;

    private static int effectLifetimeBits = 0b01100000000000000000000000000000;
    private static int effectChanceBits   = 0b00010000000000000000000000000000;

    static class EffectLifetime {
        static final int MASK             = 0b00000000000000000000000000000011 << 30;
        static final int ONCE             = 0b00000000000000000000000000000000 << 30;
        static final int DEATH            = 0b00000000000000000000000000000001 << 30;
        static final int DEATH_PERSISTENT = 0b00000000000000000000000000000010 << 30;
    }

    static class EffectChance{
        static final int MASK            = 0b00000000000000000000000000000011 << 28;
        static final int ALWAYS         = 0b00000000000000000000000000000000 << 28;
        static final int HALF            = 0b00000000000000000000000000000001 << 28;
        static final int THREEQUARTER    = 0b00000000000000000000000000000010 << 28;
        static final int QUARTER          = 0b00000000000000000000000000000011 << 28;
    }

    public PermEffect(PlayedUnit source, List<SpellEffectGroup> spellEffects, int effectMask, TargetRestriction targetRestriction, SourceRestriction sourceRestriction) {

        this.spellEffects = spellEffects;
        this.source = Optional.of(source);
        this.effectMask = effectMask;
        this.sourceRestriction = sourceRestriction;
        this.targetRestriction = targetRestriction;
    }

    public PermEffect(PermEffect permEffect, PlayedUnit source) {

        this.trigger = permEffect.trigger;
        this.effectMask = permEffect.effectMask;
        this.sourceRestriction = permEffect.sourceRestriction;
        this.targetRestriction = permEffect.targetRestriction;
        this.source = Optional.of(source);
    }

    public PermEffect(PermEffect permEffect, PlayerInformation owner){
        this.trigger = permEffect.trigger;
        this.effectMask = permEffect.effectMask;
        this.sourceRestriction = permEffect.sourceRestriction;
        this.targetRestriction = permEffect.targetRestriction;
        this.owner = owner;
    }

    public void trigger(EffectManager effectManager){
        switch (effectMask & EffectChance.MASK){
            case EffectChance.QUARTER: if (random.nextInt(4) == 0) break; else return;
            case EffectChance.HALF: if (random.nextInt(2) == 0) break; else return;
            case EffectChance.THREEQUARTER: if (random.nextInt(4) == 3) return; else break;
            case EffectChance.ALWAYS: break;
        }

        //TODO
        for (SpellEffectGroup effectGroup : spellEffects){
            effectGroup.undirectedApply(effectManager, targetRestriction, getOwner(), effectManager.getEnemy(getOwner()));
        }
    }

    public boolean doesTrigger(Event event){ return trigger.doesTrigger(event, getOwner()); }

    public ActionEnum getTriggerAction(){
        return trigger.getEventID();
    }

    public Optional<PlayedUnit> getSource(){
        return source;
    }

    public PlayerInformation getOwner(){
        if (source.isPresent()) return source.get().getOwner();
        else return owner;
    }

    public boolean expiresNever(){
        return (effectMask & EffectLifetime.MASK) == EffectLifetime.DEATH_PERSISTENT;
    }

    public boolean expiresOnOnce(){
        return (effectMask & EffectLifetime.MASK) == EffectLifetime.ONCE;
    }

    public boolean expireOnDeath(){
        return (effectMask & EffectLifetime.MASK) == EffectLifetime.DEATH;
    }

    public void removeSource(){
        this.source = Optional.empty();
    }


}
