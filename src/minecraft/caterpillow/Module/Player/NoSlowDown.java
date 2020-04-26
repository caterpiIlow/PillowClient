package caterpillow.Module.Player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventPreMotionUpdate;
import caterpillow.utils.MoveUtils;
import caterpillow.utils.PlayerUtils;
import de.Hero.settings.Setting;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowDown extends Module {

	public NoSlowDown() {
		super("NoSlowDown", Keyboard.KEY_NONE, Category.PLAYER);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Vanilla");
		options.add("NCP");
		Client.instance.settingsManager.rSetting(new Setting("NoSlowMode", this, "Vanilla", options));
	}

	@EventTarget
	public void onPre(EventPreMotionUpdate event) {
		this.setDisplayName("NoSlowDown §7broken");
		if (Client.instance.settingsManager.getSettingByName("NoSlowMode").getValString().equalsIgnoreCase("Vanilla")) {
			if (mc.gameSettings.keyBindSprint.pressed && mc.thePlayer.moveForward > 0
					&& mc.thePlayer.isCollidedHorizontally != true && mc.gameSettings.keyBindBack.pressed != true) {
				mc.thePlayer.setSprinting(true);
			}
		} else if (Client.instance.settingsManager.getSettingByName("NoSlowMode").getValString()
				.equalsIgnoreCase("NCP")) {
			if (mc.thePlayer.isBlocking() && PlayerUtils.isMoving() && MoveUtils.isOnGround(0.42D)
					&& !(Client.killAuraAttacking()
							&& Client.instance.settingsManager.getSettingByName("Autoblock").getValBoolean())) {
				double x = mc.thePlayer.posX, y = mc.thePlayer.posY, z = mc.thePlayer.posZ;
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
						C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

//				if (em.isPost())
//					mc.thePlayer.sendQueue
//							.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
			}
//			if (Client.instance.moduleManager.getModulesByName("KeepSprint").isToggled() && mc.thePlayer.moveForward > 0
//					&& mc.thePlayer.isCollidedHorizontally != true && mc.gameSettings.keyBindBack.pressed != true) {
//				mc.thePlayer.setSprinting(true);
//			}
		}
	}

}
