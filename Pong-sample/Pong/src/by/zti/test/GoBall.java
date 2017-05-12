package by.zti.test;

import org.lwjgl.opengl.Display;

public class GoBall extends GameObject{
	
	public static final int SIZE = 16;
	public static final float MAX_SPEEDX = 4f;
	public static final float MAX_SPEEDY = 8f;
	public static final float DAMPING = 0.05f;
	public float velX;
	public float velY;
	public float startX;
	public float startY;
	
	public GoBall(float x, float y){
		this.x = x;
		this.y = y;
		this.sx = SIZE;
		this.sy = SIZE;
		velX = - MAX_SPEEDX;
		velY = 0;
		startX = x;
		startY = y;
	}

	@Override
	public void update() {
		if(velY > MAX_SPEEDY){
			velY = MAX_SPEEDY;
		}else if(velY < -MAX_SPEEDY){
			velY = -MAX_SPEEDY;
		}
		x += velX;
		y += velY;
	}

	public void reversX(float center) {
		velX *= -1;
		velY += (getCenterY() - center) * DAMPING;
	}

	public void reversY(float centerY) {
		velY *= -1;
		if(velY > MAX_SPEEDY){
			velY = MAX_SPEEDY;
		}else if(velY < -MAX_SPEEDY){
			velY = -MAX_SPEEDY;
		}
	}

	public void resetPosition() {
		y = startY;
		x = startX;
		velY = 0;
		velX *= -1;
	}

}
