package units;

import java.util.List;

import effects.Effect;

public class Spell extends Card {

	public Spell(int baseID, String name, int baseManaCost, String description /*, List<Effect> battlecry*/) {
		super(baseID, name, baseManaCost, description);
	}

	private List<Effect> battlecry;
	
}
