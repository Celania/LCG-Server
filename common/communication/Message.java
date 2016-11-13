package communication;

import events.interop.base.InteropEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Message implements Serializable {

    private static final long serialVersionUID = -6184628798328502334L;

    private Command requested;
    private List<InteropEvent> events = new ArrayList<>();

    public Message(){
    }

    public Message(List<InteropEvent> events){
        this.events = events;
    }

    public void addInteropEvent(InteropEvent event){
        this.events.add(event);
    }

    public void addInteropEvent(List<InteropEvent> events){
        this.events.addAll(events);
    }

    public Iterator<InteropEvent> getEvents(){
        return events.iterator();
    }



//    private Command           requested;
//    private List<Action>      actions;
//
//    public void addAction(Action action) {
//        actions.add(action);
//    }
//
//    public void addActions(List<Action> actions) {
//        this.actions.addAll(actions);
//    }
//
//    public Iterator<Action> getIter() {
//        return actions.iterator();
//    }
//
//    public void clear() {
//        actions.clear();
//    }
//
//    public Message() {
//        actions = new LinkedList<Action>();
//    }
//
//    public Command getRequested() {
//        return requested;
//    }
//
//    public Message(Command requested) {
//        actions = new LinkedList<Action>();
//        this.requested = requested;
//    }
//
//    public Message anonymize(int playerID) {
//        Message message = new Message(this.requested);
//        for (Action action : this.actions)
//            switch (action.getAction()) {
//                case CARD_DRAW:
//                    if (action.getField() != playerID)
//                        message.addAction(new Action(ActionEnum.CARD_DRAW, action.getField(), -1));
//                    else
//                        message.addAction(action);
//                    break;
//                default:
//                    message.addAction(action);
//                    break;
//            }
//        return message;
//    }
//
//    public boolean isError() {
//        switch (actions.get(0).getAction()) {
//            case ERROR_NO_MORE_ATTACKS:
//            case ERROR_CANNOT_ATTACK:
//            case ERROR_INVALID_TARGET:
//            case ERROR_NOT_YOUR_TURN:
//            case ERROR_NOT_ENOUGH_MANA:
//            case ERROR_ALREADY_USED:
//                return true;
//            default:
//                return false;
//        }
//    }
}
