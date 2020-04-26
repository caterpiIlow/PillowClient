package caterpillow.utils;

import caterpillow.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

// From LiquidBounce & Others
public class MoveUtils {
	private static Minecraft mc = Minecraft.getMinecraft();

	public static float getSpeed() {
		return (float) Math
				.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
	}

	public static void strafe() {
		strafe(getSpeed());
	}

	public static boolean isMoving() {
		return mc.thePlayer != null
				&& (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
	}

	public static boolean hasMotion() {
		return mc.thePlayer.motionX != 0D && mc.thePlayer.motionZ != 0D && mc.thePlayer.motionY != 0D;
	}

	public static void strafe(final float speed) {
		if (!isMoving())
			return;

		final double yaw = getDirection();
		mc.thePlayer.motionX = -Math.sin(yaw) * speed;
		mc.thePlayer.motionZ = Math.cos(yaw) * speed;
	}

	public static double getDirection() {
		float rotationYaw = mc.thePlayer.rotationYaw;

		if (mc.thePlayer.moveForward < 0F)
			rotationYaw += 180F;

		float forward = 1F;
		if (mc.thePlayer.moveForward < 0F)
			forward = -0.5F;
		else if (mc.thePlayer.moveForward > 0F)
			forward = 0.5F;

		if (mc.thePlayer.moveStrafing > 0F)
			rotationYaw -= 90F * forward;

		if (mc.thePlayer.moveStrafing < 0F)
			rotationYaw += 90F * forward;

		return Math.toRadians(rotationYaw);
	}

	public static void forward(final double length) {
		final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
		mc.thePlayer.setPosition(mc.thePlayer.posX + (-Math.sin(yaw) * length), mc.thePlayer.posY,
				mc.thePlayer.posZ + (Math.cos(yaw) * length));
	}

	public static double getBaseMoveSpeed() {
		double baseSpeed = 0.2873D;
		if (mc.thePlayer != null && (Minecraft.getMinecraft()).thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = (Minecraft.getMinecraft()).thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0D + 0.2D * (amplifier + 1);
		}
		return baseSpeed;
	}

	public static void setMoveSpeed(Event aab, double moveSpeed) {
		float forward = MovementInput.moveForward;
		float strafe = MovementInput.moveStrafe;
		float yaw = mc.thePlayer.rotationYaw;
		if (forward == 0.0D && strafe == 0.0D) {
			mc.thePlayer.motionX = 0.0D;
			mc.thePlayer.motionZ = 0.0D;
		} else {
			if (forward != 0.0D) {
				if (strafe > 0.0F) {
					yaw += ((forward > 0.0D) ? -45 : 45);
				} else if (strafe < 0.0F) {
					yaw += ((forward > 0.0D) ? 45 : -45);
				}
				strafe = 0.0F;
				if (forward > 0.0F) {
					forward = 1.0F;
				} else if (forward < 0.0F) {
					forward = -1.0F;
				}
			}
			double xDist = forward * moveSpeed * Math.cos(Math.toRadians((yaw + 90.0F)))
					+ strafe * moveSpeed * Math.sin(Math.toRadians((yaw + 90.0F)));
			double zDist = forward * moveSpeed * Math.sin(Math.toRadians((yaw + 90.0F)))
					- strafe * moveSpeed * Math.cos(Math.toRadians((yaw + 90.0F)));
		}
	}

	public static void setMoveSpeed(double moveSpeed) {
		float forward = MovementInput.moveForward;
		float strafe = MovementInput.moveStrafe;
		float yaw = mc.thePlayer.rotationYaw;
		if (forward == 0.0D && strafe == 0.0D) {
			mc.thePlayer.motionX = 0.0D;
			mc.thePlayer.motionZ = 0.0D;
		}
		int d = 45;
		if (forward != 0.0D) {
			if (strafe > 0.0D) {
				yaw += ((forward > 0.0D) ? -d : d);
			} else if (strafe < 0.0D) {
				yaw += ((forward > 0.0D) ? d : -d);
			}
			strafe = 0.0F;
			if (forward > 0.0D) {
				forward = 1.0F;
			} else if (forward < 0.0D) {
				forward = -1.0F;
			}
		}
		double xDist = forward * moveSpeed * Math.cos(Math.toRadians((yaw + 90.0F)))
				+ strafe * moveSpeed * Math.sin(Math.toRadians((yaw + 90.0F)));
		double zDist = forward * moveSpeed * Math.sin(Math.toRadians((yaw + 90.0F)))
				- strafe * moveSpeed * Math.cos(Math.toRadians((yaw + 90.0F)));
		mc.thePlayer.motionX = xDist;
		mc.thePlayer.motionZ = zDist;
	}

	public static void setSpeed(double speed) {
		if (isMoving()) {
			(Minecraft.getMinecraft()).thePlayer.motionX = -MathHelper.sin(0F) * speed;
			(Minecraft.getMinecraft()).thePlayer.motionZ = MathHelper.cos(0F) * speed;
		} else {
			(Minecraft.getMinecraft()).thePlayer.motionX = 0.0D;
			(Minecraft.getMinecraft()).thePlayer.motionZ = 0.0D;
		}
	}

	public static long getBaseMoveSpeedLong() {
		double baseSpeed = 32.0D;
		if ((Minecraft.getMinecraft()).thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = (Minecraft.getMinecraft()).thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0D + 0.2D * (amplifier + 1);
		}
		return (long) (baseSpeed / 32.0D);
	}

	public static float getBaseMoveSpeedFloat() {
		double baseSpeed = 0.6D;
		if ((Minecraft.getMinecraft()).thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = (Minecraft.getMinecraft()).thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0D + 0.2D * (amplifier + 1);
		}
		return (float) baseSpeed;
	}

	public static double getBaseSpeed() {
		return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
	}

	public static boolean setMoveSpeed(long val) {
		float f = (float) val * 0.1F;
		setSpeed(f);
		return true;
	}

	public static boolean setBaseMoveSpeed() {
		float f = getBaseMoveSpeedFloat();
		setMoveSpeed(f);
		return true;

	}

	public static boolean isOnGround(double height) {
		if (!mc.theWorld.getCollidingBoundingBoxes((Entity) mc.thePlayer,
				mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty())
			return true;
		return false;
	}

}
