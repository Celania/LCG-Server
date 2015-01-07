package system;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import game.Game;
import communication.Action;
import communication.ActionEnum;
import communication.Command;
import communication.Message;
import communication.ServerCommand;
import connection.Client;
import connection.ConnectionStatus;
import connection.Server;


public class MatchMaking extends CommandAcceptor{
	
	private static MatchMaking instance;
	
	private MatchMaking(){
		super();
		status = ConnectionStatus.WAITING_FOR_OPPONENT;
		removeStatus = ConnectionStatus.IDLE;
		this.setName("MatchMaking");
	}
	
	public static synchronized MatchMaking getInstance() {
	    if(instance == null) {
	        instance = new MatchMaking();
	    }
	    return instance;
	}
	
	protected void commandDispatch(ServerCommand serverCommand){
		
		UUID clientID = serverCommand.getClientID();
		Command command = serverCommand.getCommand();
		
		switch(command.getCommand()){
		case ABORT:
			onAbort(clientID);
			break;
		default:
			break;
		}		
	}
	
	// need to check whether client is on the remove list
	public void onRun(){
		
		Iterator<Client> it = clients.values().iterator();
	    while (it.hasNext()) {
	        Client client = it.next();
	        if (it.hasNext()){
	        	System.out.println(client.getID());
	        	it.remove();
	        	Client client2 = it.next();
	        	System.out.println(client2.getID());
	        	it.remove();
	        	try {
					Server.addGame(new Game(client, client2));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	public List<Action> onAddClient(){
		List<Action> actions = new LinkedList<Action>();
		
		actions.add(new Action(ActionEnum.WAITING_FOR_OPPONENT,0, ""));
		System.out.println("Client queued");
		
		return actions;
	}
	
	public List<Action> onRemoveClient(UUID clientID){
		List<Action> actions = new LinkedList<Action>();
		
		System.out.println("Client with ID:"+ clientID +" dropped out of Queue");				
		actions.add(new Action(ActionEnum.QUEUE_LEFT,0,""));
		
		return actions;
	}
	
	private List<Action> onAbort(UUID clientID){
		List<Action> actions = new LinkedList<Action>();
		
		Client client = clients.get(clientID);
		removeClient(client);
		StdHandler.getInstance().addClient(client);

		return actions;
	}
}
