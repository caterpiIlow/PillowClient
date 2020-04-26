package caterpillow.Module.Combat;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventPostMotionUpdate;
import caterpillow.event.events.EventPreMotionUpdate;
import caterpillow.event.events.EventUpdate;
import caterpillow.utils.EntityUtils;
import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class KillAura extends Module {
	private EntityLivingBase target;
	private long current, last;
	private float yaw, pitch;
	private boolean others;
	public static float sYaw;
	public static float sPitch;
	public static float serverYaw;
	public static float serverPitch;
	float[] rot;

	EntityUtils EntityUtils = new EntityUtils();

	public KillAura() {
		super("KillAura", Keyboard.KEY_Q, Category.MISC);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("None");
		options.add("Basic");
		options.add("Slow");
		options.add("Experimental");
		Client.instance.settingsManager.rSetting(new Setting("Rotations", this, "None", options));
		Client.instance.settingsManager.rSetting(new Setting("Particles", this, 5, 0, 15, true));
		Client.instance.settingsManager.rSetting(new Setting("Existed", this, 30, 0, 500, true));
		Client.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
		Client.instance.settingsManager.rSetting(new Setting("Range", this, 4.2, 2, 7, true, 0.1));
		Client.instance.settingsManager.rSetting(new Setting("APS", this, 6, 8, 20, true));
		Client.instance.settingsManager.rSetting(new Setting("Silent Aim", this, true));
		Client.instance.settingsManager.rSetting(new Setting("AutoBlock", this, true));
		Client.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Players", this, true));
		Client.instance.settingsManager.rSetting(new Setting("Animals", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Monsters", this, true));
		Client.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Teams", this, false));
	}

	@EventTarget
	public void onPre(EventPreMotionUpdate event) {
		double delay = Client.instance.settingsManager.getSettingByName("APS").getValDouble();
		target = getClosest(Client.instance.settingsManager.getSettingByName("Range").getValDouble());
		if (target == null) {
			serverRotateDefault();
			return;
		}

		// server rotate
		// serverRotateEntity(target);

		updateTime();
		yaw = mc.thePlayer.prevRotationYaw;
		pitch = mc.thePlayer.rotationPitch;
		boolean block = target != null && Client.instance.settingsManager.getSettingByName("AutoBlock").getValBoolean()
				&& mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null
				&& mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
		if (block && target.getDistanceToEntity(mc.thePlayer) < Client.instance.settingsManager
				.getSettingByName("Range").getValDouble()) {
			mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
		}
		if (current - last > 1000 / (delay + (Math.random() * 2) - 1)) {
			serverRotateEntity(target);
			resetTime();
		}
	}

	@EventTarget
	public void onPost(EventPostMotionUpdate event) {
		if (Client.instance.settingsManager.getSettingByName("Rotations").getValString().equalsIgnoreCase("None")) {
			this.setDisplayName("Killaura§7 None");
		} else if (Client.instance.settingsManager.getSettingByName("Rotations").getValString()
				.equalsIgnoreCase("Basic")) {
			this.setDisplayName("Killaura§7 Basic");
		} else if (Client.instance.settingsManager.getSettingByName("Rotations").getValString()
				.equalsIgnoreCase("Slow")) {
			this.setDisplayName("Killaura§7 Slow");
		} else if (Client.instance.settingsManager.getSettingByName("Rotations").getValString()
				.equalsIgnoreCase("Experimental")) {
			this.setDisplayName("Killaura§7 Bad");
		}
		if (target == null)
			return;
		// mc.thePlayer.rotationYaw = yaw;
		// mc.thePlayer.rotationPitch = pitch;
	}

	private void serverRotateEntity(Entity entity) {
		rot = EntityUtils.getRotationsNeeded(entity);
		EventUpdate EventUpdate = new EventUpdate();

		if (Client.instance.settingsManager.getSettingByName("Rotations").getValString().equalsIgnoreCase("None")) {
			// none
			this.attack(entity);

		} else if (Client.instance.settingsManager.getSettingByName("Rotations").getValString()
				// basic
				.equalsIgnoreCase("Basic")) {
			this.setDisplayName("Killaura§7 Basic");

			serverYaw = rot[0];
			serverPitch = rot[1];
			if (Client.instance.settingsManager.getSettingByName("Silent Aim").getValBoolean()) {
				mc.thePlayer.sendQueue.addToSendQueue(
						new C03PacketPlayer.C05PacketPlayerLook(serverYaw, serverPitch, EventUpdate.isOnground()));
			} else {
				mc.thePlayer.rotationYaw = serverYaw;
				mc.thePlayer.rotationPitch = serverPitch;
			}
			this.attack(entity);

		} else if (Client.instance.settingsManager.getSettingByName("Rotations").getValString()
				// experimental
				.equalsIgnoreCase("Experimental")) {
			this.setDisplayName("Killaura§7 Bad");

			serverYaw = EntityUtils.limitAngleChange(serverYaw, rot[0], Math.abs(serverYaw - rot[0]));
			serverPitch = EntityUtils.limitAngleChange(serverPitch, rot[1], Math.abs(serverPitch - rot[1]));
			if (Client.instance.settingsManager.getSettingByName("Silent Aim").getValBoolean()) {
				mc.thePlayer.sendQueue.addToSendQueue(
						new C03PacketPlayer.C05PacketPlayerLook(serverYaw, serverPitch, EventUpdate.isOnground()));
			} else {
				mc.thePlayer.rotationYaw = serverYaw;
				mc.thePlayer.rotationPitch = serverPitch;
			}
			this.attack(entity);

		} else if (Client.instance.settingsManager.getSettingByName("Rotations").getValString()
				// wurst
				.equalsIgnoreCase("Slow")) {
			this.setDisplayName("Killaura§7 Slow");

			if (Client.instance.settingsManager.getSettingByName("Silent Aim").getValBoolean()) {

				serverYaw = rot[0];
				serverPitch = rot[1];
				EntityUtils.faceEntitySilent(serverYaw, serverPitch, entity);
				System.out.println("face enetity silent with attack");

			} else {

				if (EntityUtils.getDistanceFromMouse(entity) > 55) {

					EntityUtils.faceEntityClient(entity);
				} else {

					EntityUtils.faceEntityClient(entity);
					this.attack(entity);
				}

			}
		}

	}

	public void attack(Entity entity) {
		for (int i = 0; i < Client.instance.settingsManager.getSettingByName("Particles").getValDouble(); i++)
			mc.thePlayer.onCriticalHit(entity);

		rot = EntityUtils.getRotationsNeeded(entity);

		EventUpdate EventUpdate = new EventUpdate();

		// EventUpdate.setYaw(rot[0]);
		// EventUpdate.setPitch(rot[1]);

		// sYaw = mc.thePlayer.rotationYaw;
		// sPitch = mc.thePlayer.rotationPitch;
		// serverYaw = (rot[0] - sYaw) / 2 + sYaw;
		// serverPitch = (rot[1] - sPitch) / 2 + sPitch;

		// mc.thePlayer.prevRotationYaw = rot[0];
		// mc.thePlayer.rotationPitch = rot[1];

		mc.thePlayer.swingItem();
		mc.playerController.attackEntity(mc.thePlayer, entity);
		// mc.thePlayer.sendQueue.addToSendQueue(new
		// C03PacketPlayer.C05PacketPlayerLook(rot[0], rot[1],
		// EventUpdate.isOnground()));

	}

	private void updateTime() {
		current = (System.nanoTime() / 1000000L);
	}

	private void resetTime() {
		last = (System.nanoTime() / 1000000L);
	}

	private void serverRotateDefault() {
		EventUpdate EventUpdate = new EventUpdate();

		if (Client.instance.settingsManager.getSettingByName("Rotations").getValString().equalsIgnoreCase("None")) {
			mc.thePlayer.sendQueue.addToSendQueue(
					new C03PacketPlayer.C05PacketPlayerLook(serverYaw, serverPitch, EventUpdate.isOnground()));
		} else if (Client.instance.settingsManager.getSettingByName("Rotations").getValString()
				.equalsIgnoreCase("Basic")) {
			serverYaw = mc.thePlayer.rotationYaw;
			serverPitch = mc.thePlayer.rotationPitch;
			if (Client.instance.settingsManager.getSettingByName("Silent Aim").getValBoolean()) {
				mc.thePlayer.sendQueue.addToSendQueue(
						new C03PacketPlayer.C05PacketPlayerLook(serverYaw, serverPitch, EventUpdate.isOnground()));
			} else {
				mc.thePlayer.rotationYaw = serverYaw;
				mc.thePlayer.rotationPitch = serverPitch;
			}

		} else if (Client.instance.settingsManager.getSettingByName("Rotations").getValString()
				.equalsIgnoreCase("Experimental")) {
			serverYaw = EntityUtils.limitAngleChange(mc.thePlayer.rotationYaw, serverYaw, Math.abs(serverYaw - rot[0]));
			serverPitch = EntityUtils.limitAngleChange(mc.thePlayer.rotationPitch, serverPitch,
					Math.abs(serverPitch - rot[1]));
			if (Client.instance.settingsManager.getSettingByName("Silent Aim").getValBoolean()) {
				mc.thePlayer.sendQueue.addToSendQueue(
						new C03PacketPlayer.C05PacketPlayerLook(serverYaw, serverPitch, EventUpdate.isOnground()));
			} else {
				mc.thePlayer.rotationYaw = serverYaw;
				mc.thePlayer.rotationPitch = serverPitch;
			}
		} else if (Client.instance.settingsManager.getSettingByName("Rotations").getValString()
				.equalsIgnoreCase("Slow")) {

			if (Client.instance.settingsManager.getSettingByName("Silent Aim").getValBoolean()) {
				System.out.println("face entity silent without attack");
				EntityUtils.faceEntitySilent(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, null);

			} else {
				// EntityUtils.resetPrevRotation();
				EntityUtils.faceDefault();

			}

		}

	}

	@Override
	public EntityLivingBase getClosest(double range) {
		double dist = range;
		EntityLivingBase target = null;
		for (Object object : mc.theWorld.loadedEntityList) {
			Entity entity = (Entity) object;
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase player = (EntityLivingBase) entity;
				if (canAttack(player)) {
					double currentDist = mc.thePlayer.getDistanceToEntity(player);
					if (currentDist <= dist) {
						dist = currentDist;
						target = player;

					}
				}
			}
		}
		return target;
	}

	private boolean canAttack(EntityLivingBase player) {
		if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob
				|| player instanceof EntityVillager) {
			if (player instanceof EntityPlayer
					&& !Client.instance.settingsManager.getSettingByName("Players").getValBoolean())
				return false;
			if (player instanceof EntityAnimal
					&& !Client.instance.settingsManager.getSettingByName("Animals").getValBoolean())
				return false;
			if (player instanceof EntityMob
					&& !Client.instance.settingsManager.getSettingByName("Monsters").getValBoolean())
				return false;
			if (player instanceof EntityVillager
					&& !Client.instance.settingsManager.getSettingByName("Villagers").getValBoolean())
				return false;
		}
		if (player.isOnSameTeam(mc.thePlayer)
				&& Client.instance.settingsManager.getSettingByName("Teams").getValBoolean())
			return false;
		if (player.isInvisible() && !Client.instance.settingsManager.getSettingByName("Invisibles").getValBoolean())
			return false;
		if (!isInFOV(player, Client.instance.settingsManager.getSettingByName("FOV").getValDouble()))
			return false;

		return player != mc.thePlayer && player.isEntityAlive()
				&& mc.thePlayer.getDistanceToEntity(player) <= Client.instance.settingsManager.getSettingByName("Range")
						.getValDouble()
				&& player.ticksExisted > Client.instance.settingsManager.getSettingByName("Existed").getValDouble();
	}

	private boolean isInFOV(EntityLivingBase entity, double angle) {
		angle *= .5D;
		double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw,
				getRotations(entity.posX, entity.posY, entity.posZ)[0]);
		return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
	}

	private float getAngleDifference(float dir, float yaw) {
		float f = Math.abs(yaw - dir) % 360F;
		float dist = f > 180F ? 360F - f : f;
		return dist;
	}

	private float[] getRotations(double x, double y, double z) {
		double diffX = x + .5D - mc.thePlayer.posX;
		double diffY = (y + .5D) / 2D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
		double diffZ = z + .5D - mc.thePlayer.posZ;

		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180D / Math.PI);

		return new float[] { yaw, pitch };
	}

}