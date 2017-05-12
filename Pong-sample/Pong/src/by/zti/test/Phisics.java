package by.zti.test;

import java.awt.Rectangle;

public class Phisics {
	
	public static boolean checkCollisions(GameObject go1, GameObject go2){
		Rectangle r1 = new Rectangle((int)go1.getX(), (int)go1.getY(), (int)go1.getSx(), (int)go1.getSy());
		Rectangle r2 = new Rectangle((int)go2.getX(), (int)go2.getY(), (int)go2.getSx(), (int)go2.getSy());
		
		return r1.intersects(r2);
	}
	
}
