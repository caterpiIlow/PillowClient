package caterpillow.Module.Render;

import java.awt.Color;

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

public class Tracers extends Module {

	public Tracers() {
		super("Tracers", Keyboard.KEY_NONE, Category.RENDER);
	}

	@Override
	public void setup() {
		Client.instance.settingsManager.rSetting(new Setting("Players", this, true, "tracersPlayers"));
		Client.instance.settingsManager.rSetting(new Setting("Mobs", this, true, "tracersMobs"));
		Client.instance.settingsManager.rSetting(new Setting("Passives", this, true, "tracersPassives"));
		Client.instance.settingsManager.rSetting(new Setting("Animals", this, true, "tracersAnimals"));
		Client.instance.settingsManager.rSetting(new Setting("Team", this, true, "tracersTeam"));
	}

	@EventTarget
	public void event3D(Event3D event) {

		System.out.println("onrender");

		if (!this.isToggled())
			return;

		for (Object theObject : mc.theWorld.loadedEntityList) {
			if (!(theObject instanceof EntityLivingBase))
				continue;
			EntityLivingBase entity = (EntityLivingBase) theObject;

			if (entity instanceof EntityPlayer) {
				if (entity != mc.thePlayer
						&& (Client.instance.settingsManager.getSettingByID("tracersPlayers").getValBoolean()
								|| Client.instance.settingsManager.getSettingByID("tracersTeam").getValBoolean()))
					player(entity);
				continue;
			}

			if (entity instanceof EntityMob
					&& Client.instance.settingsManager.getSettingByID("tracersMobs").getValBoolean()) {

				mob(entity);
				continue;
			}
			if (entity instanceof EntityAnimal
					&& Client.instance.settingsManager.getSettingByID("tracersAnimals").getValBoolean()) {

				animal(entity);
				continue;
			}

			if (Client.instance.settingsManager.getSettingByID("tracersPassives").getValBoolean())
				passive(entity);

		}
	}

	public void player(EntityLivingBase entity) {
		float red = 0.5F;
		float green = 0.5F;
		float blue = 1F;

		double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks
				- mc.getRenderManager().renderPosX;
		double yPos = entity.lastTickPosX + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks
				- mc.getRenderManager().renderPosY;
		double zPos = entity.lastTickPosX + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks
				- mc.getRenderManager().renderPosZ;
		// render(entity, 0);
		if (entity.isOnSameTeam(mc.thePlayer)
				&& Client.instance.settingsManager.getSettingByID("tracersTeam").getValBoolean()) {
			render(entity, Color.green);

		} else {
			render(entity, Color.red);

		}
	}

	public void mob(EntityLivingBase entity) {
		render(entity, Color.orange);
	}

	public void animal(EntityLivingBase entity) {
		render(entity, Color.green);
	}

	public void passive(EntityLivingBase entity) {
		render(entity, Color.cyan);
	}

	public void render(Entity entity, Color color) {
		RenderUtils.tracerLine(entity, color);
	}

//	public void render(float red, float green, float blue, double x, double y, double z) {
//		System.out.println("rendering");
//
//		RenderUtils.drawTracerLine(x, y, z, red, green, blue, 0.45F, 1F);
//	}

}
