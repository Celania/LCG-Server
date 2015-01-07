package game;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import manager.EffectManager;
import communication.Action;
import communication.ActionEnum;
import communication.Command;
import communication.Message;
import communication.ServerCommand;
import connection.Client;
import connection.ConnectionStatus;

public class Game extends Thread{
	
	private static long roundTime = 90000;

	private int gameID;
	
	private EffectManager effectManager;
	
	private PlayerInformation player1, player2;
	private boolean turn;
	private int round;
	private int elapsedRoundTime;
	private int playedUnitID;
	private PlayerInformation activePlayer, passivePlayer;
	
	public BlockingQueue<ServerCommand> inputQueue = new LinkedBlockingQueue<ServerCommand>();	
	
	public Game(Client player1, Client player2) throws InterruptedException
	{
		gameID = 0;
		this.setName("Game " + gameID);
		this.player1 = new PlayerInformation(player1,1);
		this.player2 = new PlayerInformation(player2,2);
		
		player1.newStatus(inputQueue, this.player1.outputQueue, ConnectionStatus.IN_GAME);
		player2.newStatus(inputQueue, this.player2.outputQueue, ConnectionStatus.IN_GAME);
		
		round = 0;
		elapsedRoundTime = 0;
		turn = true;
		playedUnitID = 0;
		
		activePlayer = this.player1;
		passivePlayer = this.player2;
		
		
		Message message1 = new Message();
		message1.addAction(new Action(ActionEnum.GAME_FOUND,player2.getHeroChoice(),player2.getName()));
		message1.addAction(new Action(ActionEnum.SET_PLAYER_ID,this.player1.getPlayerID(),""));
		message1.addAction(new Action(ActionEnum.CARD_DRAW, 1, ""));
		message1.addAction(new Action(ActionEnum.CARD_DRAW, 2, ""));
		message1.addAction(new Action(ActionEnum.CARD_DRAW, 3, ""));
		this.player1.outputQueue.put(message1);
		
		Message message2 = new Message();
		message2.addAction(new Action(ActionEnum.GAME_FOUND,player1.getHeroChoice(),player1.getName()));
		message2.addAction(new Action(ActionEnum.SET_PLAYER_ID,this.player2.getPlayerID(),""));
		message2.addAction(new Action(ActionEnum.CARD_DRAW, 1, ""));
		message2.addAction(new Action(ActionEnum.CARD_DRAW, 2, ""));
		message2.addAction(new Action(ActionEnum.CARD_DRAW, 3, ""));
		message2.addAction(new Action(ActionEnum.CARD_DRAW, 4, ""));
		message2.addAction(new Action(ActionEnum.CARD_DRAW, 0, ""));
		this.player2.outputQueue.put(message2);
		
		System.out.println("Game init");
	}
	
	public void run(){
		System.out.println("Game running");
		while (true){
			Message message;
			PlayerInformation activePlayer;
			PlayerInformation passivePlayer;
			try {
				if(!inputQueue.isEmpty()){
					if(inputQueue.peek().getClientID() == player1.getID()){
						activePlayer = player1;
						passivePlayer = player2;
					}
					else{
						activePlayer = player2;
						passivePlayer = player1;
					}
					message = commandDispatch(inputQueue.take().getCommand(),activePlayer);
					activePlayer.outputQueue.put(message);	//send message to active player either way
					if(!message.isError())
						passivePlayer.outputQueue.put(message); //send message to passive player too 
				}
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removePlayedUnit(int id){
		
	}
	
	public int getNewPlayedUnitID(){
		return ++playedUnitID;
	}
	
	private List<Action> EndTurn(){
		
		List<Action> actions = new LinkedList<Action>();
		
		actions.add(new Action(ActionEnum.TURN_END,activePlayer.getPlayerID(),""));
		//Event notify END_TURN, activePlayer
		
		PlayerInformation temp = activePlayer;
		activePlayer = passivePlayer;
		passivePlayer = temp;
		
		round++;
		
		actions.add(new Action(ActionEnum.TURN_START,activePlayer.getPlayerID(),""));
		//Event notify START_TURN, activePlayer
		
		activePlayer.alterMaxMana(1);
		activePlayer.resetMana();
		
		actions.add(new Action(ActionEnum.CARD_DRAW,5,""));
		//Event notify CARD_DRAW, activePlayer, card
		
		elapsedRoundTime = 0;
		
		return actions;
		
	}
		
	private Message commandDispatch(Command command, PlayerInformation player) throws InterruptedException{
		Message message = new Message(command);
		List<Action> actions = new LinkedList<Action>();
		switch (command.getCommand()){
			case ATTACK: if(player==activePlayer) actions = Attack(command.getParam1(), command.getParam2()); else actions.add(new Action(ActionEnum.ERROR_NOT_YOUR_TURN,0,"")); break;
			case CARD_PLAY: break;
			case CLOSE: break;
			case CONCEDE: break;
			case HERO_POWER: break;
			case QUEUE: break;
			case END_TURN: if(player==activePlayer) actions = EndTurn(); else actions.add(new Action(ActionEnum.ERROR_NOT_YOUR_TURN,0,"")); break;
		default:
			break;
		}

		message.addActions(actions);
		return message;
	}
	
	//private List<Action> Attack(int attackerID, int attackedID) throws InterruptedException{	//IDs instead of pos so we dont need ownership
	private List<Action> Attack(int attackerPos, int attackedPos) throws InterruptedException{	//Attacks can only ever come from the active Player. So we don't need ownership
		List<Action> results = new LinkedList<Action>();
		
		
		
		results.add(new Action(ActionEnum.CREATURE_DAMAGE,0,3));
		
		return results;
	}
}
