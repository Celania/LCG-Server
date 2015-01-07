package units;

import effects.Effect;

public abstract class Card {

	private int baseID;
	private int baseManaCost;
	private String name;
	private String description;
	
	protected Card(int baseID, String name, int baseManaCost, String description){
		this.baseID = baseID;
		this.name = name;
		this.baseManaCost = baseManaCost;
		this.description = description;
	}
}
