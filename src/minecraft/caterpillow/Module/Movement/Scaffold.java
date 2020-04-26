package caterpillow.Module.Movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventPreMotionUpdate;
import caterpillow.utils.PlayerUtils;
import de.Hero.settings.Setting;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Scaffold extends Module {
	public Scaffold() {
		super("Scaffold", Keyboard.KEY_X, Category.MISC);
	}

	@EventTarget
	public void onPre(EventPreMotionUpdate event) {
		BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY - 1D, mc.thePlayer.posZ);

		int facing = mc.thePlayer.getHorizontalFacing().getIndex();
		if (Client.instance.settingsManager.getSettingByName("Type").getValString().equalsIgnoreCase("Basic")) {
			this.setDisplayName("Scaffold §7Basic");

//		if (mc.thePlayer.)
//		mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(
//				new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1D, mc.thePlayer.posZ), 0, null, 0F, 0F, 0F));
			mc.thePlayer.sendQueue
					.addToSendQueue(new C09PacketHeldItemChange(PlayerUtils.getFirstHotBarSlotWithBlocks()));
//		for (int i = 0; i < 10; i++) {
			if (isBlockUnder() && PlayerUtils.doesSlotHaveBlocks(PlayerUtils.getFirstHotBarSlotWithBlocks())) {
				mc.thePlayer.swingItem();
				mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(pos), facing,
						mc.thePlayer.inventoryContainer.getSlot(PlayerUtils.getFirstHotBarSlotWithBlocks()).getStack(),
						0F, 0F, 0F));

			}

//		}
			mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
		} else if (Client.instance.settingsManager.getSettingByName("Type").getValString()
				.equalsIgnoreCase("NoItemSpoof")) {
			this.setDisplayName("Scaffold §7NoItemSpoof");
			if (isBlockUnder() && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
				mc.thePlayer.swingItem();
				mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(pos), facing,
						mc.thePlayer.inventory.getCurrentItem(), 0F, 0F, 0F));

			}
		}

	}

	@Override
	public boolean isBlockUnder() {
		BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.boundingBox.minY - 2D, mc.thePlayer.posZ);

		return mc.theWorld.isAirBlock(new BlockPos(pos));
	}

	private int getOffset(int facing, boolean x) {
		int direction = 0;
		if (facing == EnumFacing.NORTH.getIndex()) {
			if (x) {
				direction = 0;
			} else {
				direction = 1;
			}

		}
		if (facing == EnumFacing.SOUTH.getIndex()) {
			if (x) {
				direction = 0;
			} else {
				direction = -1;
			}

		}

		if (facing == EnumFacing.EAST.getIndex()) {
			if (x) {
				direction = 1;
			} else {
				direction = 0;
			}

		}
		if (facing == EnumFacing.WEST.getIndex()) {
			if (x) {
				direction = -1;
			} else {
				direction = 0;
			}

		}
//		System.out.println(direction);
//		return direction;
		return 0;
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Basic");
		options.add("NoItemSpoof");
		Client.instance.settingsManager.rSetting(new Setting("Type", this, "Basic", options));
	}
}
