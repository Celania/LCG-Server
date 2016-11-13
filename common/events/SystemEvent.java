package events;

import events.interop.base.InteropEvent;

/**
 * Created by Lukas on 12.11.2016.
 */
public interface SystemEvent extends Event{

    InteropEvent makeIOEvent();

}
