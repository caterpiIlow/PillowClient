package caterpillow.event.events;

import java.util.ArrayList;

import caterpillow.event.Event;

public class EventKey extends Event {
	private int key;

	public EventKey(int key) {
		this.key = key;
	}

	public int getKey() {
		return key;
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
