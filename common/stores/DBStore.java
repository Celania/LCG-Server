package stores;

import java.util.Vector;

import units.Spell;
import units.Unit;

public class DBStore {

	Vector<Unit> units;
	Vector<Spell> spells;	
	// Vector<Hero> heroes;
	
	public Vector<Unit> getUnits() {
		return units;
	}

	public Vector<Spell> getSpells() {
		return spells;
	}

	public DBStore() {
		
	}
	
	public void loadFile(String fileName) {
		
	}

}
