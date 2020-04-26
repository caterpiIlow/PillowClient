package de.Hero.settings;

import java.util.ArrayList;

import caterpillow.Module.Module;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class Setting {

	private String name;
	private Module parent;
	private String mode;

	private String sval;
	private ArrayList<String> options;

	private boolean bval;

	private double dval;
	private double min;
	private double max;
	private double nearest = 0;
	private boolean onlyint = false;
	private String ID = "asmdaskdlasd";

	public Setting(String name, Module parent, String sval, ArrayList<String> options) {
		this.name = name;
		this.parent = parent;
		this.sval = sval;
		this.options = options;
		this.mode = "Combo";
	}

	public Setting(String name, Module parent, String sval, ArrayList<String> options, String ID) {
		this.name = name;
		this.ID = ID;
		this.parent = parent;
		this.sval = sval;
		this.options = options;
		this.mode = "Combo";
	}

	public Setting(String name, Module parent, boolean bval) {
		this.name = name;
		this.parent = parent;
		this.bval = bval;
		this.mode = "Check";
	}

	public Setting(String name, Module parent, boolean bval, String ID) {
		this.name = name;
		this.ID = ID;
		this.parent = parent;
		this.bval = bval;
		this.mode = "Check";
	}

	public Setting(String name, Module parent, double dval, double min, double max, boolean onlyint) {
		this.name = name;
		this.parent = parent;
		this.dval = dval;
		this.min = min;
		this.max = max;
		this.onlyint = onlyint;
		this.mode = "Slider";
	}

	public Setting(String name, Module parent, double dval, double min, double max, String ID, boolean onlyint) {
		this.name = name;
		this.ID = ID;
		this.parent = parent;
		this.dval = dval;
		this.min = min;
		this.max = max;
		this.onlyint = onlyint;
		this.mode = "Slider";
	}

	public Setting(String name, Module parent, double dval, double min, double max, boolean onlyint, double nearest) {
		this.name = name;
		this.parent = parent;
		this.dval = dval;
		this.min = min;
		this.max = max;
		this.onlyint = onlyint;
		this.nearest = nearest;
		this.mode = "Slider";
	}

	public Setting(String name, Module parent, double dval, double min, double max, String ID, boolean onlyint,
			double nearest) {
		this.name = name;
		this.ID = ID;
		this.parent = parent;
		this.dval = dval;
		this.min = min;
		this.max = max;
		this.onlyint = onlyint;
		this.nearest = nearest;
		this.mode = "Slider";
	}

	public String getName() {
		return name;
	}

	public String getID() {
		return ID;
	}

	public Module getParentMod() {
		return parent;
	}

	public String getValString() {
		return this.sval;
	}

	public void setValString(String in) {
		this.sval = in;
	}

	public ArrayList<String> getOptions() {
		return this.options;
	}

	public boolean getValBoolean() {
		return this.bval;
	}

	public void setValBoolean(boolean in) {
		this.bval = in;
	}

	public double getValDouble() {
		if (this.onlyint) {
			if (this.nearest != 0) {
				this.dval = (double) (Math.round(this.dval * (1 / this.nearest))) / (1 / this.nearest);
			}

			else {
				this.dval = (int) dval;
			}

		}
		return this.dval;
	}

	public void setValDouble(double in) {
		this.dval = in;
	}

	public double getMin() {
		return this.min;
	}

	public double getMax() {
		return this.max;
	}

	public boolean isCombo() {
		return this.mode.equalsIgnoreCase("Combo") ? true : false;
	}

	public boolean isCheck() {
		return this.mode.equalsIgnoreCase("Check") ? true : false;
	}

	public boolean isSlider() {
		return this.mode.equalsIgnoreCase("Slider") ? true : false;
	}

	public boolean onlyInt() {
		return this.onlyint;
	}
}
