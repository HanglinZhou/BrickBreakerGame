//
//This program invokes a impossibly-easy-level game constructor 
//This is the parent game constructor
//The main objects here are: brick, paddle, and ball

import java.util.*;
import java.awt.Font;
import java.io.*;

/**
 * Main application object.
 * Contains the core loop: process input, update, clear, run ball, paddle, brick.
 */


public class Game {
	private final String[] args;
	private static final int mapArgPosition = 2; //index in commandline arg

	public enum GameState {Title, Start, Playing, Quit, Over, Success}; 
	public enum Difficulty {easy, medium, master, hard, god};

	GameState gameState; //Store the state of the game

	char key;

	protected Ball ball;
	protected Paddle paddle;
	protected ArrayList<Brick> bricks;
	protected Difficulty difficulty;
	String userName;
	String gender;
	double score;
	int numHitOnPaddle;
	int ballSpeed;
	
	String specialEffect;
	
	//count special effect number
	int hardBrickTotal;
	int brittleBrickTotal;
	int halfScoreTotal;
	int doubleScoreTotal;
	int largerPaddleTotal;
	int smallerPaddleTotal;
	int largerBallTotal;
	int smallerBallTotal;
	
	//chance of getting special effect
	protected static double harderBrickChance = 0.5;
	protected static double brittleBrickChance = 0.5;
	protected static double halfScoreChance = 0.1;
	protected static double doubleScoreChance = 0.1;
	protected static double largerPaddleChance = 0.2;
	protected static double smallerPaddleChance = 0.1;
	protected static double largerBallChance = 0.3;
	protected static double smallerBallChance = 0.1;

	public Game(String args[]) {
		this.args = args;
		gameState = GameState.Title;

		ball = new Ball();
		paddle = new Paddle(150, 60);
		bricks = new ArrayList<Brick>();
		userName = args[0];
		gender = args[1];
		score = 0.0;
		numHitOnPaddle = 1;
		specialEffect = new String("");
		difficulty = Difficulty.medium;
		

		hardBrickTotal = 0;
		brittleBrickTotal = 0;
		halfScoreTotal = 0;
		doubleScoreTotal = 0;
		largerPaddleTotal =0 ;
		smallerPaddleTotal = 0;
		largerBallTotal = 0;
		smallerBallTotal = 0;


		readAndGenerateMap(args[mapArgPosition]);
	}

	/**
	 * Reads from an input file, which is a map of bricks.
	 * the first line is the number of bricks/row in this map. 
	 * If this number is 5, 5 bricks will evenly split the top of 
	 * the screen.
	 * 
	 * From the 2nd line onward, each integer represents if a brick is 
	 * present at that spot. 0 means this location has no brick. 1 means 
	 * this location has a brick.
	 * @param inFileName the name of the map file (should be from cmd line)
	 */

