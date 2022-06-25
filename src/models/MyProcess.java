package models;

public class MyProcess {

	private String name;
	private double time;
	private boolean locked, isSuspended_Locked, isSuspended_Ready;

	public MyProcess(String name, double time, boolean... states) {
		super();
		this.name = name;
		this.time = time;
		this.locked = states[0];
		this.isSuspended_Locked = states[1];
		this.isSuspended_Ready = states[2];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = (this.time - time);
	}

	public void updateTime(int time) {
		this.time = time;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public boolean isSuspended_Locked() {
		return isSuspended_Locked;
	}
	
	public boolean isSuspended_Ready() {
		return isSuspended_Ready;
	}
	
	public void setSuspended_Locked(boolean isSuspended_Locked) {
		this.isSuspended_Locked = isSuspended_Locked;
	}
	
	public void setSuspended_Ready(boolean isSuspended_Ready) {
		this.isSuspended_Ready = isSuspended_Ready;
	}

	@Override
	public String toString() {
		return name+" "+time+ " "+ locked +" " + isSuspended_Locked + " " + isSuspended_Ready;
	}
}
