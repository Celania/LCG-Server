package events.system;

import communication.ActionEnum;
import events.SystemEvent;
import events.interop.base.InteropEvent;
import events.system.interop.WaitingForOpponentInterop;

/**
 * Created by Lukas on 11.11.2016.
 */
public class WaitingForOpponent implements SystemEvent{

    @Override
    public ActionEnum getEventID() {
        return ActionEnum.WAITING_FOR_OPPONENT;
    }

    @Override
    public InteropEvent makeIOEvent() {
        return new WaitingForOpponentInterop();
    }
}
