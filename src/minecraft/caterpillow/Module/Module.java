package caterpillow.Module;

import caterpillow.Client.Client;
import caterpillow.Module.HUD.ScreenPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.EntityLivingBase;

public class Module {

	protected Minecraft mc = Minecraft.getMinecraft();
	protected FontRenderer fr = mc.fontRendererObj;

	private String name, displayName;
	private int key;
	private Category category;
	private boolean toggled;
	public ScreenPosition pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
	public int height, width;

	public Module(String name, int key, Category category) {
		this.name = name;
		this.key = key;
		this.category = category;
		this.pos.setRelative(0.5, 0.5);
//		System.out.println(category);
		for (String x : Client.instance.moduleManager.defaults)
			if (name == x) {
				toggled = true;
				onToggle();
				onEnable();
				break;
			} else {
				toggled = false;
			}
		setDisplayName(name);

		setup();

	}

	public Module(String name, int key, Category category, ScreenPosition pos, int width, int height) {
		this.name = name;
		this.key = key;
		this.category = category;
		this.pos = pos;
		this.width = width;
		this.height = height;
		System.out.println(category);
		for (String x : Client.instance.moduleManager.defaults)
			if (name == x) {
				toggled = true;
				onToggle();
				onEnable();
				break;
			} else {
				toggled = false;
			}
		setDisplayName(name);

		setup();

	}

//	public Module(String name, int key, Category category, ScreenPosition pos, int height, int width) {
//		this.name = name;
//		this.key = key;
//		this.category = category;
//		if (pos == null) {
//			this.pos.setRelative(0.5, 0.5);
//		} else {
//			this.pos = pos;
//		}
//		this.height = height;
//		this.width = width;
//
//		for (String x : Client.instance.moduleManager.defaults)
//			if (name == x) {
//				toggled = true;
//				onToggle();
//				onEnable();
//				break;
//			} else {
//				toggled = false;
//			}
//		setDisplayName(name);
//
//		setup();
//
//	}

	public int getHeight() {
		return height;
	}

	public boolean settingBoolean(String name) {
		return Client.instance.settingsManager.getSettingByName(name).getValBoolean();
	}

	public boolean settingBooleanID(String ID) {
		return Client.instance.settingsManager.getSettingByID(ID).getValBoolean();
	}

	public double settingDouble(String name) {
		return Client.instance.settingsManager.getSettingByName(name).getValDouble();
	}

	public double settingDoubleID(String ID) {
		return Client.instance.settingsManager.getSettingByID(ID).getValDouble();
	}

	public String setttingString(String name) {
		return Client.instance.settingsManager.getSettingByName(name).getValString();
	}

	public String setttingStringID(String ID) {
		return Client.instance.settingsManager.getSettingByID(ID).getValString();
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public ScreenPosition getPos() {
		return this.pos;
	}

	public void setPos(ScreenPosition pos) {
		this.pos = pos;
	}

	public void onEnable() {
		Client.instance.eventManager.register(this);
	}

	public void onDisable() {
		Client.instance.eventManager.unregister(this);

	}

	public void onToggle() {
	}

	public void toggle() {
		toggled = !toggled;
		onToggle();
		if (toggled) {
			onEnable();
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 1f, 1f);
		} else {
			onDisable();
			Minecraft.getMinecraft().thePlayer.playSound("random.click", 1f, 0.5f);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	public String getDisplayName() {
		String color = settingEquals("Text Color", "Category") ? getColor() : "";
		return color + displayName;
	}

	private boolean settingEquals(String setting, String name) {
		return Client.instance.settingsManager.getSettingByName(setting).getValString().equalsIgnoreCase(name);
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getColor() {
		switch (this.category) {
//		case COMBAT:
//			return "§c";
//		case MOVEMENT:
//			return "§e";
		case PLAYER:
			return "§a";
		case RENDER:
			return "§b";
		default:
			return "§7";
		}
	}

	public int getDecimalColor() {
		switch (this.category) {
//		case COMBAT:
//			return 16733525;
//		case MOVEMENT:
//			return 16777045;
		case PLAYER:
			return 5635925;
		case RENDER:
			return 5636095;
		default:
			return 11184810;
		}
	}

	public int getHexColor() {
		if (settingEquals("Text Color", "None"))
			return 0xFFFFFFFF;
		switch (this.category) {
//		case COMBAT:
//			return 0xFFFF5555;
//		case MOVEMENT:
//			return 0xFFFFFF55;
		case PLAYER:
			return 0xFF55FF55;
		case RENDER:
			return 0xFF55FFFF;
		default:
			return 0xFFAAAAAA;
		}
	}

	public void setup() {
		// TODO Auto-generated method stub

	}

	public EntityLivingBase getClosest(double range) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isBlockUnder() {
		// TODO Auto-generated method stub
		return false;
	}

	public void renderDummy(ScreenPosition pos) {

	}

}