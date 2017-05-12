package by.zti.test;

public class GoWall extends GameObject{
	private GoBall ball;
	public static final int STDSIZE = 16;
	
	public GoWall(float x, float y, float sy, float sx, GoBall ball){
		this.ball = ball;
		this.x = x;
		this.y = y;
		this.sx = sx;
		this.sy = sy;
	}

	@Override
	void update() {
		if(Phisics.checkCollisions(this, ball)){
			ball.reversY(getCenterY());
		}
	}

}
