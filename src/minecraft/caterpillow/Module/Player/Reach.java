package caterpillow.Module.Player;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;

public class Reach extends Module {
	public Reach() {
		super("Reach", Keyboard.KEY_I, Category.PLAYER);
	}

	double reach = 0;

	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.reach = 10;
	}

}
