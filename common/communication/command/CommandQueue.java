package communication.command;

import communication.Command;
import events.SystemEvent;

/**
 * Created by Lukas on 12.11.2016.
 */
public class CommandQueue implements Command {

    public final int heroChoice;

    public CommandQueue(int heroChoice){
        this.heroChoice =heroChoice;
    }

}
