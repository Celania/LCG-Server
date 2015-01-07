package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

import communication.Command;
import communication.Message;
import communication.ServerCommand;

public class Client {
	
	private Socket clientSocket;
	private ClientThreadInput clientIn;
	private ClientThreadOutput clientOut;
	
	private int heroChoice;
	private String name;
	private UUID ID;
	
	
	private ConnectionStatus status;
		
	public Client(Socket clientSocket){
		try{
		this.clientSocket = clientSocket;	//need to close the socket if client disconnects
		ID = UUID.randomUUID();
		clientIn = new ClientThreadInput(ID,new ObjectInputStream(clientSocket.getInputStream()));
		clientOut = new ClientThreadOutput(new ObjectOutputStream(clientSocket.getOutputStream()));
		clientIn.start();
		clientOut.start();
		} catch (IOException e) {
			   
		}
	}
	
	public synchronized void singleMessage(Message message){
		try {
			clientOut.singleMessage(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void unregisterQueue(){
		clientIn.unregisterQueue();
	}
	
	public synchronized void newStatus(BlockingQueue<ServerCommand> input,
			          BlockingQueue<Message> output,
					  ConnectionStatus status) throws InterruptedException
	{	
		if (this.status != status){
			this.status = status;
			clientIn.registerQueue(input);
			clientOut.registerQueue(output);	
		}
				
	}


	public UUID getID(){
		return ID;
	}

	public String getName() {
		return name;
	}
	
	public int getHeroChoice(){
		return heroChoice;
	}
	
	public void setHeroChoice(int heroChoice){
		this.heroChoice = heroChoice;
	}
}
