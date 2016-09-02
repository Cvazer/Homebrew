package by.zti.dnd.model.map;

import java.io.Serializable;
import java.util.LinkedList;

public class GameMap implements Serializable{
	private LinkedList<MapCell> cells;

	public GameMap() {
		cells = new LinkedList<>();
	}
}
