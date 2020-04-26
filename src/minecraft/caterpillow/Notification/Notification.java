package caterpillow.Notification;

import java.awt.Color;

import caterpillow.Client.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class Notification {
	private NotificationType type;
	private String title;
	private String messsage;
	private long start;

	private long fadedIn;
	private long fadeOut;
	private long end;

	private int lastOffset;

	public Notification(NotificationType type, String title, String messsage, int length) {
		this.type = type;
		this.title = title;
		this.messsage = messsage;

		fadedIn = 200 * length;
		fadeOut = fadedIn + 500 * length;
		end = fadeOut + fadedIn;
	}

	public void show() {
		start = System.currentTimeMillis();
	}

	public boolean isShown() {
		return getTime() <= end;
	}

	public int fadingOutProgress() {
		if (getTime() > fadeOut && end - getTime() != 0) {
			System.out.println("fading out progress: "
					+ (Math.tanh(3.0 - (getTime() - fadeOut) / (double) (end - fadeOut) * 3.0) * 40));

			return 40 - (int) (Math.tanh(3.0 - (getTime() - fadeOut) / (double) (end - fadeOut) * 3.0) * 40);
		}
		return 0;

	}

	private long getTime() {
		return System.currentTimeMillis() - start;
	}

	public void render() {
		if (!isShown()) {
			Client.instance.notificationManager.removeFromList(this);
		}

		if (Client.instance.notificationManager.getIndex(this) == 0) {
			Client.instance.notificationManager.setLastNotif(this);
		}

		double offset = 0;
		int width = 120;
		int height = 30;
		long time = getTime();

		if (time < fadedIn) {
			offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
		} else if (time > fadeOut) {
			offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
		} else {
			offset = width;
		}

		Color color = new Color(0, 0, 0, 220);
		Color color1;

		if (type == NotificationType.INFO)
			color1 = new Color(0, 26, 169);
		else if (type == NotificationType.WARNING)
			color1 = new Color(204, 193, 0);
		else {
			color1 = new Color(204, 0, 18);
			int i = Math.max(0, Math.min(255, (int) (Math.sin(time / 100.0) * 255.0 / 2 + 127.5)));
			color = new Color(i, 0, 0, 220);
		}

		int yOffset = Client.instance.notificationManager.getIndex(this) * 40
				- Client.instance.notificationManager.getLastNotif().fadingOutProgress();
		System.out.println(yOffset);

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

		Gui.drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - height - yOffset, GuiScreen.width,
				GuiScreen.height - 5 - yOffset, color.getRGB());
		Gui.drawRect(GuiScreen.width - offset, GuiScreen.height - 5 - height - yOffset, GuiScreen.width - offset + 4,
				GuiScreen.height - 5 - yOffset, color1.getRGB());

		fontRenderer.drawString(title, (int) (GuiScreen.width - offset + 8), GuiScreen.height - 2 - height - yOffset,
				-1);
		fontRenderer.drawString(messsage, (int) (GuiScreen.width - offset + 8), GuiScreen.height - 15 - yOffset, -1);
	}

}
