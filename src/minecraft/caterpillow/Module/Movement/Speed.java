package caterpillow.Module.Movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventPreMotionUpdate;
import caterpillow.utils.MoveUtils;
//import caterpillow.utils.SpeedUtils;
import caterpillow.utils.Timer;
import de.Hero.settings.Setting;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Speed extends Module {

	public Timer timer = new Timer();

	public Speed() {
		super("Speed", Keyboard.KEY_Z, Category.MISC);
	}

	public Minecraft mc = Minecraft.getMinecraft();

	public void onEnable() {
		super.onEnable();
//		if (Client.instance.settingsManager.getSettingByID("speedSpeed").getValString().equalsIgnoreCase("Normal")) {
//			mc.thePlayer.ticksExisted = 0;
//			if (mc.thePlayer.onGround == true && mc.thePlayer.fallDistance == 0F) {
//				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
//						mc.thePlayer.posY - 4D, mc.thePlayer.posZ, false));
//				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
//						mc.thePlayer.posY, mc.thePlayer.posZ, true));
//			}
//		}
	}

	public void onDisable() {
		super.onDisable();
	}

	@EventTarget
	public void onPre(EventPreMotionUpdate event) {

		if (Client.instance.settingsManager.getSettingByID("speedSpeed").getValString().equalsIgnoreCase("Y-Port")) {
			this.setDisplayName("Speed §7" + "Y-Port");

			if (isOnLiquid())
				return;
			if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
				if (mc.thePlayer.ticksExisted % 2 != 0)
					event.y += .4;
				mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? .45F : .2F);
				mc.timer.timerSpeed = 1.095F;
			}
		} else if (Client.instance.settingsManager.getSettingByID("speedSpeed").getValString()
				.equalsIgnoreCase("Slow")) {
			this.setDisplayName("Speed §7" + "Slow");

			MoveUtils.strafe();
			if (mc.thePlayer.onGround && MoveUtils.isMoving()) {
				mc.thePlayer.motionX *= 1F;
				mc.thePlayer.motionZ *= 1F;
				mc.thePlayer.jump();
			}
			if (!(mc.thePlayer.moveStrafing == 0) && mc.thePlayer.moveForward == 0) {
				MoveUtils.strafe(0.23F);
			} else if (!(mc.thePlayer.moveStrafing == 0) && !(mc.thePlayer.moveForward == 0)) {
				MoveUtils.strafe(0.225F);
			}

		} else if (Client.instance.settingsManager.getSettingByID("speedSpeed").getValString()
				.equalsIgnoreCase("Test")) {
			this.setDisplayName("Speed §7" + "Test");

			// mc.thePlayer.motionX *= 2F;
			// mc.thePlayer.motionZ *= 2F;
//			double forward = mc.thePlayer.movementInput.moveForward;
//			double strafe = mc.thePlayer.movementInput.moveStrafe;
//			float yaw = mc.thePlayer.rotationYaw;
//			if (forward == 0.0D && strafe == 0.0D) {
//				mc.thePlayer.motionX = 0;
//				mc.thePlayer.motionZ = 0;
//			} else {
//				if (forward != 0.0D) {
//					if (strafe > 0.0D) {
//						yaw += ((forward > 0.0D) ? -45 : 45);
//					} else if (strafe < 0.0D) {
//						yaw += ((forward > 0.0D) ? 45 : -45);
//					}
//					strafe = 0.0D;
//					if (forward > 0.0D) {
//						forward = 1.0D;
//					} else if (forward < 0.0D) {
//						forward = -1.0D;
//					}
//				}
//				if (MoveUtils.isMoving()) {
//					mc.thePlayer.motionX = (forward * 0.5 * Math.cos(Math.toRadians((yaw + 90.0F)))
//							+ strafe * 1 * Math.sin(Math.toRadians((yaw + 90.0F))));
//					mc.thePlayer.motionZ = (forward * 0.5 * Math.sin(Math.toRadians((yaw + 90.0F)))
//							- strafe * 1 * Math.cos(Math.toRadians((yaw + 90.0F))));
//					if (mc.thePlayer.onGround) {
//						mc.thePlayer.jump();
//					}
//				}
			if (mc.thePlayer.onGround) {

				mc.thePlayer.jump();
			}

			for (int i = 0; i < 30; i++) {
				mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(
						new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1D, mc.thePlayer.posZ), 0, null, 0F, 0F,
						0F));
				setMoveSpeed(Client.instance.settingsManager.getSettingByID("speedSlider").getValDouble());
			}

//			}
			if (!(mc.thePlayer.moveStrafing == 0) && mc.thePlayer.moveForward == 0) {
				MoveUtils.strafe(0.5F);
			} else if (!(mc.thePlayer.moveStrafing == 0) && !(mc.thePlayer.moveForward == 0)) {
				MoveUtils.strafe(0.5F);
			}
		} else if (Client.instance.settingsManager.getSettingByID("speedSpeed").getValString()
				.equalsIgnoreCase("Normal")) {
			this.setDisplayName("Speed §7" + "Normal");

			if (mc.thePlayer.onGround) {

				mc.thePlayer.jump();
			}
			setMoveSpeed(Client.instance.settingsManager.getSettingByID("speedSlider").getValDouble());

		}
	}

	public void setMoveSpeed(double moveSpeed) {
		double forward = mc.thePlayer.movementInput.moveForward;
		double strafe = mc.thePlayer.movementInput.moveStrafe;
		float yaw = mc.thePlayer.rotationYaw;
		if (forward == 0.0 && strafe == 0.0) {
			mc.thePlayer.motionX = 0.0;
			mc.thePlayer.motionZ = 0.0;
		}
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
		if (MoveUtils.isMoving()) {
			mc.thePlayer.motionX = (forward * moveSpeed * Math.cos(Math.toRadians((yaw + 90.0F)))
					+ strafe * moveSpeed * Math.sin(Math.toRadians((yaw + 90.0F))));
			mc.thePlayer.motionZ = (forward * moveSpeed * Math.sin(Math.toRadians((yaw + 90.0F)))
					- strafe * moveSpeed * Math.cos(Math.toRadians((yaw + 90.0F))));

		}
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Normal");
		options.add("Slow");
		options.add("Y-Port");
		options.add("Test");
		Client.instance.settingsManager.rSetting(new Setting("Mode", this, "Normal", options, "speedSpeed"));
		Client.instance.settingsManager.rSetting(new Setting("Speed", this, 1, 0, 5, "speedSlider", true, 0.1));

	}

	private boolean isOnLiquid() {
		boolean onLiquid = false;
		final int y = (int) (mc.thePlayer.boundingBox.minY - .01);
		for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(mc.thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
				net.minecraft.block.Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid))
						return false;
					onLiquid = true;
				}
			}
		}
		return onLiquid;
	}

}
