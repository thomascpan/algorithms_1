import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int N;
    private final int[][] myBlocks;

    /**
     *  Construct a board from an n-by-n array of blocks(where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException();
        }
        N = blocks.length;
        myBlocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            myBlocks[i] = Arrays.copyOf(blocks[i], N);
        }
    }

    /**
     *  Board dimension n
     */
    public int dimension() {
        return N;
    }

    /**
     *  number of blocks out of place
     */
    public int hamming() {
        int count = 0;
        int correctValue = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (myBlocks[i][j] != correctValue && myBlocks[i][j] != 0) {
                    count++;
                }
                correctValue++;
            }
        }
        return count;
    }

    /**
     *  sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = myBlocks[i][j];
//                if (value != 0) {
//                    count += distanceToGoal(value-1, i, j);
//                }
                if (value != 0 && value != goalValueAt(i, j)) {
                    int initialX = (value - 1) / N;
                    int initialY = value - 1 - initialX * N;
                    int distance = Math.abs(i - initialX)
                            + Math.abs(j - initialY);
                    count += distance;
                }
            }
        }
        return count;
    }

    private int goalValueAt(int i, int j) {
        if (isEnd(i, j)) {
            return 0;
        }
        return 1 + i * N + j;
    }

    private boolean isEnd(int i, int j) {
        return i == N - 1 && j == N - 1;
    }

    /**
     *  is this board the goal board?
     */
    public boolean isGoal() {
        int value = 1;
        int[][] goal = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == (N-1) && j == (N-1)) {
                } else {
                    goal[i][j] = value;
                }
                value++;
            }
        }
        return Arrays.deepEquals(myBlocks, goal);
    }

    /**
     *  a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        Board board = new Board(myBlocks);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (myBlocks[i][j] != 0 && myBlocks[i][j + 1] != 0) {
                    board.swap(i, j, i, j + 1);
                    return board;
                }
            }
        }
        return board;
    }

    /**
     *  does this board equal y?
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (getClass() != y.getClass()) {
            return  false;
        }
        Board otherBoard = (Board) y;
        return Arrays.deepEquals(myBlocks, otherBoard.myBlocks);
    }

    /**
     *  all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<>();
        int x = 0;
        int y = 0;
        boolean done = false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (myBlocks[i][j] == 0) {
                    x = i;
                    y = j;
                    done = true;
                    break;
                }
            }
            if (done) {
                break;
            }
        }

        Board board = new Board(myBlocks);
        if (board.swap(x, y, x-1, y)) {
            stack.push(board);
        }

        board = new Board(myBlocks);
        if (board.swap(x, y, x+1, y)) {
            stack.push(board);
        }

        board = new Board(myBlocks);
        if (board.swap(x, y, x, y-1)) {
            stack.push(board);
        }

        board = new Board(myBlocks);
        if (board.swap(x, y, x, y+1)) {
            stack.push(board);
        }
        return stack;
    }

    private boolean swap(int x, int y, int nX, int nY) {
        if (nX < 0 || nX >= N || nY < 0 || nY >= N) {
            return false;
        }
        int temp = myBlocks[x][y];
        myBlocks[x][y] = myBlocks[nX][nY];
        myBlocks[nX][nY] = temp;
        return true;
    }

    private int distanceToGoal(int index, int x, int y) {
        int goalX = index / N;
        int goalY = index % N;
        return Math.abs(x - goalX) + Math.abs(y - goalY);
    }

    /**
     *  String representation of this board (in the output format specified below)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", myBlocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
    }
}