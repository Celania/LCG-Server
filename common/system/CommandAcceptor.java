package system;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;






import communication.Action;
import communication.Command;
import communication.Message;
import communication.ServerCommand;
import connection.Client;
import connection.ConnectionStatus;

abstract class CommandAcceptor extends Thread{
	
	//protected List<Client> clients = new LinkedList<Client>();
	
	protected Map<UUID,Client> clients = new HashMap<UUID,Client>();
	
	private BlockingQueue<Client> clientsToAdd = new LinkedBlockingQueue<Client>();
	private BlockingQueue<Client> clientsToRemove = new LinkedBlockingQueue<Client>();
	
	public BlockingQueue<ServerCommand> inputQueue = new LinkedBlockingQueue<ServerCommand>();
	
	// set in the extending classes constructor
	protected ConnectionStatus status;
	protected ConnectionStatus removeStatus;
	
	public CommandAcceptor(){	}
 	
	public void addClient(Client client){
		try {
			clientsToAdd.put(client);
			client.newStatus(inputQueue, null, status);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Message message = new Message();
		List<Action> actions = onAddClient();
		if(!actions.isEmpty()){
		message.addActions(actions);
		client.singleMessage(message);
		}
	}
	
	public void run(){
		while (true){
			while(!clientsToAdd.isEmpty())
				clients.put(clientsToAdd.peek().getID(), clientsToAdd.poll());
			
			while(!clientsToRemove.isEmpty())
				clients.remove(clientsToRemove.poll().getID());
			
			if(!inputQueue.isEmpty())
				try {
					System.out.println(inputQueue.peek().getCommand().getCommand());
					commandDispatch(inputQueue.take());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			
			onRun();
			
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeClient(Client client){
		//client.newStatus(null, null, removeStatus); input/output points to system???
		Message message = new Message();
		List<Action> actions = onRemoveClient(client.getID());
		try {
			clientsToRemove.put(client);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!actions.isEmpty()){
			message.addActions(actions);
			client.singleMessage(message);
		}
	}
	
	// extending class is supposed to overwrite this
	protected void commandDispatch(ServerCommand command){
		
	}
	
	protected List<Action> onAddClient(){
		List<Action> actions = new LinkedList<Action>();
		
		return actions;
	}
	
	protected List<Action> onRemoveClient(UUID clientID){
		List<Action> actions = new LinkedList<Action>();
		
		return actions;
	}
	
	protected void onRun(){
		
	}
}
