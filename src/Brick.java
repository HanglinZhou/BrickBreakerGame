//
//This program makes brick class

public class Brick{
	private double x;
	private double y;
	private int row;
	private int col;
	private final double LENGTH = 300;
	private final double WIDTH = 124.5;
	private String color;
	private String specialEffect;

	public Brick (double px, double py, int row, int col, String bColor, String speEffect){
		x = px;
		y = py;
		this.row = row;
		this.col = col;
		color = bColor;
		specialEffect = speEffect;
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public String getColor(){
		return color;
	}
	
	public String getSpEffect(){
		return specialEffect;
	}
	
	public void setColor(String c){
		color = c;
	}
	
	public void setSpEffect(String e){
		specialEffect = e;
	}
	

	public double xUpperRange(){
		double aveLength = LENGTH / col;
		return x + aveLength /2;
	}

	public double xLowerRange(){
		double aveLength = LENGTH / col;
		return x - aveLength /2;
	}

	public double yUpperRange(){
		double aveLength = WIDTH / row ;
		return y + aveLength /2;
	}

	public double yLowerRange(){
		double aveLength = WIDTH / row;
		return y - aveLength /2;
	}


	public void draw(){
		
		switch(color){

		case "pink": 
			StdDraw.setPenColor(StdDraw.PINK);
			StdDraw.filledRectangle(x, y, LENGTH / (col * 2), WIDTH / (row * 2)); 
			break;
		case "yellow":
			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.filledRectangle(x, y, LENGTH / (col * 2), WIDTH / (row * 2)); 
			break;
		case "black":
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledRectangle(x, y, LENGTH / (col * 2), WIDTH / (row * 2)); 
			break;
		}

		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.rectangle(x, y, LENGTH / (col * 2), WIDTH / (row * 2));

	}


	public String toString(){
		return "x: " + x + " y: " + y;
	}

}