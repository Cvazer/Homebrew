package by.zti.dnd.utils;

import by.zti.dnd.model.Stats;

public class SaveThrow {
	private String name;
	private int mod;
	private boolean prof;
	private Stats.Stat stat;

	public SaveThrow(String name, int mod, boolean prof, Stats.Stat stat) {
		this.name = name;
		this.mod = mod;
		this.prof = prof;
		this.stat = stat;
	}

	public boolean isProf() {
		return prof;
	}

	public void setProf(boolean prof) {
		this.prof = prof;
	}

	public int getMod() {
		return mod;
	}

	public void setMod(int mod) {
		this.mod = mod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stats.Stat getStat() {
		return stat;
	}

	public void setStat(Stats.Stat stat) {
		this.stat = stat;
	}
}
