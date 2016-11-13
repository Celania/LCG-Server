package system;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;






import communication.Action;
import communication.ActionEnum;
import communication.Command;
import communication.Message;
import communication.ServerCommand;
import communication.command.CommandQueue;
import connection.Client;
import connection.ConnectionStatus;
import events.SystemEvent;


public class StdHandler extends CommandAcceptor{
	
	private static StdHandler instance;
	
	private StdHandler(){
		super();
		status = ConnectionStatus.IDLE;
		this.setName("StdHandler");
	}
	
	public static synchronized StdHandler getInstance() {
	    if(instance == null) {
	        instance = new StdHandler();
	    }
	    return instance;
	}
	
	protected void commandDispatch(ServerCommand serverCommand){
		Command command = serverCommand.getCommand();

		if (command instanceof CommandQueue){
            CommandQueue commandQueue =(CommandQueue) command;
            onQueue(commandQueue.heroChoice, serverCommand.getClientID());
		}

	}

	public void onRun(){
		
	}
	
	public List<SystemEvent> onAddClient(){
		List<SystemEvent> events = new LinkedList<>();
		
		//message.addAction(new Action(ActionEnum.WAITING_FOR_OPPONENT,0, ""));
		
		return events;
	}
	
	public List<SystemEvent> onRemoveClient(){
		List<SystemEvent> events = new LinkedList<>();
		
		return events;
	}
	
	private void onQueue(int heroChoice, UUID clientID){
		Client client = clients.get(clientID);
		client.unregisterQueue();
		clients.remove(clientID);
		client.setHeroChoice(heroChoice);
		MatchMaking.getInstance().addClient(client);
	}
}
