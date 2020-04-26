/*
 * Copyright © 2014 - 2015 | Alexander01998 | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package caterpillow.utils;

import java.util.ArrayList;
import java.util.UUID;

import caterpillow.event.events.EventUpdate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class EntityUtils {

	public float prevServerRotation;
	public float prevPrevServerRotation;

	public synchronized static void faceEntityClient(Entity entity) {
		float[] rotations = getRotationsNeeded(entity);
		if (rotations != null) {
			Minecraft.getMinecraft().thePlayer.rotationYaw = (float) (limitAngleChange(
					Minecraft.getMinecraft().thePlayer.prevRotationYaw, rotations[0], 55) + Math.random() * 10 - 5);// NoCheat+
			// bypass!!!
			Minecraft.getMinecraft().thePlayer.rotationPitch = (float) (rotations[1] + Math.random() * 10 - 5);
		}
	}

	public synchronized void faceEntitySilent(float serverYaw, float serverPitch, Entity entity) {
		System.out.println("faceEntitySilent");
		if (prevServerRotation == 123456789) {
			prevServerRotation = Minecraft.getMinecraft().thePlayer.rotationYaw;
			System.out.println("prevServerRotation reset");
		}

		float serverRotateYaw = (float) (limitAngleChange(prevServerRotation, serverYaw, 55) + Math.random() * 10 - 5);// NoCheat+
		System.out.println(serverRotateYaw);
		// bypass!!!
		prevServerRotation = serverRotateYaw;
		// prevPrevServerRotation = prevServerRotation;

		EventUpdate EventUpdate = new EventUpdate();

		serverPitch += Math.random() * 10 - 5;

		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(
				new C03PacketPlayer.C05PacketPlayerLook(serverRotateYaw, serverPitch, EventUpdate.isOnground()));
		System.out.println("look packet");

		if (Math.abs(serverYaw - serverRotateYaw) <= 55 && entity != null) {
			Minecraft.getMinecraft().thePlayer.swingItem();
			Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().thePlayer, entity);
			System.out.println("attack");
		}

	}

	public synchronized void faceDefault() {
		float serverYaw;
		float serverPitch;
		serverYaw = (float) (limitAngleChange(Minecraft.getMinecraft().thePlayer.prevRotationYaw,
				Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.rotationPitch)
				+ (float) (Math.random() * 10 - 5));// NoCheat+
		// bypass!!!
		serverPitch = (float) (Minecraft.getMinecraft().thePlayer.rotationPitch + (float) Math.random() * 10 - 5);
		prevServerRotation = 123456789;

		EventUpdate EventUpdate = new EventUpdate();

		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(
				new C03PacketPlayer.C05PacketPlayerLook(serverYaw, serverPitch, EventUpdate.isOnground()));

	}

	public synchronized static void faceEntityPacket(EntityLivingBase entity) {
		float[] rotations = getRotationsNeeded(entity);
		if (rotations != null) {
			float yaw = rotations[0];
			float pitch = rotations[1];
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(
					new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, Minecraft.getMinecraft().thePlayer.onGround));
		}
	}

	public static float[] getRotationsNeeded(Entity entity) {
		if (entity == null)
			return null;
		double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		double diffY;
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9
					- (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
		} else
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D
					- (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
		double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		return new float[] {
				Minecraft.getMinecraft().thePlayer.rotationYaw
						+ MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw),
				Minecraft.getMinecraft().thePlayer.rotationPitch
						+ MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };

	}

	public final static float limitAngleChange(final float current, final float intended, final float maxChange) {
		float change = intended - current;
		if (change > maxChange)
			change = maxChange;
		else if (change < -maxChange)
			change = -maxChange;
		return current + change;
	}

	public static int getDistanceFromMouse(Entity entity) {
		float[] neededRotations = getRotationsNeeded(entity);
		if (neededRotations != null) {
			float neededYaw = Minecraft.getMinecraft().thePlayer.rotationYaw - neededRotations[0],
					neededPitch = Minecraft.getMinecraft().thePlayer.rotationPitch - neededRotations[1];
			float distanceFromMouse = MathHelper.sqrt_float(neededYaw * neededYaw + neededPitch * neededPitch);
			return (int) distanceFromMouse;
		}
		return -1;
	}

	public static boolean isCorrectEntity(Object o, boolean ignoreFriends) {
		return true;
	}

	public static EntityLivingBase getClosestEntity(boolean ignoreFriends) {
		EntityLivingBase closestEntity = null;
		for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
			if (isCorrectEntity(o, ignoreFriends)) {
				EntityLivingBase en = (EntityLivingBase) o;
				if (!(o instanceof EntityPlayerSP) && !en.isDead && en.getHealth() > 0
						&& Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en))
					if (closestEntity == null || Minecraft.getMinecraft().thePlayer.getDistanceToEntity(
							en) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntity))
						closestEntity = en;
			}
		return closestEntity;
	}

	public static ArrayList<EntityLivingBase> getCloseEntities(boolean ignoreFriends, float range) {
		ArrayList<EntityLivingBase> closeEntities = new ArrayList<EntityLivingBase>();
		for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
			if (isCorrectEntity(o, ignoreFriends)) {
				EntityLivingBase en = (EntityLivingBase) o;
				if (!(o instanceof EntityPlayerSP) && !en.isDead && en.getHealth() > 0
						&& Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en)
						&& Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) <= range)
					closeEntities.add(en);
			}
		return closeEntities;
	}

	public static EntityLivingBase getClosestEntityRaw(boolean ignoreFriends) {
		EntityLivingBase closestEntity = null;
		for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
			if (isCorrectEntity(o, ignoreFriends)) {
				EntityLivingBase en = (EntityLivingBase) o;
				if (!(o instanceof EntityPlayerSP) && !en.isDead && en.getHealth() > 0)
					if (closestEntity == null || Minecraft.getMinecraft().thePlayer.getDistanceToEntity(
							en) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntity))
						closestEntity = en;
			}
		return closestEntity;
	}

	public static EntityLivingBase getClosestEnemy(EntityLivingBase friend) {
		EntityLivingBase closestEnemy = null;
		for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
			if (isCorrectEntity(o, true)) {
				EntityLivingBase en = (EntityLivingBase) o;
				if (!(o instanceof EntityPlayerSP) && o != friend && !en.isDead && en.getHealth() <= 0 == false
						&& Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en))
					if (closestEnemy == null || Minecraft.getMinecraft().thePlayer.getDistanceToEntity(
							en) < Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEnemy))
						closestEnemy = en;
			}
		return closestEnemy;
	}

	public static EntityLivingBase searchEntityByIdRaw(UUID ID) {
		EntityLivingBase newEntity = null;
		for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
			if (isCorrectEntity(o, false)) {
				EntityLivingBase en = (EntityLivingBase) o;
				if (!(o instanceof EntityPlayerSP) && !en.isDead)
					if (newEntity == null && en.getUniqueID().equals(ID))
						newEntity = en;
			}
		return newEntity;
	}

	public static EntityLivingBase searchEntityByName(String name) {
		EntityLivingBase newEntity = null;
		for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
			if (isCorrectEntity(o, false)) {
				EntityLivingBase en = (EntityLivingBase) o;
				if (!(o instanceof EntityPlayerSP) && !en.isDead
						&& Minecraft.getMinecraft().thePlayer.canEntityBeSeen(en))
					if (newEntity == null && en.getName().equals(name))
						newEntity = en;
			}
		return newEntity;
	}

	public static EntityLivingBase searchEntityByNameRaw(String name) {
		EntityLivingBase newEntity = null;
		for (Object o : Minecraft.getMinecraft().theWorld.loadedEntityList)
			if (isCorrectEntity(o, false)) {
				EntityLivingBase en = (EntityLivingBase) o;
				if (!(o instanceof EntityPlayerSP) && !en.isDead)
					if (newEntity == null && en.getName().equals(name))
						newEntity = en;
			}
		return newEntity;
	}

	public void resetPrevRotation() {
		prevServerRotation = 123456789;

	}
}