package caterpillow.Module.Movement;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;
import caterpillow.utils.MoveUtils;
import net.minecraft.client.Minecraft;

public class Strafe extends Module {

	public Strafe() {
		super("Strafe", Keyboard.KEY_NONE, Category.MISC);
	}

	public Minecraft mc = Minecraft.getMinecraft();

	@EventTarget
	public void onUpdate(EventUpdate event) {
		MoveUtils.strafe();
	}
}
