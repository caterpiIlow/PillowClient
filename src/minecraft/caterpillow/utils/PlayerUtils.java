package caterpillow.utils;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class PlayerUtils {

	private static Minecraft mc = Minecraft.getMinecraft();

	public static boolean isInLiquid() {
		for (int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); x < MathHelper
				.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; ++x) {
			for (int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper
					.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; ++z) {
				BlockPos pos = new BlockPos(x, (int) Minecraft.getMinecraft().thePlayer.boundingBox.minY, z);
				Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
				if (block != null && !(block instanceof BlockAir))
					return block instanceof BlockLiquid;
			}
		}
		return false;
	}

	public static boolean isInsideBlock() {
		for (int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX); x < MathHelper
				.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; x++) {
			for (int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); y < MathHelper
					.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxY) + 1; y++) {
				for (int z = MathHelper
						.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper
								.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; z++) {
					Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
					if (block != null && !(block instanceof BlockAir)) {
						AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld,
								new BlockPos(x, y, z),
								Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)));
						if (block instanceof BlockHopper)
							boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
						if (boundingBox != null
								&& Minecraft.getMinecraft().thePlayer.boundingBox.intersectsWith(boundingBox))
							return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isMoving() {
		if (!mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isSneaking())
			return (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F);
		return false;
	}

	public static ArrayList array;

	public static ArrayList getHotBarSlotsWithBlocks() {
		for (int i = 0; i < 9; i++) {
			if (mc.thePlayer.inventory.getStackInSlot(i) != null
					&& mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && mc.thePlayer.inventory
							.getStackInSlot(PlayerUtils.getFirstHotBarSlotWithBlocks()).stackSize > 0) {
				array.add(i);
			}
		}
		return array;
	}

	public static int getFirstHotBarSlotWithBlocks() {
		for (int i = 0; i < 9; i++) {
			if (mc.thePlayer.inventory.getStackInSlot(i) != null
					&& mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
				return i;
			}
		}
		return 0;
	}

	public static boolean doesSlotHaveBlocks(int slotToCheck) {
		if (mc.thePlayer.inventory.getStackInSlot(slotToCheck).getItem() != null
				&& mc.thePlayer.inventory.getStackInSlot(slotToCheck).getItem() instanceof ItemBlock) {
			return true;
		}
		return false;
	}

}