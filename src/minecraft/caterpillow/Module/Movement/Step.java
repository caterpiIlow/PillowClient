package caterpillow.Module.Movement;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;
import de.Hero.settings.Setting;

public class Step extends Module {
	public Step() {
		super("Step", Keyboard.KEY_K, Category.MISC);
	}

	@Override
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.stepHeight = (float) 0.5;
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.setDisplayName("Step§7 " + Client.instance.settingsManager.getSettingByName("Step Height").getValDouble());
		mc.thePlayer.stepHeight = (float) Client.instance.settingsManager.getSettingByName("Step Height")
				.getValDouble();
	}

	@Override
	public void setup() {
		Client.instance.settingsManager.rSetting(new Setting("Step Height", this, 1, 0, 3, true, 0.5));

	}

}
