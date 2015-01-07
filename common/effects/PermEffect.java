package effects;

import communication.ActionEnum;

public class PermEffect implements Effect{

	private int param1;
	private int param2;
	private Effect effect;
	private ActionEnum trigger;
	
	public PermEffect(Effect effect, ActionEnum trigger, int param1, int param2){
		this.effect = effect;
		this.param1 = param1;
		this.param2 = param2;
		this.trigger = trigger;
	}
	
	public String getDescription() {
		return effect.getDescription();
	}
	
	public void apply() {
		effect.apply();
	}

	public void undo() {
		effect.undo();
	}
	
	public ActionEnum getTrigger(){
		return trigger;
	}
	
}
