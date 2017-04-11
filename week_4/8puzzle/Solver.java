import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
    private final Stack<Board> solution;
    private boolean isSolvable;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode prevNode;
        private int cachedPriority = -1;

        public SearchNode(Board board, int moves, SearchNode prevNode) {
            this.board = board;
            this.moves = moves;
            this.prevNode = prevNode;
        }

        private int priority() {
            if (cachedPriority == -1) {
                cachedPriority = moves + board.manhattan();
            }
            return cachedPriority;
        }

        public int compareTo(SearchNode node) {
            if (priority() < node.priority()) {
                return -1;
            } else if (priority() > node.priority()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     *  Find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }

        this.solution = new Stack<>();

        if (initial.isGoal()) {
            isSolvable = true;
            solution.push(initial);
            return;
        }

        if (initial.twin().isGoal()) {
            isSolvable = false;
            return;
        }

        Board board = initial;
        Board boardTwin = initial.twin();
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        SearchNode searchNode = new SearchNode(board, 0, null);
        SearchNode searchNodeTwin = new SearchNode(boardTwin, 0, null);
        pq.insert(searchNode);
        pqTwin.insert(searchNodeTwin);

        while (true) {
            searchNode = pq.delMin();
            searchNodeTwin = pqTwin.delMin();
            board = searchNode.board;
            boardTwin = searchNodeTwin.board;

            if (boardTwin.isGoal()) {
                isSolvable = false;
                return;
            }

            if (board.isGoal()) {
                isSolvable = true;
                solution.push(board);
                while (searchNode.prevNode != null) {
                    searchNode = searchNode.prevNode;
                    solution.push(searchNode.board);
                }
                return;
            }

            searchNode.moves++;
            searchNodeTwin.moves++;

            Iterable<Board> neighbors = board.neighbors();
            Iterable<Board> neighborsTwin = boardTwin.neighbors();

            for (Board neighbor : neighbors) {
                if (searchNode.prevNode != null && neighbor.equals(searchNode.prevNode.board)) {
                    continue;
                }
                SearchNode node = new SearchNode(neighbor, searchNode.moves, searchNode);
                pq.insert(node);
            }

            for (Board neighborTwin : neighborsTwin) {
                if (searchNodeTwin.prevNode != null && neighborTwin.equals(searchNodeTwin.prevNode.board)) {
                    continue;
                }
                SearchNode node = new SearchNode(neighborTwin, searchNodeTwin.moves, searchNodeTwin);
                pqTwin.insert(node);
            }
        }
    }

    /**
     *  Is the initial board solvable?
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (isSolvable()) {
            return solution.size() - 1;
        } else {
            return -1;
        }
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (isSolvable) {
            return solution;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
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
