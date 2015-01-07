package units;

import effects.Effect;

public class Unit extends Card {

	private final int baseHealth;
	private final int baseAttack;
	//private final Effect battlecry;
	private final int baseStatusMask;
	
	public Unit(int cardID, String name, int baseHealth,
				int baseAttack, int baseManaCost,
				int statusMask, String description){
		super(cardID, name, baseManaCost, description);
		this.baseHealth = baseHealth;
		this.baseAttack = baseAttack;
		this.baseStatusMask = statusMask;
	}
	
	public int getBaseHealth(){
		return baseHealth;
	}
	
	public int getBaseAttack(){
		return baseAttack;
	}
	
	public int getBaseStatusMask(){
		return baseStatusMask;
	}
}
