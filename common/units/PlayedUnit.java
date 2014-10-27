package units;

import java.util.List;

public class PlayedUnit extends Unit {

	private int ID;
	private List<Integer> activeEffectsID;	// ToDo
	private int statusMask;
	
	public void addEffect(int trigger, int effect, int param1, int param2, int duration) {
		
	}
	
	public PlayedUnit() {
		
	}
	
}
