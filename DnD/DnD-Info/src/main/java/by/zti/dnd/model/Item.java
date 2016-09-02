package by.zti.dnd.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import by.zti.dnd.model.CharSheet.Money;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8687002507347873305L;
	private Type type;
	private String name;
	private String description;
	private int itemWeight;
	private int count;
	private Map<CharSheet.Money, Integer> price;

	public Item(Type type, String name) {
		this.type = type;
		this.name = name;
		description = "";
		itemWeight = 1;
		count = 1;
		price = new HashMap<CharSheet.Money, Integer>();
		price.put(Money.COPPER, 0);
		price.put(Money.SILVER, 0);
		price.put(Money.GOLD, 0);
	}

	public void setPrice(int cp, int sp, int gp) {
		price.put(Money.COPPER, cp);
		price.put(Money.SILVER, sp);
		price.put(Money.GOLD, gp);
	}

	public int getCurrency(Money money) {
		return price.get(money);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public int getWeight() {
		return itemWeight * count;
	}

	public int getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(int itemWeight) {
		this.itemWeight = itemWeight;
	}

	public Map<CharSheet.Money, Integer> getPrice() {
		return price;
	}

	public void setPrice(Map<CharSheet.Money, Integer> price) {
		this.price = price;
	}

	@Override
	public String toString() {
		if (count > 1) {
			return name + " (" + count + ")";
		} else {
			return name;
		}
	}

	public enum Type {
		CONSUMABLE, WEAPON_MELE, ARMOR, AMMO, JUNK, VALUABLE, GEM, RING, WEAPON_RANGE, FOOD
	}
}
