package caterpillow.event.events;

import java.util.ArrayList;

import caterpillow.event.Event;

public class Event2D extends Event {
	private float width, height;

	public Event2D(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
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
