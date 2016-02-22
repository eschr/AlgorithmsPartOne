import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import edu.princeton.cs.algs4.StdDraw;


public class FastCollinearPoints {
	private ArrayList<LineSegment> lineSegs;
	private int segCount;

	public FastCollinearPoints(Point[] points) {
		lineSegs = new ArrayList<LineSegment>();
		segCount = 0;
		int N = points.length;
        //sort by natural order first
		Arrays.sort(points);
		Point[] naturalOrder = points.clone();
		
        //natural is the current pivot point that the points array will be sorted by using a Comparator with respect to natural
		//see Point.java for implementation of comparator returned by natural.slopeOrder()
		for (Point natural : naturalOrder) {
			Arrays.sort(points, natural.slopeOrder());     //sort the points with respect to their slopes with the natural
			int i = 1;
			
			/*
			 * 1. calculate an initial slope for comparison (currentCompareSlope)
			 * 2. In second while loop : while the next point's slope (with respect to natural) is equal to the currentCompareSlope, 
			 * 	  add the point to the list
			 * 3. if list contains 3 points or greater a  valid line segment may have been found
			 * 4. check for validity and prevent duplicate line segments by checking if the natural is less than all points in the list
			 * 5. then sort the list to put greatest point at end
			 * 6. create a new LineSegment
			 */
			while (i < N) {
				ArrayList<Point> pointsWithSameSlope = new ArrayList<Point>();  //ArrayList to add points with equal slopes to natural
				double currentCompareSlope = natural.slopeTo(points[i]);
				pointsWithSameSlope.add(points[i]);
				int j = i + 1;
				//continue to end of points array or until a different slope is calculated
				while (j < N && currentCompareSlope == natural.slopeTo(points[j])) {
					pointsWithSameSlope.add(points[j]);
					j++;
				}
				if (pointsWithSameSlope.size() >= 3) {
					if (allPointsLessThanNatural(pointsWithSameSlope, natural)) {
						Collections.sort(pointsWithSameSlope);
						lineSegs.add(new LineSegment(natural, pointsWithSameSlope.get(pointsWithSameSlope.size() - 1)));
						segCount++;
					}
				}
				/* After the inner while loop ends j is equal to N or j is equal to the index of a point whose
				 * slope with the natural differed from currentCompareSlope (natural to points[i])  
				 * This slope (natural to points[j]) will be next for comparison, set i to j.
				 */
				i = j;
			}
		}

	}

   private void pprint(ArrayList<Point> p) {
	   for (int i = 0; i < p.size(); i++) {
		   System.out.print(p.get(i) + " ");
	   }
	   System.out.println();
   }
   
   //checks if every point in the list is less than the natural, see compareTo() Point.java
   private boolean allPointsLessThanNatural(ArrayList<Point> list, Point natural) {
	   for (Point p: list) {
		   if (natural.compareTo(p) >= 0) return false;
	   }
	   return true;
   }
   
   
   public int numberOfSegments()  {
	   return segCount;
   }
   
   //convert the ArrayList to an array of LineSegments and return this array
   public LineSegment[] segments()  {
	   LineSegment[] ls = new LineSegment[lineSegs.size()];
	   lineSegs.toArray(ls);
	   return ls;
	   
   }
   
   public static void main(String[] args) {

		// read the N points from a file

		In in = new In(args[0]);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.show(0);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(0.003);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.002);
		System.out.println(collinear.numberOfSegments());
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
	}
}