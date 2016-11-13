package events;

import communication.ActionEnum;
import events.interop.base.InteropEvent;
import events.interop.base.InteropEventHandler;


/**
 * Created by Lukas on 12.02.2016.
 */
public class Error extends ExternalEvent{

    public class ErrorInterop implements InteropEvent {
        private ErrorType errorType;

        public ErrorInterop(ErrorType errorType){
            this.errorType = errorType;
        }

        @Override
        public void handleInteropEvent(InteropEventHandler eventIOHandler) {
            eventIOHandler.handleInteropEvent(this);
        }
    }



    private ErrorType errorType;

    public Error(ErrorType errorType){
        super(ActionEnum.ERROR);
        this.errorType =errorType;
    }

    @Override
    public InteropEvent makeIOEvent(int activePlayer, int playerPerspective) {
        return new ErrorInterop(errorType);
    }
}
