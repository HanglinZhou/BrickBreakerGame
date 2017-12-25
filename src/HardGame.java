//
//This program invokes a hard-level game constructor 

import java.util.Random;

public class HardGame extends Game{

	public HardGame(String[] args) {
		super(args);
		this.paddle = new Paddle(150, 29);
		
		largerPaddleChance = 0.2;
		smallerPaddleChance = 0.3;
		largerBallChance = 0.2;
		smallerBallChance = 0.3;
		
		difficulty = Difficulty.hard;

	}

	public void readAndGenerateMap(String inFileName){
		super.readAndGenerateMap(inFileName);

		int brickNum = bricks.size();
		int yellowNum = (int) (0.8 * brickNum);
		System.out.println(yellowNum);
		int blackNum = (int) (0.5 * brickNum);

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
		StdDraw.pause(20);
	}

}
