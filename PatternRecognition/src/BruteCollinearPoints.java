/******************************************************************************
 *  Author: Eric Schraeder
 *  
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  Coursera, Algorithms Part I programming assignment 3
 *
 ******************************************************************************/
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> lineSeg;
	private int segmentCount;
	
	public BruteCollinearPoints(Point[] points) {
		lineSeg = new ArrayList<LineSegment>();
		segmentCount = 0;
		int N = points.length;
		Arrays.sort(points);
	
		//brute force algorithm for finding 4 collinear points ~ N^4
		//checks that points are in natural order as defined in Point.java, is so creates line from p to s
		for (int p = 0; p < N; p++) {
			for (int q = p+1; q < N; q++) {
				for (int r = p+2; r < N; r++) {
					for (int s = p+3; s < N; s++) {
						double slopePQ = points[p].slopeTo(points[q]);
						double slopeQR = points[q].slopeTo(points[r]);
						double slopeRS = points[r].slopeTo(points[s]);
						if (slopePQ == slopeQR && slopeQR == slopeRS) {
							int order = (points[p].compareTo(points[q])) + (points[q].compareTo(points[r])) + (points[r].compareTo(points[s]));
							if (order == -3) {
								LineSegment ls = new LineSegment(points[p], points[s]);
								lineSeg.add(ls);
								/*System.out.println("p: " + points[p].toString() + " q: " + points[q].toString() + " r: " + points[r].toString()
										+ " s: " + points[s].toString());*/
								segmentCount++;
								break;
							}
						}
					}
				}
			}
		}
		
	} 
	
	public int numberOfSegments() {
		return segmentCount;
	}
    public LineSegment[] segments() {
    	LineSegment[] ls = new LineSegment[segmentCount];
    	lineSeg.toArray(ls);
    	return ls;
    }

    //client to parse input txt file and create StdDraw output
    
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
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
	}
}

