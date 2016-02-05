
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF weightedUF;
	private boolean[][] percolationGrid;
	private int gridSize;
	private int virtual_top;
	private int virtual_bottom;
	
	public Percolation(int N) {
		// create N-by-N grid, with all sites blocked
		if ( N <= 0 ) throw new IllegalArgumentException();
		
		gridSize = N;
		percolationGrid = new boolean[N][N];
		
		virtual_top = 0;
		virtual_bottom = (gridSize * gridSize) + 1;
		
		//initialize the UF with 2 extra virtual nodes 
		//virtual nodes will be used to check if the system percolates, see initialization below
		weightedUF = new WeightedQuickUnionUF((gridSize * gridSize) + 2);
		
		/** initialize the top row so element 0 is connected with 1 thru N
		 *  as well initialize so all elements in the last row of size N attached to N+2
		 */
		
		for (int i = 1; i <= gridSize; i++) {
			weightedUF.union(virtual_top, i);
		}
		
		int bottomRowStart = gridSize * ( gridSize - 1 ) + 1;
		
		for (int j = bottomRowStart; j < virtual_bottom; j++) {
			weightedUF.union(virtual_bottom, j);
		}
		
	}
	public void open(int i, int j) {
		// open site (row i, column j) if it is not open already
		if (! isOpen(i, j) ) {
			int x = i - 1;
			int y = j - 1;
			percolationGrid[x][y] = true;
			
			//now check if the surrounding elements are open
			int siteUF = convertTo1D(x, y);
			
			checkNeighbor(siteUF, i - 1, j);  //above
			if (percolates()) return;
			checkNeighbor(siteUF, i + 1, j);  //below
			if (percolates()) return;
			checkNeighbor(siteUF, i, j - 1);  //left
			if (percolates()) return;
			checkNeighbor(siteUF, i, j+ 1);   //right
			if (percolates()) return;
			

		}
		return;
	}
	
	public boolean isOpen(int i, int j)  {
		// is site (row i, column j) open?
		checkRange(i, j);
		
		int x = i - 1;
		int y = j - 1;
		return percolationGrid[x][y] == true;
	}
	public boolean isFull(int i, int j) {
		// is site (row i, column j) full?
		checkRange(i, j);
		
		if (! isOpen(i, j) ) return false;
		
		int x = i - 1;
		int y = j - 1;
		
		int locationInUF = convertTo1D(x, y);
		
		return weightedUF.connected(virtual_top, locationInUF);

	}
	public boolean percolates() {
		return weightedUF.connected(virtual_top, virtual_bottom);
	}
	
	private void checkNeighbor(int currentSiteUF, int i, int j) {
		if ( (i == 0) || (i > gridSize)) return;
		if ( (j == 0) || (j > gridSize)) return;
		
		int neighborUF = convertTo1D(i-1, j-1);
		
		if ( isOpen(i, j) ) weightedUF.union(currentSiteUF, neighborUF);
		return;	
	}
	
	private int convertTo1D(int i, int j) {
		return i * gridSize + j + 1;
	}
	
	private void checkRange(int i, int j){
		if (i < 1) throw new IndexOutOfBoundsException();
		else if (j > gridSize) throw new IndexOutOfBoundsException();
	}

}
