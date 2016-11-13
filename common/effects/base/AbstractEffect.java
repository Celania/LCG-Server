package effects.base;

import events.ActionResult;
import events.Event;
import game.PlayerInformation;

import java.util.List;

import manager.EffectManager;
import units.Card;
import units.PlayedUnit;
import units.info.EffectTargetable;

public abstract class AbstractEffect {

    // An Effect that implements TargetsUnits supports applying its effect to Units
    public interface TargetsUnits {
        void _apply(EffectManager effectManager, PlayedUnit target, int param1, int param2);
        void _undo(EffectManager effectManager, PlayedUnit target, int param1, int param2);
    }

    // An Effect that implements TargetsPlayers supports applying its effect to players
    public interface TargetsPlayers {
        void _apply(EffectManager effectManager, PlayerInformation target, int param1, int param2);
        void _undo(EffectManager effectManager, PlayedUnit target, int param1, int param2);
    }

    // An Effect that implements TargetsPlayers supports applying its effect to players
    public interface TargetsCards {
        void _apply(EffectManager effectManager, Card target, int param1, int param2);
        void _undo(EffectManager effectManager, PlayedUnit target, int param1, int param2);
    }

	public abstract String getDescription(int param1, int param2);

	public void apply(EffectManager effectManager, EffectTargetable target, int param1, int param2){
		target.applyEffect(effectManager, this, param1, param2);
    }

    public void apply(EffectManager effectManager, PlayedUnit target, int param1, int param2){
        if (this instanceof TargetsUnits)
            ((TargetsUnits) this)._apply(effectManager, target, param1, param2);
        else{
            // This effect cannot be applied on units
        }
    }

    public void apply(EffectManager effectManager, PlayerInformation target, int param1, int param2){
        if (this instanceof TargetsPlayers)
            ((TargetsPlayers) this)._apply(effectManager, target, param1, param2);
        else{
            // This effect cannot be applied on players
        }
    }

    public void apply(EffectManager effectManager, Card target, int param1, int param2){
        if (this instanceof TargetsCards)
            ((TargetsCards) this)._apply(effectManager, target, param1, param2);
        else{
            // This effect cannot be applied on cards
        }
    }

    public void undo(EffectManager effectManager, EffectTargetable target, int param1, int param2){

    }

}
