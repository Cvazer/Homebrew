package by.zti.dnd.model;

import java.io.Serializable;

public class Trait implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5200292997431297348L;
	private Type type;
	private String name;
	private String description;

	public Trait(Type type, String name) {
		this.type = type;
		this.name = name;
		description = "";
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

	@Override
	public String toString() {
		return name;
	}

	public enum Type {
		CLASS, RACE, FEAT
	}
}
