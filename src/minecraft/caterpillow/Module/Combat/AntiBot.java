package caterpillow.Module.Combat;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;
import net.minecraft.entity.Entity;

public class AntiBot extends Module {
	public AntiBot() {
		super("AntiBot", Keyboard.KEY_M, Category.MISC);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		for (Object entity : mc.theWorld.loadedEntityList)
			if (((Entity) entity).isInvisible() && entity != mc.thePlayer)
				mc.theWorld.removeEntity((Entity) entity);
	}
}