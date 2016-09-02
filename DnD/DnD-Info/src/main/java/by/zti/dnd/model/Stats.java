package by.zti.dnd.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Stats implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8538943363462597490L;
	private Map<Stat, Integer> values;

	public Stats() {
		this(10, 10, 10, 10, 10, 10);
	}

	public Stats(int str, int dex, int con, int intel, int wis, int charm) {
		values = new HashMap<Stat, Integer>();
		setAllStats(str, dex, con, intel, wis, charm);
	}

	public void setAllStats(int str, int dex, int con, int intel, int wis, int charm) {
		values.put(Stat.STR, str);
		values.put(Stat.DEX, dex);
		values.put(Stat.CON, con);
		values.put(Stat.INTEL, intel);
		values.put(Stat.WIS, wis);
		values.put(Stat.CHARM, charm);
	}

	public void setStat(Stat stat, int value) {
		values.put(stat, value);
	}

	public int getStat(Stat stat) {
		return values.get(stat);
	}

	public int getMod(Stat stat) {
		return (values.get(stat) - 10) / 2;
	}

	public enum Stat {
		STR, DEX, CON, INTEL, WIS, CHARM
	}
}
