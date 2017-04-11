/*---------------------------------------------------------
 *  Thomas Pan
 *  12/11/2016
 *  Percolation: Exercise for modeling percolation
 *  utilizing union-find data type.
 *  To use: instantiante instance of Percolation, specifying
 *  its size.
 *---------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF wquuf; // WeightedQuickUnionUF object.
    private int[] grid; // Grid to keep track of whether sites are open.
    private int m; // Variable to store length or width of grid.
    private int top; // Variable to reference virtual top.
    private int bot; // Variable to reference virtual bot.
    private int length; // Variable to reference total length of Percolation.

    /*---------------------------------------------------------
     *  Instantiates Percolation Object.
     *---------------------------------------------------------*/
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        m = n;
        length = m*m;
        wquuf = new WeightedQuickUnionUF(length);
        grid = new int[length];
        top = 0;
        bot = length-1;
        for (int i = 0; i < length; i++) {
            grid[i] = 0;
        }
        for (int i = 0; i < m; i++) {
            wquuf.union(i, top);
        }
        for (int i = length-m; i < length; i++) {
            wquuf.union(i, bot);
        }
    }

    /*---------------------------------------------------------
     *  Opens a site (row, col) if not already open.
     *---------------------------------------------------------*/
    public void open(int row, int col) {
        validateInput(row);
        validateInput(col);

        grid[xyTo1D(row, col)] = 1;
        if (((row+1) <= m) && isOpen(row+1, col)) {
            wquuf.union(xyTo1D(row, col), xyTo1D(row+1, col));
        }
        if (((row-1) > 0) && isOpen(row-1, col)) {
            wquuf.union(xyTo1D(row, col), xyTo1D(row-1, col));
        }
        if (((col+1) <= m) && isOpen(row, col+1)) {
            wquuf.union(xyTo1D(row, col), xyTo1D(row, col+1));
        }
        if (((col-1) > 0) && isOpen(row, col-1)) {
            wquuf.union(xyTo1D(row, col), xyTo1D(row, col-1));
        }
    }

    /*---------------------------------------------------------
     *  Checks if site is open (row, col).
     *---------------------------------------------------------*/
    public boolean isOpen(int row, int col) {
        validateInput(row);
        validateInput(col);

        return grid[xyTo1D(row, col)] == 1;
    }

    /*---------------------------------------------------------
     *  Checks if site is full (row col); it has a path to
     *  the top row.
     *---------------------------------------------------------*/
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && wquuf.connected(top, xyTo1D(row, col));
    }

    /*---------------------------------------------------------
     *  Checks if the system percolates. It has a path from
     *  bototm row to top row.
     *---------------------------------------------------------*/
    public boolean percolates() {
        return wquuf.connected(top, bot);
    }

    /*---------------------------------------------------------
     *  Converts 2-D coordinates to 1-D coordinates (row, col).
     *---------------------------------------------------------*/
    private int xyTo1D(int row, int col) {
        row--;
        col--;
        return ((row*m)+col);
    }

    /*---------------------------------------------------------
     *  Checks if inputs are valid.
     *---------------------------------------------------------*/
    private void validateInput(int input) {
        if (input <= 0 || input > m) {
            throw new IllegalArgumentException();
        }
    }
    
    public static void main(String[] args) {
    }
}