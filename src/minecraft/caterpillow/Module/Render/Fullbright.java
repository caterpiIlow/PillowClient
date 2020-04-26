package caterpillow.Module.Render;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.EventUpdate;
import de.Hero.settings.Setting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Fullbright extends Module {

	private float oldGamma;

	public Fullbright() {
		super("Fullbright", Keyboard.KEY_O, Category.RENDER);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Gamma");
		options.add("Potion");
		this.setDisplayName("Fullbright§7 Gamma");
		Client.instance.settingsManager.rSetting(new Setting("Bright Type", this, "Gamma", options));

	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (Client.instance.settingsManager.getSettingByName("Bright Type").getValString().equalsIgnoreCase("Potion")) {
			this.setDisplayName("Fullbright§7 Potion");
			mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 5200, 1));
		}
	}

	@Override
	public void onEnable() {
		super.onEnable();

		try {
			if (Client.instance.settingsManager.getSettingByName("Bright Type").getValString()
					.equalsIgnoreCase("Gamma")) {
				this.setDisplayName("Fullbright§7 Gamma");

				oldGamma = mc.gameSettings.gammaSetting;
				mc.gameSettings.gammaSetting = 100;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// §7

	@Override
	public void onDisable() {

		super.onDisable();
		if (Client.instance.settingsManager.getSettingByName("Bright Type").getValString().equalsIgnoreCase("Gamma")) {
			mc.gameSettings.gammaSetting = oldGamma;
		} else if (Client.instance.settingsManager.getSettingByName("Bright Type").getValString()
				.equalsIgnoreCase("Potion")) {
			mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
		}

	}

}
