//
//This program prompts user to choose difficulty level 
// Then invokes different game constructor 

import java.awt.Font;

public class GameMenu{
	private int difficulty;

	public GameMenu(String[] args){

		difficulty = -1;
		run();

		if (difficulty >=0){
			play(args);
		}
	}
	
	public int getDifficulty(){
		return difficulty;
	}

	private void play(String[] args) {
		Game g = null;

		switch (difficulty){
		case 0:
			g = new Game(args);
			break;
		case 1:
			g = new MediumGame(args);
			break;
		case 2:
			g = new MasterGame(args);
			break;
		case 3:
			g = new HardGame(args);
			break;
		case 4:
			g = new GodGame(args);
			break;
		default: 
			break;
		}


		g.run();
				System.exit(0);
	}
	
	
	private void run() {
		StdDraw.clear();
		initialize();
		drawTitle();

		processInput();



	}

	private void initialize() {

		StdDraw.setCanvasSize(700, 700);
		StdDraw.setXscale(0, 300);
		StdDraw.setYscale(0, 300);

		StdDraw.enableDoubleBuffering();
	}


	private void processInput() {

		while (difficulty < 0) {
			if(StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped();
				switch(key) {
				case 'e':
					difficulty = 0;
					break;
				case 'n':
					difficulty = 1;
					break;
				case 'm':
					difficulty = 2;
					break;
				case 'h':
					difficulty = 3;
					break;
				case 'j':
					difficulty = 4;
					break;

				default: 
					break;
				}
			}
		}
	}


	private void drawTitle(){
		Font font = new Font("SansSerif", Font.BOLD, 25);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.setFont(font);
		StdDraw.picture(150, 150, "A-pile-of-bricks-0071.jpg", 300, 300);

		StdDraw.text(150, 220, "Breaker!");
		Font font2 = new Font("SansSerif", Font.BOLD, 20);
		StdDraw.setFont(font2);


		StdDraw.text(150, 200, "Select and press your difficulty to start:");		
		StdDraw.text(150, 190, "'e': impossibly easy");
		StdDraw.text(150, 180, "'n': not too rough");
		StdDraw.text(150, 170, "'m': master ");
		StdDraw.text(150, 160, "'h': no seriously, give up now");
		StdDraw.text(150, 150, "'j' if you are god");

		StdDraw.show();
//		StdDraw.pause(10);
	}
}
