package manager;

import java.util.*;

import communication.ActionEnum;
import effects.base.PermEffect;

import effects.base.TempEffect;
import events.Event;
import events.ExternalEvent;
import events.InternalEvent;
import game.EventResult;
import game.PlayerInformation;
import units.PlayedUnit;

public class EffectManager {

    public EffectManager() {
        /*
         * for (ActionEnum trigger : ActionEnum.values()){ permEffects.get(trigger) = new LinkedList<PermEffect>();
         * permEffects.get(trigger) = new LinkedList<PermEffect>(); }
         */
    }

    public void init(EventResult eventResult, PlayerInformation player1, PlayerInformation player2) {
        this.eventResult = eventResult;

        this.player1 = player1;

        this.player2 = player2;
    }

    private PlayerInformation                 player1;
    private PlayerInformation                 player2;

    private Map<ActionEnum, List<PermEffect>> permEffects      = new HashMap<>();
    private Map<ActionEnum, List<TempEffect>> tempEffects      = new HashMap<>();

    private Queue<Event> eventQueue = new LinkedList<>();
    private EventResult eventResult = null;

    public void addPermEffect(PermEffect effect, PlayedUnit source) {

        permEffects.get(effect.getTriggerAction()).add(new PermEffect(effect, source));
    }

    public void addPermEffect(PermEffect effect, PlayerInformation owner){
        permEffects.get(effect.getTriggerAction()).add(new PermEffect(effect, owner));
    }

    public void addTempEffect(TempEffect effect, PlayedUnit source) {
        tempEffects.get(effect.getTriggerAction()).add(new TempEffect(effect, source));
    }

    public void addPermEffects(Iterator<PermEffect> it, PlayedUnit source) {
        while (it.hasNext())
            addPermEffect(it.next(), source);
    }

    public void addTempEffects(Iterator<TempEffect> it, PlayedUnit source) {
        while (it.hasNext())
            addTempEffect(it.next(), source);
    }

    public void deleteEffects(PlayedUnit playedUnit) {

        for (Map.Entry<ActionEnum, List<PermEffect>> effects : permEffects.entrySet()) {
            for (PermEffect effect : effects.getValue()) {
                // we remove every effect thats been attributed to the playedUnit if it is not flagged as death-persistent
                if (effect.getSource().isPresent() && effect.getSource().get() == playedUnit
                        && !(effect.expiresNever())){
                    effect.removeSource();
                    effects.getValue().remove(effect);
                }
            }
        }
    }

    public void fireEvent(Event event){

        if (!eventQueue.isEmpty()){
            eventQueue.add(event);
            return;
        }
        else
            eventQueue.add(event);

        List<ExternalEvent> result = new ArrayList<>();


        // TODO proper order instead of perm first, temp second
        while (!eventQueue.isEmpty()){
            Event activeEvent = eventQueue.poll();
            if (activeEvent instanceof ExternalEvent)
                result.add((ExternalEvent)activeEvent);

            List<PermEffect> pEffects = permEffects.get(activeEvent.getEventID());
            if (pEffects != null) {
                for (PermEffect effect : pEffects)
                    if (effect.doesTrigger(activeEvent)) {
                        effect.trigger(this);
                    }
            }

            List<TempEffect> tEffects = tempEffects.get(activeEvent.getEventID());
            if (tEffects != null) {
                for (TempEffect effect : tEffects)
                    if (effect.doesTrigger(activeEvent)) {
                        effect.trigger(this);
                    }
            }

            // break on maximum stack depth
            if (result.size() > 1024)
                break;
        }

        eventResult.addEvents(result);
    }

    public void fireEvents(List<Event> events){
        for (Event e: events){
            fireEvent(e);
        }
    }

    public PlayerInformation getEnemy(PlayerInformation player){
        if (player == player1)
            return player2;
        else
            return player1;
    }
}
