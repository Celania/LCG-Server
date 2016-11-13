package events.system;

import communication.ActionEnum;
import events.SystemEvent;
import events.interop.base.InteropEvent;

/**
 * Created by Lukas on 12.11.2016.
 */
public class QueueLeft implements SystemEvent {
    @Override
    public ActionEnum getEventID() {
        return ActionEnum.QUEUE_LEFT;
    }

    @Override
    public InteropEvent makeIOEvent() {
        return null;
    }
}
