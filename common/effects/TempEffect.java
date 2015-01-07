package effects;

import communication.ActionEnum;

public class TempEffect extends PermEffect {

	public TempEffect(Effect effect, ActionEnum trigger, int param1, int param2, int duration) {
		super(effect, trigger, param1, param2);
		this.duration = duration;
	}

	private int duration;
	
}
