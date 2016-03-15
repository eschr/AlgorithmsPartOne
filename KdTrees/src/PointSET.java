import edu.princeton.cs.algs4.*;



public class PointSET {
	SET<Point2D> set;
	
	// construct an empty set of points 
	public PointSET()
	{
		set = new SET<Point2D>();
	}
	// is the set empty? 
	public boolean isEmpty() 
	{
		return set.isEmpty();
	}
	// number of points in the set 
	public int size() 
	{
		return set.size();
	}
	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) 
	{
		set.add(p);
	}
	// does the set contain point p? 
	public boolean contains(Point2D p) 
	{
		return set.contains(p);
	}

	// draw all points to standard draw 
	public void draw() 
	{
		Point2D center = new Point2D(0.5, 0.5);
		for (Point2D pt : set) {
			pt.draw();
			pt.drawTo(center);
			
		}
	}
	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect)
	{
		Stack<Point2D> pointsInRange = new Stack<Point2D>();
		for (Point2D pt : set) {
			if (rect.contains(pt)) pointsInRange.push(pt);
		}
		
		return pointsInRange;
		
	}

	public Point2D nearest(Point2D p)
	{
		double smallestDistance = 1.0;
		Point2D closestPoint = null;
		for (Point2D pt : set) {
			if (pt.distanceTo(p) < smallestDistance) closestPoint = pt; 
		}
		return closestPoint;
	}

	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);

		StdDraw.show(0);
		
		StdDraw.setPenRadius(0.02);

		// initialize the data structures with N points from standard input
		PointSET brute = new PointSET();

		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);

			brute.insert(p);
		}

		brute.draw();
	}
}
