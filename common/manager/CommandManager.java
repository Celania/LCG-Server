package manager;

import game.Game;
import communication.Command;
import communication.Message;

public class CommandManager {
	
	private static class InstanceHolder {
	    private static final CommandManager instance = new CommandManager();
	}

	public static CommandManager getInstance() {
	    return InstanceHolder.instance;
	}
	
	public Message execute(Game game, Command command){
		Message message = new Message();
		
		switch(command.getCommand()){
			case 1:    
		}
		
		
		return message;
	}

}
