import edu.princeton.cs.algs4.*;

public class Solver {
	
	private MinPQ<SolverNode> minPriorityQue;
	private SolverNode solution;
	private Board original;
	private int movesToSolve;
	
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
		
		@Override
		public int compareTo(SolverNode o) {
			if (this.priority < o.priority) return -1;
			else if (this.priority > o.priority) return 1;
			else return 0;
		}
	}
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)  {
    	original = initial;
    	minPriorityQue = new MinPQ<SolverNode>();
    	SolverNode init = new SolverNode(initial, 0, null);
    	SolverNode twinInit = new SolverNode(initial.twin(), 0, null);
    	minPriorityQue.insert(init);
    	minPriorityQue.insert(twinInit);
    	movesToSolve = 0;
    	while (true) {
    		solution = minPriorityQue.delMin();
    		if (solution.board.isGoal()) break;
    		movesToSolve++;
    		for (Board b : solution.board.neighbors()) {
    			if (movesToSolve == 1) {
    				minPriorityQue.insert(new SolverNode(b, movesToSolve, solution));
    			}
    			else if (movesToSolve > 1) {
    				if (! b.equals(solution.previous.board)) {
    					minPriorityQue.insert(new SolverNode(b, movesToSolve, solution));
    				}
    			}
    		}
    	}
    	
    }
    // is the initial board solvable?
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
    	if (isSolvable()) return movesToSolve;
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
