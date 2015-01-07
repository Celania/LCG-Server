package communication;

import java.util.UUID;

public class ServerCommand {
	
	private Command command;
	private UUID clientID;
	
	public ServerCommand(Command command, UUID clientID){
		this.command = command;
		this.clientID = clientID;
	}

	public Command getCommand(){
		return command;
	}
	
	public UUID getClientID(){
		return clientID;
	}
}
