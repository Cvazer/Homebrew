package by.zti.dnd.model;

import java.io.Serializable;

import by.zti.dnd.model.Stats.Stat;

public class Skill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1151681143681683190L;
	private String name;
	private Stats.Stat scaling;
	private boolean proficient;
	private String description;

	public Skill(String name, Stat scaling) {
		this(name, scaling, false);
	}

	public Skill(String name, Stat scaling, boolean proficient) {
		super();
		this.name = name;
		this.scaling = scaling;
		this.proficient = proficient;
		setDescription("");
	}

	public int getMod(CharSheet sheet) {
		int result = sheet.stats.getMod(scaling);
		if (proficient) {
			result += sheet.getParam(CharSheet.Param.PROFICIENCY);
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stats.Stat getScaling() {
		return scaling;
	}

	public void setScaling(Stats.Stat scaling) {
		this.scaling = scaling;
	}

	public boolean isProficient() {
		return proficient;
	}

	public void setProficient(boolean proficient) {
		this.proficient = proficient;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
