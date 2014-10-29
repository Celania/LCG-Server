package connection;

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

  public ClientThread(Socket clientSocket, ClientThread[] threads) {
    this.clientSocket = clientSocket;
    this.threads = threads;
    maxClientsCount = threads.length;
    status = ConnectionStatus.UNINITIALIZED;
    heroChoice = 0;
  }
  
  public void opponentFound(ClientThread opponent) throws IOException{
	  status = ConnectionStatus.IN_GAME;
	  this.opponent = opponent;
	  Message message = new Message();
	  message.addAction(new Action(0, opponent.heroChoice, opponent.clientName));
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
	    
	    status = ConnectionStatus.WAITING_FOR_OPPONENT;
	    
	    Message message = new Message();
	    message.addAction(new Action(1,0,"Waiting for Opponent"));
	    
	    os.writeObject(message);
      
//      search for an opponent who is not in a game yet.
      while (status == ConnectionStatus.WAITING_FOR_OPPONENT){
    	  lock.lock();
    		  if(status == ConnectionStatus.IN_GAME){
    			  break;
    		  }
    	  for (int i = 0; i < maxClientsCount; i++){
    			  if(threads[i] != null && threads[i] != this
    				&& threads[i].status == ConnectionStatus.WAITING_FOR_OPPONENT){
    				  opponent = threads[i];
    				  opponent.opponentFound(this);
    				  status = ConnectionStatus.IN_GAME;
    				  message = new Message();
    				  message.addAction(new Action(0, opponent.heroChoice, opponent.clientName));
    				  os.writeObject(message);
//    				  os.println("Game started with "+ opponent.clientName +" playing "
//    						  + this.heroChoice +" against "+ opponent.heroChoice);
    				  break;
    			  }
    	  }
    	  if(status == ConnectionStatus.WAITING_FOR_OPPONENT)
    		  lock.unlock();
      }
      lock.unlock();
      
      while (true){
    	  command = (Command) is.readObject();
    	  
    	  if (command.getCommand() == 1)
    		  break;
    	  
    	  
    	  message = new Message();
    	  Action action = new Action(1,2,"");
    	  message.addAction(action);
    	  os.writeObject(message);
    	    
      }
	  
      /* Welcome the new the client. */
//      os.println("Welcome " + name
//          + " to our chat room.\nTo leave enter /quit in a new line.");
//      synchronized (this) {
//        for (int i = 0; i < maxClientsCount; i++) {
//          if (threads[i] != null && threads[i] == this) {
//            clientName = "@" + name;
//            break;
//          }
//        }
//        for (int i = 0; i < maxClientsCount; i++) {
//          if (threads[i] != null && threads[i] != this) {
//            threads[i].os.println("*** A new user " + name
//                + " entered the chat room !!! ***");
//          }
//        }
//      }
//      /* Start the conversation. */
//      while (true) {
//        String line = is.readLine();
//        if (line.startsWith("/quit")) {
//          break;
//        }
//        /* If the message is private sent it to the given client. */
//        if (line.startsWith("@")) {
//          String[] words = line.split("\\s", 2);
//          if (words.length > 1 && words[1] != null) {
//            words[1] = words[1].trim();
//            if (!words[1].isEmpty()) {
//              synchronized (this) {
//                for (int i = 0; i < maxClientsCount; i++) {
//                  if (threads[i] != null && threads[i] != this
//                      && threads[i].clientName != null
//                      && threads[i].clientName.equals(words[0])) {
//                    threads[i].os.println("<" + name + "> " + words[1]);
//                    /*
//                     * Echo this message to let the client know the private
//                     * message was sent.
//                     */
//                    this.os.println(">" + name + "> " + words[1]);
//                    break;
//                  }
//                }
//              }
//            }
//          }
//        } else {
//          /* The message is public, broadcast it to all other clients. */
//          synchronized (this) {
//            for (int i = 0; i < maxClientsCount; i++) {
//              if (threads[i] != null && threads[i].clientName != null) {
//                threads[i].os.println("<" + name + "> " + line);
//              }
//            }
//          }
//        }
//      }
//      synchronized (this) {
//        for (int i = 0; i < maxClientsCount; i++) {
//          if (threads[i] != null && threads[i] != this
//              && threads[i].clientName != null) {
//            threads[i].os.println("*** The user " + name
//                + " is leaving the chat room !!! ***");
//          }
//        }
//      }
//      os.println("*** Bye " + name + " ***");

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
