import java.util.Arrays;

//
//This program invokes a GameMenu constructor 
//User should put username (No spcae), gender(female/male), and the brick map file in command line

public class GameMain {
	// private static final enum arguments = {NAME, GENDER, PATH};
	private static final int NUM_ARGS_NEEDED = 3;
	private static final int NAME = 0;
	private static final int GENDER = 1;
	private static final int PATH = 2;


	public static void main(String[] args) {
		


		//add default command line args
		if (args.length < NUM_ARGS_NEEDED) {
			String[] defaultArgs = new String[NUM_ARGS_NEEDED];
			defaultArgs[NAME] = "default";
			defaultArgs[GENDER] = "f";
			defaultArgs[PATH] = "map.txt";
			GameMenu menu = new GameMenu(defaultArgs);

		} 
		else {
			GameMenu menu = new GameMenu(args);
		}
		
		


	}
}

