package hu.marazmarci.belatheblob.handlers;

import hu.marazmarci.belatheblob.states.Level1;

public abstract class FadeTask<T extends Object> extends Task<T> {

	public FadeTask(T data, long when) {
		super(data, when);
	}
	
	public abstract void update();
	
	protected float getVal() { return (float)(Level1.frame-start)/(float)(when-start); }
	
}
