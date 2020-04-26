package caterpillow.event.events;

import java.util.ArrayList;

import caterpillow.event.Event;

public class Event3D extends Event{
	private float partialTicks;
	
	public Event3D(float partialTicks) {
		this.partialTicks = partialTicks;
	}
	public float getPartialTicks() {
		return partialTicks;
	}
	@Override
	public void fire(ArrayList listeners) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Class getListenerType() {
		// TODO Auto-generated method stub
		return null;
	}
}
