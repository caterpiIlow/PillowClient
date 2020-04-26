package caterpillow.event.Listeners;

import java.util.ArrayList;

import caterpillow.event.Event;
import caterpillow.event.Listener;

public interface KnockbackListener extends Listener {

	public void onKnockback(KnockbackEvent event);

	public static class KnockbackEvent extends Event<KnockbackListener> {
		private double x;
		private double y;
		private double z;
		private final double defaultX;
		private final double defaultY;
		private final double defaultZ;

		public KnockbackEvent(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
			defaultX = x;
			defaultY = y;
			defaultZ = z;
		}

		@Override
		public void fire(ArrayList<KnockbackListener> listeners) {
			for (KnockbackListener listener : listeners)
				listener.onKnockback(this);
		}

		@Override
		public Class<KnockbackListener> getListenerType() {
			return KnockbackListener.class;
		}

		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public double getZ() {
			return z;
		}

		public void setZ(double z) {
			this.z = z;
		}

		public double getDefaultX() {
			return defaultX;
		}

		public double getDefaultY() {
			return defaultY;
		}

		public double getDefaultZ() {
			return defaultZ;
		}
	}
}