/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package caterpillow.Module.Combat;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventReceivePacket;
import caterpillow.event.events.EventUpdate;
import de.Hero.settings.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Velocity extends Module {
	public Velocity() {
		super("Velocity", Keyboard.KEY_N, Category.MISC);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setup() {
		Client.instance.settingsManager.rSetting(new Setting("horizontal", this, 0, 0, 1, true, 0.1));
		Client.instance.settingsManager.rSetting(new Setting("vertical", this, 0, 0, 1, true, 0.1));
	}

	/*
	 * @Override public void onEnable() { EVENTS.add(KnockbackListener.class, this);
	 * }
	 * 
	 * @Override public void onDisable() { EVENTS.remove(KnockbackListener.class,
	 * this); }
	 */
	// @RegisterEvent(events = { EventPacket.class, EventUpdate.class })
	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.setDisplayName(
				"Velocity§7 H: " + (int) Client.instance.settingsManager.getSettingByName("horizontal").getValDouble()
						+ " V: " + (int) Client.instance.settingsManager.getSettingByName("vertical").getValDouble());
	}

	@EventTarget
	public void onReceivePacket(EventReceivePacket event) {
		// System.out.println("packet received");
		Packet p = event.getPacket();

		if (p instanceof S12PacketEntityVelocity) {
			S12PacketEntityVelocity packet = (S12PacketEntityVelocity) p;
			System.out.println("kb event");
			int horizontal = (int) Client.instance.settingsManager.getSettingByName("horizontal").getValDouble() * 10;
			int vertical = (int) Client.instance.settingsManager.getSettingByName("vertical").getValDouble() * 10;
			// if (vertical != 0 || horizontal != 0) {
			packet.setMotionX(horizontal * packet.getMotionX() / 10);
			packet.setMotionY(vertical * packet.getMotionY() / 10);
			packet.setMotionZ(horizontal * packet.getMotionZ() / 10);
//			} else {
//				event.setCancelled(true);
//			}

		}
	}
}