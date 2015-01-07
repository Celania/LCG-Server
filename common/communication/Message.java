package communication;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6184628798328502334L;
	private Command requested;
	private List<Action> actions;
	
	public void addAction(Action action){
		actions.add(action);
	}
	
	public void addActions(List<Action> actions){
		this.actions.addAll(actions);
	}
	
	public Iterator<Action> getIter(){
		return actions.iterator();
	}
	
	public void clear(){
		actions.clear();
	}
	
	public Message(){
		actions = new LinkedList<Action>();
	}
	
	public Command getRequested(){
		return requested;
	}
	
	public Message(Command requested){
		actions = new LinkedList<Action>();
		this.requested = requested;
	}
	
	public boolean isError(){
		switch(actions.get(0).getAction()){
		case ERROR_NO_MORE_ATTACKS:
		case ERROR_CANNOT_ATTACK:
		case ERROR_INVALID_TARGET:
		case ERROR_NOT_YOUR_TURN:
		case ERROR_NOT_ENOUGH_MANA:
		case ERROR_ALREADY_USED:
			return true;
		default: return false;
		}
	}
}
