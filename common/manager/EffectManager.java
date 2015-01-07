package manager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import units.PlayedUnit;
import communication.Action;
import communication.ActionEnum;
import effects.PermEffect;
import effects.TempEffect;

public class EffectManager {

	Map<ActionEnum, List<PermEffect>> permEffects;
	Map<ActionEnum, List<TempEffect>> tempEffects;
	
	public void addEffect(int duration, int effect, int param1, int param2) {
		
	}
	
	public void addEffect(int duration, int trigger /* */){
		
	}
	
	public void deleteEffects(int playedUnitID) {
		
	}
	
	public List<Action> fireEvent(ActionEnum action, PlayedUnit source, int param){
		List<Action> result = new LinkedList<Action>();
		
		Iterator<PermEffect> effects = permEffects.get(action).iterator();
		
		PermEffect effect;
		while (effects.hasNext()){
			effect = effects.next();
			if(effect.getTrigger() == action)
				effect.apply();
		}
		
		result.add(new Action(action, param, source.getID()));
		
		return result;
	}
	
	/*public List<Action> fireEvent(Event event, PlayerInformation source, int param){
		
	}*/
}
