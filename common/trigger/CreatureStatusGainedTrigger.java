package trigger;

import communication.ActionEnum;
import events.game.CreatureStatusGained;
import events.Event;
import game.PlayerInformation;

/**
 * Created by Lukas on 23.02.2016.
 */
public class CreatureStatusGainedTrigger extends Trigger {


    int status;

    public CreatureStatusGainedTrigger(int status){
        super(ActionEnum.CREATURE_STATUS_ADD);
        this.status = status;
    }

    @Override
    public boolean doesTrigger(Event event, PlayerInformation effectSourceOwner) {
        if (event instanceof CreatureStatusGained){
            if (((CreatureStatusGained) event).status == status)
                return true;
        }
        return false;
    }
}
