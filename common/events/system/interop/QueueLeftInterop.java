package events.system.interop;

import events.interop.base.InteropEvent;
import events.interop.base.InteropEventHandler;

/**
 * Created by Lukas on 12.11.2016.
 */
public class QueueLeftInterop implements InteropEvent{

    @Override
    public void handleInteropEvent(InteropEventHandler eventIOHandler) {
        eventIOHandler.handleInteropEvent(this);
    }
}
