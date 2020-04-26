package caterpillow.event.events;

import java.util.ArrayList;

import caterpillow.event.Event;
import net.minecraft.network.Packet;

public class EventReceivePacket extends Event {
	private Packet packet;

	public EventReceivePacket(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	@Override
	public void fire(ArrayList listeners) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class getListenerType() {
		// TODO Auto-generated method stub
		return null;
	}
}