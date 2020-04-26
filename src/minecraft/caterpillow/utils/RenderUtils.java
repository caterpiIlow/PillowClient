/*
 * Copyright © 2014 - 2017 | Wurst-Imperium | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package caterpillow.utils;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import caterpillow.Client.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class RenderUtils {
	/**
	 * Renders a box with any size and any color.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param color
	 */
	public static void box(double x, double y, double z, double x2, double y2, double z2, Color color) {
		x = x - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y = y - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z = z - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		x2 = x2 - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y2 = y2 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z2 = z2 - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		setColor(color);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		drawColorBox(new AxisAlignedBB(x, y, z, x2, y2, z2));
		GL11.glColor4d(0, 0, 0, 0.5F);
		RenderGlobal.func_181563_a(new AxisAlignedBB(x, y, z, x2, y2, z2), 255, 255, 0, 50);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	/**
	 * Renders a frame with any size and any color.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param color
	 */
	public static void frame(double x, double y, double z, double x2, double y2, double z2, Color color) {
		x = x - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y = y - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z = z - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		x2 = x2 - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y2 = y2 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z2 = z2 - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		setColor(color);
		RenderGlobal.func_181563_a(new AxisAlignedBB(x, y, z, x2, y2, z2), 255, 255, 0, 50);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	/**
	 * Renders an ESP box with the size of a normal block at the specified
	 * coordinates.
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void blockESPBox(BlockPos blockPos) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.0F);
		GL11.glColor4d(0, 1, 0, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glColor4d(0, 0, 0, 0.5F);
		RenderGlobal.func_181563_a(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 255, 255, 0, 50);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void framelessBlockESP(BlockPos blockPos, Color color) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glColor4d(color.getRed() / 255, color.getGreen() / 255, color.getBlue() / 255, 0.15);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void emptyBlockESPBox(BlockPos blockPos) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4d(0, 0, 0, 0.5F);
		RenderGlobal.func_181563_a(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 255, 255, 0, 50);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static int enemy = 0;
	public static int friend = 1;
	public static int other = 2;
	public static int target = 3;
	public static int team = 4;

	public static void entityESPBox(Entity entity, int mode) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		if (mode == 0)// Enemy
			GL11.glColor4d(1 - Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
					Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40, 0, 0.5F);
		else if (mode == 1)// Friend
			GL11.glColor4d(0, 0, 1, 0.5F);
		else if (mode == 2)// Other
			GL11.glColor4d(1, 1, 0, 0.5F);
		else if (mode == 3)// Target
			GL11.glColor4d(1, 0, 0, 0.5F);
		else if (mode == 4)// Team
			GL11.glColor4d(0, 1, 0, 0.5F);
		Minecraft.getMinecraft().getRenderManager();
		RenderGlobal
				.func_181563_a(
						new AxisAlignedBB(
								entity.boundingBox.minX - 0.05 - entity.posX
										+ (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX),
								entity.boundingBox.minY - entity.posY
										+ (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY),
								entity.boundingBox.minZ - 0.05 - entity.posZ
										+ (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ),
								entity.boundingBox.maxX + 0.05 - entity.posX
										+ (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX),
								entity.boundingBox.maxY + 0.1 - entity.posY
										+ (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY),
								entity.boundingBox.maxZ + 0.05 - entity.posZ
										+ (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ)),
						255, 255, 0, 50);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void nukerBox(BlockPos blockPos, float damage) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.0F);
		GL11.glColor4d(damage, 1 - damage, 0, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		drawColorBox(new AxisAlignedBB(x + 0.5 - damage / 2, y + 0.5 - damage / 2, z + 0.5 - damage / 2,
				x + 0.5 + damage / 2, y + 0.5 + damage / 2, z + 0.5 + damage / 2));
		GL11.glColor4d(0, 0, 0, 0.5F);
		RenderGlobal.func_181563_a(new AxisAlignedBB(x + 0.5 - damage / 2, y + 0.5 - damage / 2, z + 0.5 - damage / 2,
				x + 0.5 + damage / 2, y + 0.5 + damage / 2, z + 0.5 + damage / 2), 255, 255, 0, 50);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void searchBox(BlockPos blockPos) {
		double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth(1.0F);
		float sinus = 1F - MathHelper
				.abs(MathHelper.sin(Minecraft.getSystemTime() % 10000L / 10000.0F * (float) Math.PI * 4.0F) * 1F);
		GL11.glColor4d(1 - sinus, sinus, 0, 0.15);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glColor4d(0, 0, 0, 0.5);
		RenderGlobal.func_181563_a(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 255, 255, 0, 50);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}

	public static void drawColorBox(AxisAlignedBB axisalignedbb) {
		Tessellator ts = Tessellator.getInstance();
		WorldRenderer wr = ts.getWorldRenderer();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts X.
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		ts.draw();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		ts.draw();// Ends X.
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Y.
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		ts.draw();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		ts.draw();// Ends Y.
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Z.
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		ts.draw();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		ts.draw();// Ends Z.
	}

	public static void tracerLine(Entity entity, int mode) {
		double x = entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = entity.posY + entity.height / 2 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		glBlendFunc(770, 771);
		glEnable(GL_BLEND);
		glLineWidth(2.0F);
		glDisable(GL11.GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glDepthMask(false);
		if (mode == 0)// Enemy
			GL11.glColor4d(1 - Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
					Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40, 0, 0.5F);
		else if (mode == 1)// Friend
			GL11.glColor4d(0, 0, 1, 0.5F);
		else if (mode == 2)// Other
			GL11.glColor4d(1, 1, 0, 0.5F);
		else if (mode == 3)// Target
			GL11.glColor4d(1, 0, 0, 0.5F);
		else if (mode == 4)// Team
			GL11.glColor4d(0, 1, 0, 0.5F);
		glBegin(GL_LINES);
		{
			glVertex3d(0, Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0);
			glVertex3d(x, y, z);
		}
		glEnd();
		glEnable(GL11.GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	public static void tracerLine(Entity entity, Color color) {
		double x = entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX;
		double y = entity.posY + entity.height / 2 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		double z = entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		glBlendFunc(770, 771);
		glEnable(GL_BLEND);
		glLineWidth(2.0F);
		glDisable(GL11.GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glDepthMask(false);
		setColor(color);
		glBegin(GL_LINES);
		{
			glVertex3d(0, Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0);
			glVertex3d(x, y, z);
		}
		glEnd();
		glEnable(GL11.GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	public static void tracerLine(double x, double y, double z, Color color) {
		x += 0.5 - Minecraft.getMinecraft().getRenderManager().renderPosX;
		y += 0.5 - Minecraft.getMinecraft().getRenderManager().renderPosY;
		z += 0.5 - Minecraft.getMinecraft().getRenderManager().renderPosZ;
		glBlendFunc(770, 771);
		glEnable(GL_BLEND);
		glLineWidth(2.0F);
		glDisable(GL11.GL_TEXTURE_2D);
		glDisable(GL_DEPTH_TEST);
		glDepthMask(false);
		setColor(color);
		glBegin(GL_LINES);
		{
			glVertex3d(0, Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0);
			glVertex3d(x, y, z);
		}
		glEnd();
		glEnable(GL11.GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	public static void scissorBox(int x, int y, int xend, int yend) {
		int width = xend - x;
		int height = yend - y;
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int factor = sr.getScaleFactor();
		int bottomY = Minecraft.getMinecraft().currentScreen.height - yend;
		glScissor(x * factor, bottomY * factor, width * factor, height * factor);
	}

	public static void setColor(Color c) {
		glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}

	public static float[] getRGB(Color c) {
		return new float[] { c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f };
	}

	public static void drawSolidBox() {
		glBegin(GL_QUADS);
		{
			glVertex3d(0, 0, 0);
			glVertex3d(1, 0, 0);
			glVertex3d(1, 0, 1);
			glVertex3d(0, 0, 1);

			glVertex3d(0, 1, 0);
			glVertex3d(0, 1, 1);
			glVertex3d(1, 1, 1);
			glVertex3d(1, 1, 0);

			glVertex3d(0, 0, 0);
			glVertex3d(0, 1, 0);
			glVertex3d(1, 1, 0);
			glVertex3d(1, 0, 0);

			glVertex3d(1, 0, 0);
			glVertex3d(1, 1, 0);
			glVertex3d(1, 1, 1);
			glVertex3d(1, 0, 1);

			glVertex3d(0, 0, 1);
			glVertex3d(1, 0, 1);
			glVertex3d(1, 1, 1);
			glVertex3d(0, 1, 1);

			glVertex3d(0, 0, 0);
			glVertex3d(0, 0, 1);
			glVertex3d(0, 1, 1);
			glVertex3d(0, 1, 0);
		}
		glEnd();
	}

	public static void drawOutlinedBox() {
		glBegin(GL_LINES);
		{
			glVertex3d(0, 0, 0);
			glVertex3d(1, 0, 0);

			glVertex3d(1, 0, 0);
			glVertex3d(1, 0, 1);

			glVertex3d(1, 0, 1);
			glVertex3d(0, 0, 1);

			glVertex3d(0, 0, 1);
			glVertex3d(0, 0, 0);

			glVertex3d(0, 0, 0);
			glVertex3d(0, 1, 0);

			glVertex3d(1, 0, 0);
			glVertex3d(1, 1, 0);

			glVertex3d(1, 0, 1);
			glVertex3d(1, 1, 1);

			glVertex3d(0, 0, 1);
			glVertex3d(0, 1, 1);

			glVertex3d(0, 1, 0);
			glVertex3d(1, 1, 0);

			glVertex3d(1, 1, 0);
			glVertex3d(1, 1, 1);

			glVertex3d(1, 1, 1);
			glVertex3d(0, 1, 1);

			glVertex3d(0, 1, 1);
			glVertex3d(0, 1, 0);
		}
		glEnd();
	}

	public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha,
			float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(2);
		GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
		GL11.glVertex3d(x, y, z);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	public static void entityESPBox(Entity entity, int r, int g, int b) {
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL_BLEND);
		GL11.glLineWidth((float) Client.instance.settingsManager.getSettingByName("Thickness").getValDouble());
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL_DEPTH_TEST);
		GL11.glDepthMask(false);
//		setColor(color);

//		if (mode == 0)// Enemy
//			GL11.glColor4d(1 - Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
//					Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40, 0, 0.5F);
//		else if (mode == 1)// Friend
//			GL11.glColor4d(0, 0, 1, 0.5F);
//		else if (mode == 2)// Other
//			GL11.glColor4d(1, 1, 0, 0.5F);
//		else if (mode == 3)// Target
//			GL11.glColor4d(1, 0, 0, 0.5F);
//		else if (mode == 4)// Team
//			GL11.glColor4d(0, 1, 0, 0.5F);
		Minecraft.getMinecraft().getRenderManager();
		RenderGlobal.func_181563_a(
				new AxisAlignedBB(
						entity.boundingBox.minX - 0.05 - entity.posX
								+ (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX),
						entity.boundingBox.minY - entity.posY
								+ (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY),
						entity.boundingBox.minZ - 0.05 - entity.posZ
								+ (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ),
						entity.boundingBox.maxX + 0.05 - entity.posX
								+ (entity.posX - Minecraft.getMinecraft().getRenderManager().renderPosX),
						entity.boundingBox.maxY + 0.1 - entity.posY
								+ (entity.posY - Minecraft.getMinecraft().getRenderManager().renderPosY),
						entity.boundingBox.maxZ + 0.05 - entity.posZ
								+ (entity.posZ - Minecraft.getMinecraft().getRenderManager().renderPosZ)),
				r, g, b, (int) Client.instance.settingsManager.getSettingByName("Alpha").getValDouble());
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL_BLEND);
	}
}