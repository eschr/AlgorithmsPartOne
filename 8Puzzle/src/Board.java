import java.util.Arrays;
import java.util.Stack;

public class Board {
	
	private int N;
	private int zeroI;
	private int zeroJ;

	//this 
	private final int[][] board;
	
	//used for generating the neighbor boards
	private int[][] neighborBlocks;
	private int[][] twinBoard;
	private final int[] boardAsArray;
	private Stack<Board> neighborBoards;
	
	// construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
	// constructs a 1D array and a twin board 
    public Board(int[][] blocks) {
    	
    	N = blocks.length;
    	neighborBoards = new Stack<Board>();
    	boardAsArray = new int[N * N];
    	board = new int[N][N];
    	twinBoard = new int[N][N];
    	neighborBlocks = new int[N][N];
   
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			boardAsArray[N * i + j] = blocks[i][j];
    			board[i][j] = blocks[i][j];
    			twinBoard[i][j] = blocks[i][j];
    			neighborBlocks[i][j] = blocks[i][j];
    			if (blocks[i][j] == 0) {
    				zeroI = i;
    				zeroJ = j;
    			}
    		}
    	}
    	swapPairOfBlocks();	
    }
    //create new boards by swapping 0 with surrounding values    
    //check current position of 0 to generate valid boards
    //minimum of 2 boards will be generated, max of 4
    //  00 01 02 03
    //  10 11 12 13
    //  20 21 22 23
    //  30 31 32 33
    private void createNeighbors(int zeroI, int zeroJ) {
    	//case 00
    	if (zeroI == 0 && zeroJ == 0) {
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ));
    		return;
    	}
    	//case 03
    	else if (zeroI == 0 && zeroJ == N-1) {
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ));
    		return;
    	}
    	//case 30
    	else if (zeroI == N-1 && zeroJ == 0) {
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ));
    		return;
    	}
    	//case 33
    	else if (zeroI == N-1 && zeroJ == N-1) {
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ));
    		return;	
    	}
    	//case L side of board
    	else if (zeroJ == 0) {
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ));
    		return;
    	}
    	//case R side of board
    	else if (zeroJ == N-1) {
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ));
    		return;
    	}
    	//case Top of board
    	else if (zeroI == 0) {
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ));
    		return;
    	}
    	//case Bottom of board
    	else if (zeroI == N-1) {
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ));
    		return;
    	}
    	//0 not along any edges of the board
    	//4 exchanges and 4 possible neighbor boards
    	else {
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ));
    		return;
    	}
    }
    
    //takes [i][j] = location of 0
    private Board exchangeZeroWithAbove(int i, int j) {
    	int temp = neighborBlocks[i-1][j];
		neighborBlocks[i][j] = temp;
		neighborBlocks[i-1][j] = 0;
		Board b =  new Board(neighborBlocks);
		neighborBlocks[i][j] = 0;
		neighborBlocks[i-1][j] = temp;
		return b;
    }
    
    private Board exchangeZeroWithBelow(int i, int j) {
    	int temp = neighborBlocks[i+1][j];
		neighborBlocks[i][j] = temp;
		neighborBlocks[i+1][j] = 0;
		Board b =  new Board(neighborBlocks);
		neighborBlocks[i][j] = 0;
		neighborBlocks[i+1][j] = temp;
		return b;
    }
    private Board exchangeZeroWithLeft(int i, int j) {
    	int temp = neighborBlocks[i][j-1];
		neighborBlocks[i][j] = temp;
		neighborBlocks[i][j-1] = 0;
		Board b =  new Board(neighborBlocks);
		neighborBlocks[i][j] = 0;
		neighborBlocks[i][j-1] = temp;
		return b;
    }
    private Board exchangeZeroWithRight(int i, int j) {
    	int temp = neighborBlocks[i][j+1];
		neighborBlocks[i][j] = temp;
		neighborBlocks[i][j+1] = 0;
		Board b =  new Board(neighborBlocks);
		neighborBlocks[i][j] = 0;
		neighborBlocks[i][j+1] = temp;
		return b;
    }
    
    //swap pair of blocks to create the twin board
    //will be used to test if the original board is unsolvable
    private void swapPairOfBlocks() {
    	if( (twinBoard[0][0] != 0) && (twinBoard[0][1] != 0) ) {
    		int temp = twinBoard[0][0];
    		twinBoard[0][0] = twinBoard[0][1];
    		twinBoard[0][1] = temp;
    	}
    	else {
    		int temp = twinBoard[1][0];
    		twinBoard[1][0] = twinBoard[1][1];
    		twinBoard[1][1] = temp;
    	}
    }
    // board dimension N
    public int dimension() {
    	return N;
    }
    
    // number of blocks out of place
    public int hamming() {
    	int count = 0;
    	for (int i = 0; i < N * N; i++) {
    		if ( (boardAsArray[i] != (i+1)) && (boardAsArray[i] != 0) ) count++;
    	}
    	return count;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan()  {
    	int goal_i;
    	int goal_j;
    	int val;
    	int manhattan_sum = 0;
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (board[i][j] == 0) continue;
    			val = board[i][j] - 1;
    			goal_i = val / N;
    			goal_j = val % N;
    			manhattan_sum += (Math.abs(goal_i - i) + Math.abs(goal_j - j)); 
    		}
    	}
    	return manhattan_sum;
    }
    // is this board the goal board?
    public boolean isGoal()   {
    	return this.hamming() == 0;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin()  {
    	return new Board(twinBoard);	
    }
    // does this board equal y?
    public boolean equals(Object y)  {
    	if (y == null) return false;
    	if (this.getClass() != y.getClass()) return false;
    	Board that = (Board) y;
    	return Arrays.deepEquals(this.board, that.board);
    }
 
    public Iterable<Board> neighbors() {
    	createNeighbors(zeroI, zeroJ);
    	return neighborBoards;
    }

    // string representation of this board (in the output format specified below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", board[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

    public static void main(String[] args) {
    	int[][] bricks = {{8, 1, 3},{4, 0, 2},{7, 6, 5}};
    	Board board = new Board(bricks);
    	System.out.println(board.hamming());
    	System.out.println(board.manhattan());
    	System.out.println(board.isGoal());
    	System.out.println(board.toString());
    	System.out.println("----------");
    	//System.out.println(board.twin().toString());
    	for (Board b : board.neighbors()) {
    		System.out.println(b.toString());
    	}
    	System.out.println(board.toString());
    }
}