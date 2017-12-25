//
//This program makes ball class

public class Ball{
	private double rx, ry;
	private double vx, vy;   
	private double radius;   

	public Ball() {
		rx = 150;
		ry = 5.5;
		vx      = 4  + Math.random() * 0.3;

		vy      = 6 + Math.random() * 0.3;

		radius  = 3;
	} 

	public double getRX(){
		return rx;
	}

	public double getRY(){
		return ry;
	}

	public double getVX(){
		return vx;
	}

	public double getVY(){
		return vy;
	}


	public void setRX(double rx){
		this.rx = rx;
	}

	public void setRY(double ry){
		this.ry = ry;
	}

	public void setVX(double vx){
		this.vx = vx;
	}

	public void setVY(double vy){
		this.vy = vy;
	}
	
	public void setRadius(double r){
		this.radius = r;
	}



	public double getRadius(){
		return radius;
	}

	/*
	 * helper methods for finding top, left, right and bottom of the ball
	 */
	
	public double leftOfBall(){
		return this.rx - this.radius;
	}
	public double rightOfBall(){
		return this.rx + this.radius;
	}	
	public double topOfBall(){
		return this.ry + this.radius;
	}	
	public double bottomOfBall(){
		return this.ry - this.radius;
	}


	public void move() {
		if ((rx + vx > 300.0) || (rx + vx < 0)) vx = -vx ;
		if ((ry + vy > 250.0)) vy = -vy ;

		rx = rx + vx; ry = ry + vy;
	}


	public void draw() {
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdDraw.filledCircle(rx, ry, radius);
		StdDraw.enableDoubleBuffering();
	}

}

