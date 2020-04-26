package caterpillow.event.events;

import java.util.ArrayList;

import caterpillow.event.Event;

public class EventVelocity extends Event {
	private double x;

	private double y;

	private double z;

	public void call(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		super.call();
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return this.z;
	}

	public void setZ(double z) {
		this.z = z;
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
