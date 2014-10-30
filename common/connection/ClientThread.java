package connection;

import game.Game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.locks.ReentrantLock;

import manager.CommandManager;
import communication.Action;
import communication.Command;
import communication.Message;

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 */
class ClientThread extends Thread {
	
  private final static ReentrantLock lock = new ReentrantLock();

  private String clientName = null;
  private ObjectInputStream is = null;
  private ObjectOutputStream os = null;
  private Socket clientSocket = null;
  private final ClientThread[] threads;
  private int maxClientsCount;
  private ClientThread opponent = null;
  private ConnectionStatus status;
  private int heroChoice;
  private CommandManager commandmanager;
  private Game game;

  public ClientThread(Socket clientSocket, ClientThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
    status = ConnectionStatus.UNINITIALIZED;
    heroChoice = 0;
    commandmanager = new CommandManager();
  }
  
  public void opponentFound(ClientThread opponent, Game game) throws IOException{
	  status = ConnectionStatus.IN_GAME;
	  this.opponent = opponent;
	  this.game = game;
	  Message message = new Message();
	  message.addAction(new Action(3, 2, ""));
	  message.addAction(new Action(0, opponent.heroChoice, opponent.clientName));
	  message.addAction(new Action(21, 12352, ""));	// draw cards
	  message.addAction(new Action(21, 1252, ""));
	  message.addAction(new Action(21, 122, ""));
	  message.addAction(new Action(21, 1152, ""));
	  message.addAction(new Action(21, 1700, ""));
	  message.addAction(new Action(8, 0, "Opponent Turn"));
	  os.writeObject(message);  
  }

  public void run() {
    int maxClientsCount = this.maxClientsCount;
    ClientThread[] threads = this.threads;

    try {
    	
    	/*
         * Create input and output streams for this client.
         */
//        is = new DataInputStream(clientSocket.getInputStream());
//        os = new PrintStream(clientSocket.getOutputStream());
        
        is = new ObjectInputStream(clientSocket.getInputStream());
        os = new ObjectOutputStream(clientSocket.getOutputStream());
        
	    Command command = (Command)is.readObject();
	    clientName = command.getParam3();
	    heroChoice = command.getParam1();
	    
	    Message message = new Message();
	    message.addAction(new Action(1,0,"Waiting for Opponent"));
	    
	    os.writeObject(message);
	    
	    status = ConnectionStatus.WAITING_FOR_OPPONENT;
	    
      
//      search for an opponent who is not in a game yet.
      while (status == ConnectionStatus.WAITING_FOR_OPPONENT){
    	  lock.lock();
    		  if(status == ConnectionStatus.IN_GAME){
    			  lock.unlock();
    			  break;
    		  }
    	  for (int i = 0; i < maxClientsCount; i++){
    			  if(threads[i] != null && threads[i] != this
    				&& threads[i].status == ConnectionStatus.WAITING_FOR_OPPONENT){
    				  opponent = threads[i];
    				  game = new Game();
    				  opponent.opponentFound(this, game);
    				  status = ConnectionStatus.IN_GAME;
    				  os.reset();
    				  message.clear();
    				  message.addAction(new Action(3, 1, ""));	// game_start 
    				  message.addAction(new Action(0, opponent.heroChoice, opponent.clientName));
    				  message.addAction(new Action(21, 1252, ""));	// draw card
    				  message.addAction(new Action(21, 122, ""));
    				  message.addAction(new Action(21, 12152, ""));
    				  message.addAction(new Action(5, 0, ""));	// Start_Turn
    				  message.addAction(new Action(21, 5555, ""));	// draw random card
    				  message.addAction(new Action(14, 1, ""));		// Mana_Count
    				  os.writeObject(message);
    				  break;
    			  }
    	  }
    	  lock.unlock();
      }
      
      
      while (true){
    	  
    	  command = (Command) is.readObject();
    	  
    	  if (command.getCommand() == 1)
    		  break;
    	  
    	  message.clear();
    	  Action action = new Action(1,2,"");
    	  message.addAction(action);
    	  os.reset();
    	  os.writeObject(message);
    	    
      }
	  
      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      synchronized (this) {
        for (int i = 0; i < maxClientsCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }
      /*
       * Close the output stream, close the input stream, close the socket.
       */
      is.close();
      os.close();
      clientSocket.close();
    } catch (IOException e) {
   
    } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
