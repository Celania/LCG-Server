package units;

import game.Game;

import java.util.LinkedList;
import java.util.List;

import communication.Action;
import communication.ActionEnum;
import stores.DBStore;

public class PlayedUnit{

	private Unit baseUnit;	//final? what happens if we want to change a creature
	//private List<Integer> activeEffectsID;	// ToDo
	
	private int ID;
	private int statusMask = 0;
	private int health;
	private int attack;
	private int maxHealth;
	private boolean alive; //mark creature as dead and have game remove dead creatures?
			
	public PlayedUnit(Game game, int baseID) { //context(board) creature is created in for id retrieval?
		baseUnit = DBStore.getUnit(baseID);
		ID = game.getNewPlayedUnitID();
		health = baseUnit.getBaseHealth();
		maxHealth = baseUnit.getBaseHealth();
		attack = baseUnit.getBaseAttack();
		statusMask = baseUnit.getBaseStatusMask();
		alive = true;
	}
	
	public void addEffect(int trigger, int effect, int param1,
			  int param2, int duration) {

	}
	
	public void addStatus(int status){
		this.statusMask |= status;
		//EventManager event
	}	
	
	public boolean hasStatus(int status){
		return ((this.statusMask & status) == status);
	}
	
	public void removeStatus(int status){
		this.statusMask &= ~status;
	}
	
	public List<Action> alterHealth(int amount){
		List<Action> result = new LinkedList<Action>();
		result.clear();
		if (amount < 0)
			result = getDamaged(amount);
		else
			result = getHealed(amount);
		return result;
	}
	
	private List<Action> getDamaged(int amount){
		List<Action> result = new LinkedList<Action>();
		health -= amount;
		result.add(new Action(ActionEnum.CREATURE_DAMAGE, amount, this.ID));
		//Event (DAMAGED, this, amount)
		if (health < 0)
			result.addAll(die());
		return result;
	}
	
	private List<Action> getHealed(int amount){
		List<Action> result = new LinkedList<Action>();
		health += amount % maxHealth;
		result.add(new Action(ActionEnum.CREATURE_HEAL, amount, this.ID));
		//Event (HEALED, this, amount)
		return result;
	}
	
	public List<Action> die(){
		List<Action> result = new LinkedList<Action>();
		
		//remove unit from board
		alive = false;
		
		//Event(DEATH, this)
				
		return result;
	}
	
	public int getID(){
		return ID;
	}

	public int getAttack(){
		return this.attack;
	}
	
	public boolean isAlive(){
		return alive;
	}
}
