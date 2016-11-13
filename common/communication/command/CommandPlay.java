package communication.command;

import communication.Command;

/**
 * Created by Lukas on 04.11.2016.
 */
public class CommandPlay implements Command{

    public final int handPosition;
    public final int boardPosition;
    public final int boardPlayerID;

    public CommandPlay(int handPosition, int boardPosition, int boardPlayerID){
        this.boardPosition =boardPosition;
        this.handPosition = handPosition;
        this.boardPlayerID =boardPlayerID;
    }
}
