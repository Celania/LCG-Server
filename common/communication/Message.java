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
	private List<Action> actions;
	
	public void addAction(Action action){
		actions.add(action);
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
	
}
