package caterpillow.event;

import java.util.ArrayList;

import caterpillow.event.Listeners.RenderListener;

public class RenderEvent extends Event<RenderListener> {
	public static final RenderEvent INSTANCE = new RenderEvent();

	@Override
	public void fire(ArrayList<RenderListener> listeners) {
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).onRender();
	}

	@Override
	public Class<RenderListener> getListenerType() {
		return RenderListener.class;
	}
}