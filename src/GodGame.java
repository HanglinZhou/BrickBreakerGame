//
//This program invokes a God-level game constructor 

import java.util.Random;

public class GodGame extends Game{
	
	

	public GodGame(String[] args) {
		super(args);
		this.paddle = new Paddle(150, 20);
		
		largerPaddleChance = 0.2;
		smallerPaddleChance = 0.3;
		largerBallChance = 0.2;
		smallerBallChance = 0.4;
		difficulty = Difficulty.god;

	}

	public void readAndGenerateMap(String inFileName){
		super.readAndGenerateMap(inFileName);

		int brickNum = bricks.size();
		int yellowNum = (int) ( 0.9 * brickNum);
		int blackNum = (int) ( 0.9 * brickNum);

		Random r = new Random();

		int yellowCount = 0;
		while (yellowCount < yellowNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setColor("yellow");
			yellowCount++;
		}

		int blackCount = 0;
		while (blackCount < blackNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setColor("black");
			blackCount++;
		}



	}

	public void show(){
		StdDraw.show();
		StdDraw.pause(2);
	}

}
