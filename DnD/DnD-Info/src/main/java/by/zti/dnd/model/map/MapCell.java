package by.zti.dnd.model.map;

public class MapCell {
	private int x, y;

	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;

		MapCell mapCell = (MapCell) object;

        return x == mapCell.x && y == mapCell.y;
	}
}
