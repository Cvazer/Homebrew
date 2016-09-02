package by.zti.dnd.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7308514921841624923L;
	private int weight;
	private ArrayList<Item> items;
	private ArrayList<Item> equiped;

	public Inventory() {
		items = new ArrayList<Item>();
		equiped = new ArrayList<Item>();
	}

	public void equip(Item item) {
		equiped.add(item);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public int getWeight() {
		return weight;
	}

	public int updateWeight() {
		weight = 0;
		for (Item item : items) {
			weight += item.getWeight();
		}
		return weight;
	}

	public ArrayList<Item> getEquiped() {
		return equiped;
	}

	public void setEquiped(ArrayList<Item> equiped) {
		this.equiped = equiped;
	}
}
