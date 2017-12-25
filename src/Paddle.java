//
//This program makes Paddle class 

public class Paddle{
	public double x;
	private double length;
	private final double HEIGHT = 2.5;

	public Paddle(double positionX, double bLength){
		x = positionX;
		length = bLength;


	}

	public double getPX(){
		return x;
	}

	public double getPLength(){
		return length;
	}
	
	public void setPLength(double l){
		length = l;
	}


	public void draw() {
		StdDraw.setPenColor(StdDraw.PINK);
		StdDraw.filledRectangle(x, 1.25, length / 2, HEIGHT / 2);
		

	}

}
