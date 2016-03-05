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
 * Solver.java - uses general method as defined by A* search algorithm 
 * 
 * A* is an informed search algorithm, or a best-first search, 
 * meaning that it solves problems by searching among all possible paths to the solution (goal) 
 * for the one that incurs the smallest cost (least distance traveled, shortest time, etc.), 
 * and among these paths it first considers the ones that appear to lead most quickly to the solution.
 * 
 * Solver.java uses a minimum priority queue of nodes that implement a Manhattan priority function
 * 
 */


import edu.princeton.cs.algs4.*;

public class Solver {
	
	private MinPQ<SolverNode> minPriorityQue;
	private SolverNode solution;
	private Board original;
	
	/* SolverNode defines a search node of the game to be a board, a priority
	 * the number of moves made to reach the board, and the previous search node.
	 * 
	 * SolverNode priority is defined as a boards Manhattan distance + moves
	 * Manhattan priority function. The sum of the Manhattan distances (sum of the vertical and horizontal distance) from the blocks to their goal positions, 
	 * plus the number of moves made so far to get to the search node.
	 */
	private static class SolverNode implements Comparable<SolverNode> {
		private Board board;
		private int priority;
		private int moves;
		private SolverNode previous;
		
		public SolverNode(Board b, int move, SolverNode prev) {
			board = b;
			moves = move;
			priority = b.manhattan() + moves;
			previous = prev;
		}
		
		public int compareTo(SolverNode o) {
			if (this.priority < o.priority) 
				return -1;
			if (this.priority > o.priority) 
				return 1;
			if (this.board.hamming() > o.board.hamming()) 
				return -1;
			if (this.board.equals(o.board))
				return 0;
			else return 1;
		}
	
	}
	
	
    public Solver(Board initial)  {
    	if (initial == null) throw new NullPointerException();
    	
    	original = initial;
    	
    	minPriorityQue = new MinPQ<SolverNode>();
    	
    	SolverNode init = new SolverNode(initial, 0, null);
    	SolverNode twinInit = new SolverNode(initial.twin(), 0, null);

    	minPriorityQue.insert(twinInit);
    	minPriorityQue.insert(init);
    	
    	while (true) {
    		solution = minPriorityQue.delMin();
    		if (solution.board.isGoal()) break;
    		
    		for (Board b : solution.board.neighbors()) {
    			if (! thisBoardAlreadyUsed(b, solution)) {
    				minPriorityQue.insert(new SolverNode(b, solution.moves + 1, solution));
    			}
    		}
    	}
    	
    	
    }
    
    //check if the Board b is already been added to the game tree
    private boolean thisBoardAlreadyUsed(Board b, SolverNode base) {
    	SolverNode curr = base;
    	while (curr.previous != null) {
    		if (curr.board.equals(b) || curr.board.equals(original)) {
    			return true;
    		}
    		curr = curr.previous;
    		
    	}
    	return false;
    }
    
    // is the initial board solvable?
    // detect unsolvable puzzles 
    public boolean isSolvable() {
    	SolverNode cur = solution;
    	while (cur.previous != null) {
    		cur = cur.previous;
    	}
    	if (cur.board.equals(original)) return true;
    	else return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	if (isSolvable()) return solution.moves;
    	else return -1;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()  {
    	if (! isSolvable() ) return null;
    	
    	Stack<Board> stack = new Stack<Board>();
    	
    	SolverNode cur = solution;
    	while (cur.previous != null) {
    		stack.push(cur.board);
    		cur = cur.previous;
    	}
 
    	stack.push(cur.board);
    	return stack;
    }
    
    //test client
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