	public void readAndGenerateMap(String inFileName){

		try {
			List<Double> xList = new ArrayList<Double>();
			List<Double> yList = new ArrayList<Double>();

			File fin = new File (inFileName);
			Scanner sin = new Scanner (fin);

			int column = sin.nextInt();
			int row = sin.nextInt();


			while (sin.hasNext()){
				for (int i = 0; i < row; i++){
					for (int j = 0; j < column; j++){
						int mapNum = sin.nextInt();
						if (mapNum == 1){
							xList.add(computeXCoordinate(column, j + 1));
							yList.add(computeYCoordinate(row, i + 1));

						}
					}
				}
			}


			for(int i = 0; i < xList.size(); i++){
				Brick brick = new Brick(xList.get(i), yList.get(i), row, column, "pink", "nothing");
				bricks.add(brick);

			} 

			initializeBrickSpecialEffect();

			sin.close();


			return;
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}

	//helper method to add special effects to brick
	private void initializeBrickSpecialEffect() {

		Random r = new Random();

		int brickNum = bricks.size();
		int longerPNum = (int) ( largerPaddleChance* brickNum);
		int shorterPNum = (int) ( smallerPaddleChance * brickNum);
		
		int largerBNum = (int) ( largerBallChance * brickNum);
		int smallerBNum = (int) ( smallerBallChance * brickNum);
		
		int doubleScoreNum = (int) ( doubleScoreChance * brickNum);
		int halfScoreNum = (int) ( halfScoreChance * brickNum);
		
		int harderBrickNum = (int) ( harderBrickChance * brickNum);
		int brittleBrickNum = (int) ( brittleBrickChance * brickNum);


		int lPCount = 0;
		int sPCount = 0;

		while (lPCount < longerPNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setSpEffect("longPaddle");
			lPCount++;
		}

		while (sPCount < shorterPNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setSpEffect("shortPaddle");
			sPCount++;
		}

		int lBCount = 0;
		int sBCount = 0;

		while (lBCount < largerBNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setSpEffect("largeBall");
			lBCount++;
		}

		while (sBCount < smallerBNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setSpEffect("smallBall");
			sBCount++;
		}
		
		int dSCount = 0;
		int hSCount = 0;

		while (dSCount < doubleScoreNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setSpEffect("doubleScore");
			dSCount++;
		}

		while (hSCount < halfScoreNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setSpEffect("halfScore");
			hSCount++;
		}
		
		int hBCount = 0;
		int bBCount = 0;

		while (hBCount < harderBrickNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setSpEffect("hardBrick");
			hBCount++;
		}

		while (bBCount < brittleBrickNum){
			int i = r.nextInt(bricks.size());
			bricks.get(i).setSpEffect("brittleBrick");
			bBCount++;
		}
		
		

	}

	//	//compute x coordinate of brick's central point
	private double computeXCoordinate(int col, int presentCol){

		final double TOTALLENGTH = 300;
		double aveLength = TOTALLENGTH / col;
		double xCoordinate = presentCol * aveLength - aveLength / 2;

		return xCoordinate;
	}

	private double computeYCoordinate(int row, int presentRow){

		final double TOTALWIDTH = 124.5;
		double aveLength = TOTALWIDTH / row;
		double yCoordinate = presentRow * aveLength - aveLength / 2 + 124.5;

		return yCoordinate;

	}


	public void run() {
		initialize();

		while (gameState != GameState.Quit || gameState != GameState.Over){

			processInput();
			update();
			clear();
			draw();
			show();

		}
	}


	//click p/P to start game
	//click q/Q to quit game
	public void processInput() {

		if(StdDraw.hasNextKeyTyped()) {
			key = StdDraw.nextKeyTyped();
			switch(gameState) {
			case Title:
				if(key == 's' || key =='S'){
					gameState = GameState.Start;
				}
				break;

			case Start:
				if (key == 'p' || key == 'P'){
					gameState = GameState.Playing;
				}

				else if (key == 'q' || key == 'Q'){
					gameState = GameState.Quit;
				}
				break;

			case Playing:  
				if (key == 'q' || key == 'Q'){
					gameState = GameState.Quit;
				}


				else if (key == 'a' || key == 'A') {
					paddle.x -= 15;
				}

				else if(key == 'd' || key == 'D'){
					paddle.x += 15;
				}

				if (ball.getRY() <= 2.5 && outOfPaddleBound()){
					gameState = GameState.Over;
				}
				break;
			case Over: 
				if (key == 'y' || key == 'Y'){
					GameMenu menu = new GameMenu(args);

				}
				else if (key == 'q' || key == 'Q') {
					gameState = GameState.Quit;
				}

				break;
			case Quit:
				writeScoreSheet();

				System.exit(0);
				break;

			default:
				break;
			}
		}
	}

	private void writeScoreSheet() {

		String outFileName = "score.txt";

		try {
			PrintWriter pout = new PrintWriter(outFileName);
			pout.println("Name: " + userName);
			
			switch (difficulty) {
			case easy: 
				pout.println("Difficulty level is : impossibly easy ");
				break;

			case medium: 
				pout.println("Difficulty level is : not too rough️ 🐒");
				break;
				
			case master: 
				pout.println("Difficulty level is : master");
				break;
				
			case hard: 
				pout.println("Difficulty level is : no seriously, give up now");
				break;
				
			case god: 
				pout.println("Difficulty level is : God's level 🦄️");
				break;
			}


			pout.println("Your score is : " + score);
			pout.println("You hit paddle " + (numHitOnPaddle -1) + " times");
			pout.println("All you special effects you got:");
			pout.println("longer paddle * " + largerPaddleTotal);
			pout.println("shorter paddle * " + smallerPaddleTotal);
			pout.println("larger ball * " + largerBallTotal);
			pout.println("smaller ball * " + smallerBallTotal);
			pout.println("double score * " + doubleScoreTotal);
			pout.println("half score * " + halfScoreTotal);
			pout.println("hard brick * " + hardBrickTotal);
			pout.println("brittle brick * " + brittleBrickTotal);
			
			pout.close();



		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void initialize() {

		StdDraw.setCanvasSize(700, 700);
		StdDraw.setXscale(0, 300);
		StdDraw.setYscale(0, 300);

		StdDraw.enableDoubleBuffering();
	}


	public void clear() {

		StdDraw.clear();
	}

	public void drawTitle(){
		StdDraw.picture(100, 200, "minion2.jpg", 225, 160);
		StdDraw.picture(150, 50, "minion1.jpg", 80, 100);
		StdDraw.picture(250, 70, "minion3.jpg", 80, 100);

		Font font = new Font("SansSerif", Font.BOLD, 25);
		StdDraw.setFont(font);

		StdDraw.text(150, 130, "Press 'S' or 's' to start.");
		StdDraw.text(150, 110, "'a' or 'A' move left");
		StdDraw.text(150, 100, "'d' or 'D' move right");


	}

	public void drawStart(){
		ball.draw();
		paddle.draw();
		for(int i = 0; i < bricks.size(); i++){
			bricks.get(i).draw();
		}

		Font font = new Font("SansSerif", Font.BOLD, 25);
		StdDraw.setFont(font);

		StdDraw.setPenColor(StdDraw.PINK);
		StdDraw.text(150, 30, "Press 'P' to play. ");

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(150, 250, 150, 1);

		drawName();
		drawProfilePhoto();

	}

	//draw userName
	private void drawName(){
		Font font = new Font("SansSerif", Font.ITALIC, 25);
		StdDraw.setFont(font);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(40, 275, userName);

	}

	//draw user profile photo  according to gender 
	private void drawProfilePhoto(){
		if (gender.equals("female")){
			StdDraw.picture(10, 275, "minion7.jpg", 15, 22);
		}
		else if (gender.equals("male")){
			StdDraw.picture(10, 275, "minion8.jpg", 18, 24);
		}
	}

	private void updateScore(){
		Font font = new Font("SansSerif", Font.CENTER_BASELINE, 20);
		StdDraw.setFont(font);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(100, 275, "Current score: ");
		Font font2 = new Font("SansSerif", Font.CENTER_BASELINE, 23);
		StdDraw.setFont(font2);
		StdDraw.text(100, 260, " " + score);


	}

	public void draw() {
		//Start with a clean slate

		//Draw the game based upon the screen it is on
		switch(gameState) {
		case Title: 
			drawTitle();
			break;
		case Start:
			drawStart();
			break;

		case Playing: 

			drawName();
			drawProfilePhoto();
			updateScore();

			StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
			StdDraw.filledRectangle(150, 250, 150, 1);
			StdDraw.text(260, 275, specialEffect);
			paddle.draw();

			for(int i = 0; i < bricks.size(); i++){
				bricks.get(i).draw();
			}
			ball.draw();


			break;
		case Over: 
			StdDraw.picture(150, 110, "minion4.jpg", 90, 80);
			StdDraw.setPenColor(StdDraw.BLACK);
			Font font = new Font("SansSerif", Font.BOLD, 25);
			StdDraw.setFont(font);
			StdDraw.text(150, 180, "game over");
			StdDraw.text(150, 160, "press 'y' or 'Y' to start over");

			break;

		case Quit:

			StdDraw.picture(150, 110, "minion5.jpg", 300, 230);
			StdDraw.setPenColor(StdDraw.BLACK);
			Font font2 = new Font("SansSerif", Font.BOLD, 25);
			StdDraw.setFont(font2);
			StdDraw.text(150, 180, "coward!");
			StdDraw.text(150, 160, "press any key to close this window");

			break;

		case Success:
			StdDraw.picture(150, 110, "minion6.jpg", 300, 230);
			StdDraw.setPenColor(StdDraw.BLACK);
			Font font3 = new Font("SansSerif", Font.BOLD, 25);
			StdDraw.setFont(font3);
			StdDraw.text(150, 160, "YOU WIN!! Wannan try again?");


			break;

		default:
			break;
		}
	}


	public void update(){
		if (gameState == GameState.Playing){
			ball.move();

			if (ball.getRY() <= 5.5 && ! outOfPaddleBound()){
				//							ball.setVX(-ball.getVX());
				ball.setVY(-ball.getVY());
				//				ball.setRX(ball.getRX() +ball.getVX());
				//				ball.setRY(ball.getRY() +ball.getVY());
				numHitOnPaddle += 1;

			}
			else if(ball.getRY() < 2.5){
				gameState = GameState.Over;
			}


			for(int i = 0; i < bricks.size(); i++){


				if( hitBrick(bricks.get(i)) ) {

					ball.setVY(-ball.getVY());
					
					if ( bricks.get(i).getSpEffect().equals("longPaddle") ){
						paddle.setPLength(paddle.getPLength() * 1.5);
						
						largerPaddleTotal++;
						specialEffect = new String("longer paddle!");
						

					}
					else if ( bricks.get(i).getSpEffect().equals("shortPaddle") ){
						paddle.setPLength(paddle.getPLength() / 1.5);
						
						smallerPaddleTotal++;
						specialEffect = new String("shorter paddle!");

						
					}

					if ( bricks.get(i).getSpEffect().equals("largeBall") ){
						ball.setRadius(ball.getRadius() * 1.2);
						largerBallTotal++;
						specialEffect = new String("larger ball!");


					}
					else if ( bricks.get(i).getSpEffect().equals("smallBall") ){
						ball.setRadius(ball.getRadius() / 1.2);
						smallerBallTotal++;
						specialEffect = new String("smaller ball!");

						
					}
					else if ( bricks.get(i).getSpEffect().equals("doubleScore") ){
						score *=2;
						doubleScoreTotal++;
						specialEffect = new String("double score!");
						
					}
					else if ( bricks.get(i).getSpEffect().equals("halfScore") ){
						score /=2;
						halfScoreTotal++;
						specialEffect = new String("half of score!");	
					}
					else if ( bricks.get(i).getSpEffect().equals("hardBrick") ){
						
						
						if( bricks.get(i).getColor().equals("pink") )
							bricks.get(i).setColor("black");
						
						hardBrickTotal++;
						specialEffect = new String("hard brick!");
					}
					else if ( bricks.get(i).getSpEffect().equals("brittleBrick") ){
						if ( bricks.get(i).getColor().equals("black") )
							bricks.get(i).setColor("pink");
						
						brittleBrickTotal++;
						specialEffect = new String("brittle brick!");
					}
					
					//					
					if ( bricks.get(i).getColor().equals("pink") ){
						score += numHitOnPaddle * 15;
						bricks.remove(i);
					}
					else if ( bricks.get(i).getColor().equals("yellow") ){
						bricks.get(i).setColor("pink");
						score += numHitOnPaddle * 22;

					}
					else if ( bricks.get(i).getColor().equals("black") ){
						bricks.get(i).setColor("yellow");
						score += numHitOnPaddle * 42;

					}


					return;

				}
			}
		}
	}

	public void show() {
		StdDraw.show();
		StdDraw.pause(100);
	}

	public boolean outOfPaddleBound(){

		if (ball.getRX() <= paddle.getPX() + paddle.getPLength() / 2  
				&& ball.getRX() >= paddle.getPX() - paddle.getPLength() / 2 ) {
			return false;
		}

		return true;

	}

	/**
	 * A helper method that tells if a brick has been hit
	 * @param brick
	 * @return
	 */
	private boolean hitBrick(Brick brick){

		if(ball.topOfBall() <= brick.yUpperRange()
				&& ball.topOfBall() >= brick.yLowerRange() 
				&& ball.getRX() >= brick.xLowerRange()
				&& ball.getRX() <= brick.xUpperRange()){

			return true;
		}

		else if(ball.bottomOfBall() >= brick.yLowerRange() 
				&& ball.bottomOfBall() <= brick.yUpperRange()
				&& ball.getRX() >= brick.xLowerRange()
				&& ball.getRX() <= brick.xUpperRange()){

			return true;
		}


		else if(ball.leftOfBall() <= brick.xUpperRange() 
				&& ball.leftOfBall() >= brick.xLowerRange() 
				&& ball.getRY() >= brick.yLowerRange()
				&& ball.getRY() <= brick.yUpperRange()){

			return true;
		}

		else if(ball.rightOfBall() >= brick.xLowerRange() 
				&& ball.rightOfBall() <= brick.xUpperRange()
				&& ball.getRY() >= brick.yLowerRange()
				&& ball.getRY() <= brick.yUpperRange()){

			return true;
		}

		return false;	
	}

}
