package caterpillow.Module.Player;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {
	public NoFall() {
		super("NoFall", Keyboard.KEY_I, Category.PLAYER);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.setDisplayName("NoFall §7Vanilla");
		if (mc.thePlayer.fallDistance > 2F)
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	}

}
