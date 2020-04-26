package caterpillow.Module.Player;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventPreMotionUpdate;

public class InvMove extends Module {
	public InvMove() {
		super("InvMove", Keyboard.KEY_NONE, Category.PLAYER);
	}

	@EventTarget
	public void onPre(EventPreMotionUpdate event) {
		if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat)) {
			this.mc.gameSettings.keyBindForward.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode());
			this.mc.gameSettings.keyBindBack.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode());
			this.mc.gameSettings.keyBindLeft.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode());
			this.mc.gameSettings.keyBindRight.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode());
			this.mc.gameSettings.keyBindJump.pressed = Keyboard
					.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
		}
	}
}
