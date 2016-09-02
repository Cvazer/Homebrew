package by.zti.dnd.model;

import java.io.Serializable;

public class Proficiency implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8651325914401653350L;
	private Type type;
	private String name;
	private String description;

	public Proficiency(Type type, String name) {
		this.type = type;
		this.name = name;
		this.description = "";
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public enum Type {
		LANGUAGE, WEAPON, ARMOR, TOOL
	}
}
