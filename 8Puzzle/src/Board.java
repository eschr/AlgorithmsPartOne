/*
 * Author: Eric Schraeder 3/2016
 * Algorithms I Princeton University via coursera.org
 * Programming assignment III - 8 Puzzle
 * 
 * The 8-puzzle problem is a puzzle invented and popularized by Noyes Palmer Chapman in the 1870s. 
 * It is played on a 3-by-3 grid with 8 square blocks labeled 1 through 8 and a blank square. Goal is to rearrange the blocks so that they are in order, 
 * using as few moves as possible. You are permitted to slide blocks horizontally or vertically into the blank square. 
 * 
 * This project solves the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.
 * 
 * Board.java - represents a 8-puzzle problem board
 * Solver.java - implements the A* search algorithm 
 * 
 */

import java.util.Stack;

public class Board {
	
	//size of board
	private short N;
	
	//location of the open space "0" in the puzzle
	private short zeroI;
	private short zeroJ;

	//2D array representation of the board
	private final short[][] board;
	
	// construct a board from an N-by-N array of integers
    public Board(int[][] blocks) {
    	
    	N = (short) blocks.length;
    	board = new short[N][N];
  
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			board[i][j] = (short) blocks[i][j];
    			if (blocks[i][j] == 0) {
    				zeroI = (short) i;
    				zeroJ = (short) j;
    			}
    		}
    	}
 
    }
    
    /*
     *   createNeighbors parameters:  i and j locations of 0 for this board,
     *   int 2D array for creating new Boards, stack to store all possible neighbors of this Board 
     *   
     *   checks the location of the zero/open space for valid moves
     *   calls the appropriate exchangeZeroWith<POSITION>() method(s) to create all valid neighbor Boards
     *   neighbor Boards pushed to stack 
     *   
     */
    private void createNeighbors(int zeroI, int zeroJ, int[][] neighbors, Stack<Board> neighborBoards) {
    	//cases values = i x j
    	//case 0 x 0
    	if (zeroI == 0 && zeroJ == 0) {
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ, neighbors));
    		return;
    	}
    	//case 0 x N - 1
    	else if (zeroI == 0 && zeroJ == N-1) {
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ, neighbors));
    		return;
    	}
    	//case N-1 x 0
    	else if (zeroI == N-1 && zeroJ == 0) {
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ, neighbors));
    		return;
    	}
    	//case N-1 x N-1
    	else if (zeroI == N-1 && zeroJ == N-1) {
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ, neighbors));
    		return;	
    	}
    	//case L side of board
    	else if (zeroJ == 0) {
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ, neighbors));
    		return;
    	}
    	//case R side of board
    	else if (zeroJ == N-1) {
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ, neighbors));
    		return;
    	}
    	//case Top of board
    	else if (zeroI == 0) {
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ, neighbors));
    		return;
    	}
    	//case Bottom of board
    	else if (zeroI == N-1) {
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ, neighbors));
    		return;
    	}
    	//0 not along any edges of the board
    	//4 exchanges and 4 possible neighbor boards
    	else {
    		neighborBoards.push(exchangeZeroWithLeft(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithRight(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithAbove(zeroI, zeroJ, neighbors));
    		neighborBoards.push(exchangeZeroWithBelow(zeroI, zeroJ, neighbors));
    		return;
    	}
    }
    
    /*
     *  The following 'exchange' methods create the neighbor Boards of this Board
     *  a 'neighbor' is a valid move as defined for 8-puzzle problem
     *  methods swap the values with zero/open space and return a new neighbor Board
     */
    private Board exchangeZeroWithAbove(int i, int j, int[][] neighborBlocks) {
    	int temp = neighborBlocks[i-1][j];
		neighborBlocks[i][j] = temp;
		neighborBlocks[i-1][j] = 0;
		Board b =  new Board(neighborBlocks);
		neighborBlocks[i][j] = 0;
		neighborBlocks[i-1][j] = temp;
		return b;
    }
    
    private Board exchangeZeroWithBelow(int i, int j, int[][] neighborBlocks) {
    	int temp = neighborBlocks[i+1][j];
		neighborBlocks[i][j] = temp;
		neighborBlocks[i+1][j] = 0;
		Board b =  new Board(neighborBlocks);
		neighborBlocks[i][j] = 0;
		neighborBlocks[i+1][j] = temp;
		return b;
    }
    private Board exchangeZeroWithLeft(int i, int j, int[][] neighborBlocks) {
    	int temp = neighborBlocks[i][j-1];
		neighborBlocks[i][j] = temp;
		neighborBlocks[i][j-1] = 0;
		Board b =  new Board(neighborBlocks);
		neighborBlocks[i][j] = 0;
		neighborBlocks[i][j-1] = temp;
		return b;
    }
    private Board exchangeZeroWithRight(int i, int j, int[][] neighborBlocks) {
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
    private void swapPairOfBlocks(int[][] twinBoard) {
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
    
    public int dimension() {
    	return N;
    }
    
    /*
     * Hamming priority function. The number of blocks in the wrong position, plus the number of moves made so far to get to the search node. 
     * Intuitively, a search node with a small number of blocks in the wrong position is close to the goal, 
     * and we prefer a search node that have been reached using a small number of moves.
     */
    public int hamming() {
    	int count = 0;
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if(board[i][j] != 0 && board[i][j] != (N*i + j + 1)) count++;
    		}
    	}
    	return count;
    }
    
    // returns the Manhattan distance for this Board
    // Manhattan distance = distance between two points is the sum of the absolute differences of their Cartesian coordinates. 
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
    

    public boolean isGoal()   {
    	return this.hamming() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin()  {
    	int[][] twin = new int[N][N];
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			twin[i][j] = board[i][j];
    		}
    	}
    	swapPairOfBlocks(twin);
    	return new Board(twin);
    }
    
 
    public boolean equals(Object y)  {
    	if (y == null) return false;
    	if (this.getClass() != y.getClass()) return false;
    	Board that = (Board) y;
    	for(int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			if (this.board[i][j] != that.board[i][j])
    				return false;
    		}
    	}
    	return true;
    }
 
    // returns an Iterable containing all the neighbors of this board
    // neighbors new Boards that represent all valid moves of the 8-puzzle problem from current Board permutation
    public Iterable<Board> neighbors() {
    	Stack<Board> neighborsStack = new Stack<Board>();
    	
    	int[][] neighbors = new int[N][N];
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			neighbors[i][j] = board[i][j];
    		}
    	}
    	createNeighbors(zeroI, zeroJ, neighbors, neighborsStack);
    	return neighborsStack;
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

   // Test client for Board.java
   public static void main(String[] args) {
    	int[][] bricks = {{1, 0, 3},{4, 5, 2},{7, 6, 8}};
    	int[][] bricks2 = {{1, 3, 0},{4, 5, 2},{7, 6, 8}};
    	Board board = new Board(bricks);
    	Board board2 = new Board(bricks2);
    	System.out.println(board.equals(board2));
    	System.out.println(board.hamming());
    	System.out.println(board.manhattan());
    	System.out.println(board.isGoal());
    	System.out.println(board.toString());
    	System.out.println("----------");
    	System.out.println(board.twin().toString());
    	for (Board b : board.neighbors()) {
    		System.out.println(b.toString());
    	}
    	System.out.println(board.toString());
    }
}