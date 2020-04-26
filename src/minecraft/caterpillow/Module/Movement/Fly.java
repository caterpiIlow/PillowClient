package caterpillow.Module.Movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;
import de.Hero.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Fly extends Module {
	public Fly() {
		super("Fly", Keyboard.KEY_B, Category.MISC);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {

		double speed = Client.instance.settingsManager.getSettingByName("Speed").getValDouble();
		if (Client.instance.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Vanilla")) {

			this.setDisplayName("Fly §7Vanilla");

			mc.thePlayer.capabilities.isFlying = true;
			mc.thePlayer.capabilities.allowFlying = true;

		} else if (Client.instance.settingsManager.getSettingByName("Fly Mode").getValString()
				.equalsIgnoreCase("Motion")) {
			this.setDisplayName("Fly §7Motion");

			if (mc.thePlayer.movementInput.jump) {
				mc.thePlayer.motionY = speed * 0.6D;

			} else if (mc.thePlayer.movementInput.sneak) {
				mc.thePlayer.motionY = -speed * 0.6D;

			} else {
				mc.thePlayer.motionY = 0.0D;
			}

			double forward = mc.thePlayer.movementInput.moveForward;
			double strafe = mc.thePlayer.movementInput.moveStrafe;
			float yaw = mc.thePlayer.rotationYaw;
			if (forward == 0.0D && strafe == 0.0D) {
				mc.thePlayer.motionX = 0;
				mc.thePlayer.motionZ = 0;
			} else {
				if (forward != 0.0D) {
					if (strafe > 0.0D) {
						yaw += ((forward > 0.0D) ? -45 : 45);
					} else if (strafe < 0.0D) {
						yaw += ((forward > 0.0D) ? 45 : -45);
					}
					strafe = 0.0D;
					if (forward > 0.0D) {
						forward = 1.0D;
					} else if (forward < 0.0D) {
						forward = -1.0D;
					}
				}
				mc.thePlayer.motionX = (forward * speed * Math.cos(Math.toRadians((yaw + 90.0F)))
						+ strafe * speed * Math.sin(Math.toRadians((yaw + 90.0F))));
				mc.thePlayer.motionZ = (forward * speed * Math.sin(Math.toRadians((yaw + 90.0F)))
						- strafe * speed * Math.cos(Math.toRadians((yaw + 90.0F))));

			}

		} else if (Client.instance.settingsManager.getSettingByName("Fly Mode").getValString()
				.equalsIgnoreCase("Hypixel")) {
			this.setDisplayName("Fly §7Hypixel");
			double y;
			double y1;
			mc.thePlayer.motionY = 0;
			if (mc.thePlayer.ticksExisted % 3 == 0) {
				y = mc.thePlayer.posY - 1.0E-10D;
				mc.thePlayer.sendQueue.addToSendQueue(
						new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true));

			}

			y1 = mc.thePlayer.posY + 1.0E-10D;
			mc.thePlayer.setPosition(mc.thePlayer.posX, y1, mc.thePlayer.posZ);

		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
		if (Client.instance.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Vanilla")) {

			mc.thePlayer.capabilities.isFlying = false;
			mc.thePlayer.capabilities.allowFlying = false;
		} else if (Client.instance.settingsManager.getSettingByName("Fly Mode").getValString()
				.equalsIgnoreCase("Motion")) {
			mc.thePlayer.motionX = 0;
			mc.thePlayer.motionY = 0;
			mc.thePlayer.motionZ = 0;
		}
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("Motion");
		options.add("Hypixel");
		Client.instance.settingsManager.rSetting(new Setting("Fly Mode", this, "Vanilla", options));
		Client.instance.settingsManager.rSetting(new Setting("Speed", this, 1, 1, 10, true, 0.1));

	}
}
