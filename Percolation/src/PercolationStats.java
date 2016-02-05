import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
   private double[] trialsResults;
   private int trials;
   
   public PercolationStats(int N, int T) {
	   // perform T independent experiments on an N-by-N grid
	   int x = 0;
	   int y = 0;
	   int openCalls = 0;
	   double grid = (double) N * N;
	   
	   trials = T;
	   trialsResults = new double[T];
	   
	   for (int i = 0; i < T; i++) {
		   Percolation percolationSystem = new Percolation(N);
		   while (! percolationSystem.percolates() ) {
			   x = StdRandom.uniform(1, N+1);
			   y = StdRandom.uniform(1, N+1);
			   
			   if ( ! percolationSystem.isOpen(x, y) ) {
				   percolationSystem.open(x, y);
				   openCalls++;
			   }
		   }
		   
		   trialsResults[i] = openCalls / grid;
		   openCalls = 0;
	   }  
   }
   
   public double mean() {
	   // sample mean of percolation threshold
	   return StdStats.mean(trialsResults);
   }
   public double stddev() {
	   // sample standard deviation of percolation threshold
	   return StdStats.stddev(trialsResults);
   }
   public double confidenceLo() {
	   // low  endpoint of 95% confidence interval
	   return this.mean() - ( (1.96 * this.stddev() ) / Math.sqrt(trials) );
   }
   public double confidenceHi() {
	   // high endpoint of 95% confidence interval
	   return this.mean() + ( (1.96 * this.stddev() ) / Math.sqrt(trials) );
   }

   public static void main(String[] args) {
	   // test client (described below)
	   int N = Integer.parseInt( args[0] );
	   int T = Integer.parseInt( args[1] );
	   
	   if ( (N <= 0) || ( T <= 0 ) ) throw new IllegalArgumentException();
	   
	   PercolationStats percStats = new PercolationStats(N, T);
	   System.out.println("mean                    = " + percStats.mean());
	   System.out.println("stddev                  = " + percStats.stddev());
	   System.out.println("95% confidence interval = " + percStats.confidenceLo() + ", " + percStats.confidenceHi());
	   
	   return;
	   
   }
}
