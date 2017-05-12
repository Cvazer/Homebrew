package by.zti.test;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

public class Game {
	
	private ArrayList<GameObject> objects;
	private int playerScore;
	private int enemyScore;
	private GoBall ball;
	
	public Game(){
		objects = new ArrayList<GameObject>();
		playerScore = 0;
		enemyScore = 0;
		ball = new GoBall(Display.getWidth()/2 - GoBall.SIZE/2, Display.getHeight()/2 - GoBall.SIZE/2);
		GoPlayer player = new GoPlayer(10, Display.getHeight()/2 - GoPlayer.SIZEY/2, ball, 1);
	//	GoPlayer player2 = new GoPlayer(Display.getWidth() - GoPlayer.SIZEX - 10, Display.getHeight()/2 - GoPlayer.SIZEY/2, ball, -1);
		GoEnemy enemy = new GoEnemy(Display.getWidth() - GoPlayer.SIZEX - 10, Display.getHeight()/2 - GoPlayer.SIZEY/2, ball);
		GoWall wall1 = new GoWall(0, 0, GoWall.STDSIZE, Display.getWidth(), ball);
		GoWall wall2 = new GoWall(0, Display.getHeight() - GoWall.STDSIZE, GoWall.STDSIZE, Display.getWidth(), ball);
		objects.add(ball);
		objects.add(player);
		objects.add(enemy);
		objects.add(wall1);
		objects.add(wall2);
	}

	public void getInput() {
		for(GameObject go: objects){
			go.input();
		}
	}

	public void update() {
		for(GameObject go: objects){
			go.update();
		}
		if(ball.getX()> Display.getWidth()){
			playerScore++;
			ball.resetPosition();
		}
		if(ball.getX()<0){
			enemyScore++;
			ball.resetPosition();
		}
	}

	public void render() {
		for(GameObject go: objects){
			go.render();
		}
		Display.setTitle("Player Score: "+playerScore+" Enemy Score: "+enemyScore);
	}
	
}
