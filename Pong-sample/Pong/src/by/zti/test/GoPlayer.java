package by.zti.test;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class GoPlayer extends GameObject{
	public static final int SIZEX = 16;
	public static final int SIZEY = SIZEX * 7;
	public static final float SPEED = 4f;
	private GoBall ball;
	private int direction;
	
	public GoPlayer(float x, float y, GoBall ball, int direction){
		this.x = x;
		this.y = y;
		this.sx = SIZEX;
		this.sy = SIZEY;
		this.ball = ball;
		this.direction = direction;
	}

	@Override
	void update() {
		if(Phisics.checkCollisions(this, ball)){
			ball.reversX(getCenterY());
		}
	}

	@Override
	public void input() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)||Keyboard.isKeyDown(Keyboard.KEY_UP)){
			if(!(y+SIZEY+GoWall.STDSIZE>=Display.getHeight())&&direction>0){
				move(1 * direction);
			}else if(!(y<=0)&&direction<0){
				move(1 * direction);
			}
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)||Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			if(!(y-GoWall.STDSIZE<=0)&&direction>0){
				move(-1 * direction);
			}else if(!(y+SIZEY+GoWall.STDSIZE>=Display.getHeight())&&direction<0){
				move(-1 * direction);
			}
		}
		
	}

	private void move(int i) {
		y += SPEED * i;
	}

}
