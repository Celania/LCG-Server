package system;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import communication.command.CommandAbortQueue;
import events.SystemEvent;
import events.interop.base.InteropEvent;
import events.system.QueueLeft;
import events.system.WaitingForOpponent;
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
		
		if (command instanceof CommandAbortQueue) {
            onAbort(clientID);
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
	
	public List<SystemEvent> onAddClient(){
		List<SystemEvent> events = new LinkedList<>();
		
		events.add(new WaitingForOpponent());
		System.out.println("Client queued");
		
		return events;
	}
	
	public List<SystemEvent> onRemoveClient(UUID clientID){
		List<SystemEvent> events = new LinkedList<>();
		
		System.out.println("Client with ID:"+ clientID +" dropped out of Queue");				
		events.add(new QueueLeft());
		
		return events;
	}
	
	private List<InteropEvent> onAbort(UUID clientID){
		List<InteropEvent> actions = new LinkedList<>();
		
		Client client = clients.get(clientID);
		removeClient(client);
		StdHandler.getInstance().addClient(client);

		return actions;
	}
}
