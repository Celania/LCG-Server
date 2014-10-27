package game;

import communication.Command;
import communication.Message;

public class Game {

	private PlayerInformation player1;
	private PlayerInformation player2;
	private boolean turn;
	private int round;
	
	public void notify(Message message) {
		
	}
	
	public Command receive() {
		return null;
	}
}
