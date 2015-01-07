package manager;

import java.util.UUID;

import game.Game;
import communication.Command;
import communication.Message;

public class CommandManager {
		
	public static Message execute(Game game, Command command, UUID clientID){
		Message message = new Message();
		
		switch(command.getCommand()){
		
			//case CARD_PLAY:	message = game.playCard(command.getParam1(), command.getParam2());
		}
		
		
		return message;
	}

}
