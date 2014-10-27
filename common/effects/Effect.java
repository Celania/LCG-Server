package effects;

public interface Effect {

	public String getDescription();
	public void apply();
	public void undo();
		
}
