package system;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;






import communication.Action;
import communication.ActionEnum;
import communication.Command;
import communication.Message;
import communication.ServerCommand;
import connection.Client;
import connection.ConnectionStatus;


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
		
		switch(command.getCommand()){
		
		case QUEUE:
			onQueue(command.getParam1(), serverCommand.getClientID());
			break;
		case CLOSE:
			break;
		case ABORT:
			break;
		default:
			break;
		}
		
	}

	public void onRun(){
		
	}
	
	public List<Action> onAddClient(){
		List<Action> actions = new LinkedList<Action>();
		
		//message.addAction(new Action(ActionEnum.WAITING_FOR_OPPONENT,0, ""));
		
		return actions;
	}
	
	public List<Action> onRemoveClient(){
		List<Action> actions = new LinkedList<Action>();
		
		return actions;
	}
	
	private void onQueue(int heroChoice, UUID clientID){
		Client client = clients.get(clientID);
		client.unregisterQueue();
		clients.remove(clientID);
		client.setHeroChoice(heroChoice);
		MatchMaking.getInstance().addClient(client);
	}
}
