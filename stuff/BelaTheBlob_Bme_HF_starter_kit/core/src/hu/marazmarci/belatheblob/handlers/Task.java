package hu.marazmarci.belatheblob.handlers;

import static hu.marazmarci.belatheblob.states.Level1.frame;

public abstract class Task<T extends Object> implements Runnable {
	
	protected T data;
	public long when;
	
	protected long start;
	public boolean active = true;
	
	public Task(T data, long when) {
		this(when);
		this.data = data;
	}
	
	public Task(T data) {
		this.data = data;
	}
	
	public Task(long when) {
		this.when = when;
		this.start = frame;
	}
	
	public void action() {active = false; run();}
	
	public abstract void run();
}