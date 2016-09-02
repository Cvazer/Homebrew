package by.zti.dnd.utils;

import by.zti.dnd.model.Stats;

public class Stat {
	private String name;
	private int value;
	private int mod;
	private Stats.Stat stat;

	public Stat(String name, int value, int mod, Stats.Stat stat) {
		this.setName(name);
		this.setValue(value);
		this.setMod(mod);
		this.setStat(stat);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getMod() {
		return mod;
	}

	public void setMod(int mod) {
		this.mod = mod;
	}

	public Stats.Stat getStat() {
		return stat;
	}

	public void setStat(Stats.Stat stat) {
		this.stat = stat;
	}

}
