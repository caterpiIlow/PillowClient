package caterpillow.Module.Render;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import de.Hero.settings.Setting;

public class ClickGUI extends Module {
	public ClickGUI() {
		super("GUI", Keyboard.KEY_RSHIFT, Category.RENDER);

	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("New");
		options.add("JellyLike");
		Client.instance.settingsManager.rSetting(new Setting("Design", this, "New", options));
		ArrayList<String> options2 = new ArrayList<>();
		options2.add("RGB Slider");
		options2.add("Rainbow");
		Client.instance.settingsManager.rSetting(new Setting("GUI Color", this, "RGB Slider", options2));
		Client.instance.settingsManager.rSetting(new Setting("Color delay", this, 20, 0, 100, "clickGUIDelay", true));
		Client.instance.settingsManager.rSetting(new Setting("Sound", this, true));
		Client.instance.settingsManager.rSetting(new Setting("GuiRed", this, 110, 0, 255, true));
		Client.instance.settingsManager.rSetting(new Setting("GuiGreen", this, 250, 0, 255, true));
		Client.instance.settingsManager.rSetting(new Setting("GuiBlue", this, 241, 0, 255, true));

	}

	@Override
	public void onEnable() {
		super.onEnable();

		mc.displayGuiScreen(Client.instance.clickGui);

//		toggle();
	}

}
