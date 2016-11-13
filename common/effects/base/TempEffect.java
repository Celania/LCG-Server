package effects.base;

import units.PlayedUnit;
import communication.ActionEnum;
import units.info.SourceRestriction;
import units.info.TargetRestriction;

import java.util.List;

public class TempEffect extends PermEffect {

	private int duration;
	
	public TempEffect(PlayedUnit source, List<SpellEffectGroup> spellEffects, int effectMask, TargetRestriction targetRestriction, SourceRestriction sourceRestriction, int duration) {
		super(source, spellEffects, effectMask, targetRestriction, sourceRestriction);
		this.duration = duration;
	}

	public TempEffect(TempEffect effect, PlayedUnit source) {
		super(effect, source);
		duration = effect.duration;
	}
	
	public void roundPassed(){
	    if(duration > 0)
	        duration--;
	}
	
	public boolean hasExpired(){
	    return duration == 0;
	}
	
}
