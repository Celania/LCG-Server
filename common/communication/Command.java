package communication;

import java.io.Serializable;

public class Command implements Serializable{

	private static final long serialVersionUID = 6195055236038777963L;
	
	private int command;
	private int param1;
	private int param2;
	private String param3;
	
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	public int getParam1() {
		return param1;
	}
	public void setParam1(int param1) {
		this.param1 = param1;
	}
	public int getParam2() {
		return param2;
	}
	public void setParam2(int param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	
	public void clear(){
		command = -1;
		param1 = 0;
		param2 = 0;
		param3 = null;
	}
}
