package by.zti.test;


public abstract class GameObject {
	protected float x;
	protected float y;
	protected float sx;
	protected float sy;
	
	abstract void update();
	public void render(){
		Draw.Rect(x, y, sx, sy);
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getSx(){
		return sx;
	}
	
	public float getSy(){
		return sy;
	}
	
	public float getCenterY(){
		return (y + sy/2);
	}
	
	public void input(){
		
	}
}
