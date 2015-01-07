package game;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import communication.Message;
import connection.Client;
import units.Card;
import units.PlayedUnit;

public class PlayerInformation {

	private int armor;
	private int heroChoice;
	private int lifePoints;
	
	private int maxMana;
	private int mana;
	
	/*how to resolve overloads?*/
	private int overload;
	private int locked;
	
	private int weaponID;
	private int playerID;
	private List<PlayedUnit> board;
	private List<Card> deck;
	private List<Card> hand;
	private Client client;
	public BlockingQueue<Message> outputQueue;

	public PlayerInformation(Client client, int ID){
		this.playerID = ID;
		this.armor = 0;
		this.board = new LinkedList<PlayedUnit>();
		this.deck = new LinkedList<Card>();
		this.hand = new LinkedList<Card>();
		this.heroChoice = client.getHeroChoice();
		this.lifePoints = 30;
		this.maxMana = 0;
		this.mana = 0;
		this.weaponID = 0;
		this.client = client;
		
		this.outputQueue = new LinkedBlockingQueue<Message>();
		
	}
	
	public UUID getID(){
		return client.getID();
	}
	
	public int getHeroChoice(){
		return heroChoice;
	}
	
	public int getPlayerID(){
		return playerID;
	}
	
	public int getMana(){
		return mana;
	}
	
	public int getMaxMana(){
		return maxMana;
	}
	
	public void alterMaxMana(int amount){
		maxMana = (maxMana + amount) % 10;
	}
	
	public void alterMana(int amount){
		mana = (mana + amount) % 10; 
	}
	
	public void resetMana(){
		mana = maxMana - overload;
	}

}
