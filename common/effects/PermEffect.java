package effects;

public class PermEffect implements Effect{

	private int param1;
	private int param2;
	private Effect effect;
	
	public String getDescription() {
		return effect.getDescription();
	}
	
	public void apply() {
		effect.apply();
	}

	public void undo() {
		effect.undo();
	}
	
}
