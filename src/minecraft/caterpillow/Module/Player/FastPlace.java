package caterpillow.Module.Player;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;

public class FastPlace extends Module {
	public FastPlace() {
		super("FastPlace", Keyboard.KEY_U, Category.PLAYER);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.rightClickDelayTimer = Math.min(mc.rightClickDelayTimer, 1);
	}
}
