package caterpillow.Module.Specific;

import org.lwjgl.input.Keyboard;

import caterpillow.Module.Category;
import caterpillow.Module.Module;

public class MouseSwap extends Module {
	public MouseSwap() {
		super("MouseSwap", Keyboard.KEY_K, Category.SPECIFIC);
	}

	@Override
	public void onEnable() {
		mc.gameSettings.keyBindAttack.setKeyCode(mc.gameSettings.keyBindAttack.getKeyCode() == -100 ? -99 : -100);
		mc.gameSettings.keyBindAttack.resetKeyBindingArrayAndHash();
		mc.gameSettings.keyBindUseItem.setKeyCode(mc.gameSettings.keyBindUseItem.getKeyCode() == -99 ? -100 : -99);
		mc.gameSettings.keyBindUseItem.resetKeyBindingArrayAndHash();
		mc.gameSettings.saveOptions();
		toggle();
	}

}
