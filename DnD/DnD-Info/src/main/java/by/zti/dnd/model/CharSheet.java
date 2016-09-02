package by.zti.dnd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.zti.dnd.model.Stats.Stat;

public class CharSheet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6685028082984915020L;
	private String token;
	private String name;
	private String race;
	private String gameClass;
	private String background;
	private boolean inspiration;

	public Map<Stats.Stat, Boolean> saves;
	public Map<Param, Integer> params;
	public Inventory inv;
	public ArrayList<Proficiency> profs;
	public ArrayList<Trait> traits;
	public ArrayList<Skill> skills;
	public Stats stats;
	public Map<Money, Integer> walet;

	public CharSheet(String token) {
		this.token = token;
		params = new HashMap<Param, Integer>();
		saves = new HashMap<Stats.Stat, Boolean>();
		walet = new HashMap<Money, Integer>();
		inv = new Inventory();
		stats = new Stats();
		profs = new ArrayList<Proficiency>();
		traits = new ArrayList<Trait>();
		skills = new ArrayList<Skill>();
		inspiration = false;
		gameClass = "none";
		race = "none";
		background = "none";
		params.put(Param.PROFICIENCY, 2);
		params.put(Param.LEVEL, 1);
		params.put(Param.AC, 10 + stats.getMod(Stats.Stat.DEX));
		params.put(Param.INIT, stats.getMod(Stats.Stat.DEX));
		params.put(Param.SPEED, 30);
		params.put(Param.HIT_DICE, 10);
		params.put(Param.HIT_DICE_TOTAL, 1);
		params.put(Param.HIT_DICE_CURRENT, 1);
		params.put(Param.HP_ROLLED, 10);
		params.put(Param.HP_MAX, 10);
		params.put(Param.HP_CURRENT, 10);
		params.put(Param.XP, 0);
		saves.put(Stat.STR, false);
		saves.put(Stat.DEX, false);
		saves.put(Stat.CON, false);
		saves.put(Stat.INTEL, false);
		saves.put(Stat.WIS, false);
		saves.put(Stat.CHARM, false);
		walet.put(Money.COPPER, 0);
		walet.put(Money.SILVER, 0);
		walet.put(Money.GOLD, 0);
	}

	public enum Param {
		PROFICIENCY, LEVEL, AC, INIT, SPEED, HP_CURRENT, HP_MAX, HP_ROLLED, HIT_DICE, XP, HIT_DICE_TOTAL, HIT_DICE_CURRENT
	}

	public enum Money {
		COPPER, SILVER, GOLD
	}

	public void setSave(Stats.Stat stat, boolean value) {
		saves.put(stat, value);
	}

	public boolean getSave(Stats.Stat stat) {
		return saves.get(stat);
	}

	public void setParam(Param param, int value) {
		params.put(param, value);
	}

	public int getParam(Param param) {
		return params.get(param);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + " | " + token;
	}

	public boolean isInspiration() {
		return inspiration;
	}

	public void setInspiration(boolean inspiration) {
		this.inspiration = inspiration;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getGameClass() {
		return gameClass;
	}

	public void setGameClass(String gameClass) {
		this.gameClass = gameClass;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}
}
