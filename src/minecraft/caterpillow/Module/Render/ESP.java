package caterpillow.Module.Render;

import java.awt.Color;

import javax.swing.text.html.StyleSheet;

import org.lwjgl.input.Keyboard;

import caterpillow.Client.Client;
import caterpillow.Module.Category;
import caterpillow.Module.Module;
import caterpillow.event.EventTarget;
import caterpillow.event.events.Event3D;
import caterpillow.utils.RenderUtils;
import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class ESP extends Module {

	public ESP() {
		super("ESP", Keyboard.KEY_NONE, Category.RENDER);
	}

	@Override
	public void setup() {
		Client.instance.settingsManager.rSetting(new Setting("Alpha", this, 50, 0, 255, true));
		Client.instance.settingsManager.rSetting(new Setting("Thickness", this, 3, 1, 5, true));
		Client.instance.settingsManager.rSetting(new Setting("Players", this, true, "espPlayers"));
		Client.instance.settingsManager.rSetting(new Setting("Mobs", this, true, "espMobs"));
		Client.instance.settingsManager.rSetting(new Setting("Passives", this, true, "espPassives"));
		Client.instance.settingsManager.rSetting(new Setting("Animals", this, true, "espAnimals"));
		Client.instance.settingsManager.rSetting(new Setting("Team", this, true, "espTeam"));
	}

	@EventTarget
	public void event3D(Event3D event) {
//		System.out.println("playeresp");
//		for (Object entity : mc.theWorld.loadedEntityList)
//			if (entity instanceof EntityPlayer && entity != mc.thePlayer)
//				RenderUtils.entityESPBox((Entity) entity, 0);
//	}
		if (!this.isToggled())
			return;

		for (Object theObject : mc.theWorld.loadedEntityList) {
			if (!(theObject instanceof EntityLivingBase))
				continue;
			EntityLivingBase entity = (EntityLivingBase) theObject;

			if (entity instanceof EntityPlayer) {
				if (entity != mc.thePlayer
						&& (Client.instance.settingsManager.getSettingByID("espPlayers").getValBoolean()
								|| Client.instance.settingsManager.getSettingByID("espTeam").getValBoolean()))
					player(entity);
				continue;
			}

			if (entity instanceof EntityMob
					&& Client.instance.settingsManager.getSettingByID("espMobs").getValBoolean()) {

				mob(entity);
				continue;
			}
			if (entity instanceof EntityAnimal
					&& Client.instance.settingsManager.getSettingByID("espAnimals").getValBoolean()) {

				animal(entity);
				continue;
			}

			if (Client.instance.settingsManager.getSettingByID("espPassives").getValBoolean())
				passive(entity);

		}
	}

	public void player(EntityLivingBase entity) {
		if (entity.isOnSameTeam(mc.thePlayer)
				&& Client.instance.settingsManager.getSettingByID("espTeam").getValBoolean()) {
			render(entity, "green");

		} else {
			render(entity, "red");
		}
	}

	public void mob(EntityLivingBase entity) {
		render(entity, "orange");
	}

	public void animal(EntityLivingBase entity) {
		render(entity, "green");
	}

	public void passive(EntityLivingBase entity) {
		render(entity, "aqua");
	}

	// render(red, green, blue, xPos, yPos, zPos);

	public void render(Entity entity, String color) {

		RenderUtils.entityESPBox(entity, getRGBValues(color)[0], getRGBValues(color)[1], getRGBValues(color)[2]);
	}

	public int[] getRGBValues(String color) {
		StyleSheet s = new StyleSheet();
		String rgb = color;
		Color c1 = s.stringToColor(rgb);
		int rgbColor[] = new int[3];
		rgbColor[0] = c1.getRed();
		rgbColor[1] = c1.getGreen();
		rgbColor[2] = c1.getBlue();

		return rgbColor;
	}

}