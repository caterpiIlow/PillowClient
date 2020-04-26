package caterpillow.Module.Movement;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;

public class Sprint extends Module {
	public Sprint() {
		super("KeepSprint", Keyboard.KEY_M, Category.MISC);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0)
			mc.thePlayer.setSprinting(true);
	}

	@Override
	public void onDisable() {
		super.onDisable();

		mc.thePlayer.setSprinting(false);
	}

}
