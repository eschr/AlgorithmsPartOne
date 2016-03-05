/*
 * Ball
 */
public class Ball {
	private double rx, ry; //position of ball
	private double vx, vy; //velocity of ball
	
	private final double radius;  //radius of ball
	
	//set random color to each ball
	private int red;
	private int green;
	private int blue;
	
	public Ball() {
		rx = StdRandom.uniform(0.01, 0.06);
		ry = StdRandom.uniform(0.01, 0.06);
		vx = StdRandom.uniform(0.01, 0.08);
		vy = StdRandom.uniform(0.01, 0.05);
		radius = StdRandom.uniform(0.005, 0.03);
		red = StdRandom.uniform(0, 255);
		green = StdRandom.uniform(0, 255);
		blue = StdRandom.uniform(0, 255);
	}
	
	public void move(double dt) 
	{
		double SIZE = 1.0;
		if ((rx + vx*dt > SIZE - radius) || (rx + vx*dt < radius)) { vx = -vx; }
		if ((ry + vy*dt > SIZE - radius) || (ry + vy*dt < radius)) { vy = -vy; }
		rx = rx + vx*dt;
		ry = ry + vy*dt;
	}
	
	public void draw() 
	{
		StdDraw.setPenColor(red, green, blue);
		StdDraw.filledCircle(rx, ry, radius);
	}
}
