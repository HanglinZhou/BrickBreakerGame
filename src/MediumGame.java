//
//This program invokes a medium-level game constructor 

import java.util.Random;

public class MediumGame extends Game{

	public MediumGame(String[] args) {
		super(args);
		this.paddle = new Paddle(150, 45);
		
		largerPaddleChance = 0.1;
		smallerPaddleChance = 0.2;
		largerBallChance = 0.2;
		smallerBallChance = 0.2;
	}

	public void readAndGenerateMap(String inFileName){
		super.readAndGenerateMap(inFileName);

		int brickNum = bricks.size();
		int yellowNum = (int) (0.2 * brickNum);
		System.out.println(yellowNum);
		int blackNum = (int) (0.1 * brickNum);

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
		StdDraw.pause(80);
	}

}
