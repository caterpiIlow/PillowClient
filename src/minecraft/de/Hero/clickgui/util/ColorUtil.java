package de.Hero.clickgui.util;

import java.awt.Color;

import caterpillow.Client.Client;

//Deine Imports

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class ColorUtil {

	public static Color rainbow(double offset, double delay) {
		double rainbowState = Math.ceil((System.currentTimeMillis() - offset * 20) / delay);
		rainbowState %= 360;
		System.out.println(Color.getHSBColor((float) (rainbowState / 360.0f), 1f, 1f));
		return Color.getHSBColor((float) (rainbowState / 360.0f), 1f, 1f);
	}

	public static Color getClickGUIColor() {
		if (Client.instance.settingsManager.getSettingByName("GUI Color").getValString()
				.equalsIgnoreCase("RGB Slider")) {
			return new Color((int) Client.instance.settingsManager.getSettingByName("GuiRed").getValDouble(),
					(int) Client.instance.settingsManager.getSettingByName("GuiGreen").getValDouble(),
					(int) Client.instance.settingsManager.getSettingByName("GuiBlue").getValDouble());
		} else if (Client.instance.settingsManager.getSettingByName("GUI Color").getValString()
				.equalsIgnoreCase("Rainbow")) {
			return rainbow(0, Client.instance.settingsManager.getSettingByID("clickGUIDelay").getValDouble());

		}
		return null;
	}

}
