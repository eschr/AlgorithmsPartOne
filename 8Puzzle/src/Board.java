import java.util.Arrays;

public class Board {
	
	private int N;
	private int[][] board;
	private int[][] twinBoard;
	private int[] boardAsArray;
	
	// construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
	// constructs a 1D array and a twin board 
    public Board(int[][] blocks) {
    	N = blocks.length;
    	boardAsArray = new int[N * N];
    	board = new int[N][N];
    	twinBoard = new int[N][N];
   
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			boardAsArray[N * i + j] = blocks[i][j];
    			board[i][j] = blocks[i][j];
    			twinBoard[i][j] = blocks[i][j];
    		}
    	}
    	swapPairOfBlocks();
    	
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
    	return 1;
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
    // does this board equal y?
    /*public Iterable<Board> neighbors() {
    	
    }*/
    // string representation of this board (in the output format specified below)
    public String toString() {
    	String boardAsString = N + "\n";
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			boardAsString += board[i][j] + " ";
    		}
    		boardAsString += "\n";
    	}
    	return boardAsString;
    }

    public static void main(String[] args) {
    	int[][] bricks = {{0, 1, 3},{4,8,2},{7,6,5}};
    	Board board = new Board(bricks);
    	System.out.println(board.hamming());
    	System.out.println(board.isGoal());
    	System.out.println(board.toString());
    	System.out.println("----------");
    	System.out.println(board.twin().toString());
    }
}