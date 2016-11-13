package communication.command;

import communication.Command;

/**
 * Created by Lukas on 11.11.2016.
 */
public class CommandEndTurn implements Command {

    final int activePlayerID;

    public CommandEndTurn(int activePlayerID){
        this.activePlayerID = activePlayerID;
    }
}
