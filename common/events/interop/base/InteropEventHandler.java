package events.interop.base;

import events.Error;
import events.game.interop.CardDrawn.CardDrawnInterop;
import events.game.interop.CardDrawn.CardDrawnInteropAnon;
import events.game.interop.CreatureDamageTaken.CreatureDamageTakenInterop;
import events.game.interop.CreatureStatusGainedInterop.CreatureStatusGainedInterop;
import events.game.interop.TurnEnded.TurnEndedInterop;
import events.game.interop.TurnStarted.TurnStartedInterop;
import events.system.interop.QueueLeftInterop;
import events.system.interop.WaitingForOpponentInterop;

/**
 * Created by Lukas on 20.06.2016.
 */
public interface InteropEventHandler {

    void handleInteropEvent(CreatureDamageTakenInterop creatureDamageTaken);
    void handleInteropEvent(CardDrawnInterop cardDrawn);
    void handleInteropEvent(CardDrawnInteropAnon cardDrawnAnon);
    void handleInteropEvent(CreatureStatusGainedInterop creatureStatusGainedInterop);
    void handleInteropEvent(Error.ErrorInterop errorInterop);
    void handleInteropEvent(TurnEndedInterop turnEnded);
    void handleInteropEvent(TurnStartedInterop turnStarted);


    void handleInteropEvent(QueueLeftInterop queueLeftInterop);
    void handleInteropEvent(WaitingForOpponentInterop waitingForOpponentInterop);
}
