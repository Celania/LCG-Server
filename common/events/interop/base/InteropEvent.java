package events.interop.base;

import java.io.Serializable;

/**
 * Created by Lukas on 20.06.2016.
 */
public interface InteropEvent extends Serializable{

    static final long serialVersionUID = 8367706544071022993L;

    public void handleInteropEvent(InteropEventHandler eventIOHandler);

}
