package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;




import communication.Command;
import communication.ServerCommand;

public class ClientThreadInput extends Thread {
	
	private UUID clientID;
	private ObjectInputStream input;
	private BlockingQueue<ServerCommand> inputQueue;
	
	public ClientThreadInput(UUID clientID, ObjectInputStream input){
		this.clientID = clientID;
		this.input = input;
		this.setName("ClientInput" + clientID);
	}
	
	public void registerQueue(BlockingQueue<ServerCommand> inputQueue){
		this.inputQueue = inputQueue;
	}
	
	public void unregisterQueue(){
		this.inputQueue = null;
	}	
	
	public void run(){
		try{			
			while (true){
		    	if(inputQueue != null){
		    		ServerCommand command = new ServerCommand((Command) input.readObject(),clientID);
		    		inputQueue.put(command);
		    	}
		    		//inputQueue.put(new ServerCommand((Command) input.readObject(),clientID)); //blocks within the put so it uses the wrong queue
		}
		} catch (IOException e) {
		   
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
