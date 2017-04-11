/*---------------------------------------------------------
 *  Thomas Pan
 *  12/11/2016
 *  PercolationStats: Calculates statistics of Percolation.
 *  To use: run code and specify two command-line arguments:
 *  size of grid, number of trails.
 *  ex. java PercolationStats 200 100
 *---------------------------------------------------------*/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results; // Keep track of results.

    /*---------------------------------------------------------
     *  Instantiates PercolationStats Object.
     *---------------------------------------------------------*/
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            int count = 0;
            while (!p.percolates() && count < (n * n)) {
                int col = StdRandom.uniform(1, n+1);
                int row = StdRandom.uniform(1, n+1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    count++;
                }
            }
            double dCount = (double) (count);
            double dTotal = (double) (n * n);
            double threshold = dCount/dTotal;
            results[i] = threshold;
        }
    }

    /*---------------------------------------------------------
     *  Calulates mean.
     *---------------------------------------------------------*/
    public double mean() {
        return StdStats.mean(results);
    }

    /*---------------------------------------------------------
     *  Caclulates standard deviation.
     *---------------------------------------------------------*/
    public double stddev() {
        return StdStats.stddev(results);
    }

    /*---------------------------------------------------------
     *  Calculates lower cofidence bound.
     *---------------------------------------------------------*/
    public double confidenceLo() {
        return mean() - ((1.96) * (stddev()/Math.sqrt(results.length)));
    }

    /*---------------------------------------------------------
     *  Calculates upper confidence bound.
     *---------------------------------------------------------*/
    public double confidenceHi() {
        return mean() + ((1.96) * (stddev()/Math.sqrt(results.length)));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}