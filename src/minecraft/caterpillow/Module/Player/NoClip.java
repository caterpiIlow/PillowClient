package caterpillow.Module.Player;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;

public class NoClip extends Module {
	public NoClip() {
		super("NoClip", Keyboard.KEY_U, Category.PLAYER);
	}

	@EventTarget
	public void onUpdate() {
		this.setDisplayName("NoClip §7broken");
		mc.thePlayer.noClip = true;
//		mc.thePlayer.fallDistance = 0;
//		mc.thePlayer.onGround = false;
//
//		mc.thePlayer.capabilities.isFlying = false;
//		mc.thePlayer.motionX = 0;
//		mc.thePlayer.motionY = 0;
//		mc.thePlayer.motionZ = 0;
//
//		float speed = 0.2F;
//		mc.thePlayer.jumpMovementFactor = speed;
//		if (mc.gameSettings.keyBindJump.pressed)
//			mc.thePlayer.motionY += speed;
//		if (mc.gameSettings.keyBindSneak.pressed)
//			mc.thePlayer.motionY -= speed;
	}

	@Override
	public void onDisable() {
		mc.thePlayer.noClip = false;
	}
}
