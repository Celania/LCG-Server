package communication;

import java.io.Serializable;

public class Action implements Serializable{


	private static final long serialVersionUID = 7367706544071022993L;
	private ActionEnum action;
	private int field;
	private int value;
	private String value2;
	// private ActionEnum action
	
	public int getField() {
		return field;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getValue2(){
		return value2;
	}
	
	public ActionEnum getAction(){
		return action;
	}
	
	public Action(int field, int value, String value2){
		this.field = field;
		this.value = value;
		this.value2 = value2;
	}
	
	public Action(ActionEnum action, int value, String value2){
		this.action = action;
		this.value = value;
		this.value2 = value2;
	}
	
	public Action(ActionEnum action, int value, int value2){
		this.action = action;
		this.value = value2;
		this.field = value;
	}
	

}
