package by.zti.test;

public class GoEnemy extends GameObject{
	public static final int SIZEX = 16;
	public static final int SIZEY = SIZEX * 7;
	public static final float MAX_SPEEDY = 4f;
	public static final float DAMPING = 0.05f;
	private GoBall ball;

	public GoEnemy(float x, float y, GoBall ball){
		this.x = x;
		this.y = y;
		this.sx = SIZEX;
		this.sy = SIZEY;
		this.ball = ball;
	}
	
	@Override
	void update() {
		if(Phisics.checkCollisions(this, ball)){
			ball.reversX(getCenterY());
		}

	float speed = (ball.getCenterY() - getCenterY()) * DAMPING;
	
	if(speed > MAX_SPEEDY){
		speed = MAX_SPEEDY;
	}else if(speed < -MAX_SPEEDY){
		speed = -MAX_SPEEDY;
	}
	
	y += speed;
		
	}

}
