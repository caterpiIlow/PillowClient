package caterpillow.event;

import java.util.ArrayList;

import caterpillow.event.Listeners.GUIRenderListener;

public class GUIRenderEvent extends Event<GUIRenderListener> {
	public static final GUIRenderEvent INSTANCE = new GUIRenderEvent();

	@Override
	public void fire(ArrayList<GUIRenderListener> listeners) {
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).onRenderGUI();
	}

	@Override
	public Class<GUIRenderListener> getListenerType() {
		return GUIRenderListener.class;
	}
}