package caterpillow.Notification;

import java.util.concurrent.CopyOnWriteArrayList;

//create instance in Client.java for this to work
//to use do Client.instance.notificationManager.show(NotificationType.(NOTIF TYPE), "Title", "text", time (seconds);
//run Client.instance.notificationManager.notificationUpdate(); in GuiIngame.java
//u should be able to find it

public class NotificationManager {
	private static CopyOnWriteArrayList<Notification> pendingNotifications = new CopyOnWriteArrayList<Notification>();
	private static Notification currentNotification = null;

	public Notification lastNotif = null;

	public Notification getLastNotif() {
		return lastNotif;
	}

	public void setLastNotif(Notification lastNotif) {
		this.lastNotif = lastNotif;
	}

	public static void show(NotificationType type, String name, String text, int length) {
		Notification newNotification = new Notification(type, name, text, length);
		System.out.println("Added notification to queue");
		pendingNotifications.add(newNotification);
		System.out.println("Shwoing notification");
		newNotification.show();
	}

	public void removeFromList(int index) {
		pendingNotifications.remove(index);
	}

	public void removeFromList(Notification object) {
		pendingNotifications.remove(object);
	}

	public int getIndex(Notification notification) {
		return pendingNotifications.indexOf(notification);
	}

	public Notification getObject(int index) {
//		CopyOnWriteArrayList<Notification> notificationList = pendingNotifications;

		return pendingNotifications.get(index);
	}

	public static void notificationUpdate() {
		CopyOnWriteArrayList<Notification> notificationList = pendingNotifications;
		for (Notification notification : notificationList) {
			notification.render();
		}
	}

}
