import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.*;

public class PercolationStats {

    private double[] thresholds;

    private Map<RowCol, Integer> rowColsToElements = new HashMap<>();

    private class RowCol {
        public Integer row;
        public Integer col;

        public RowCol(Integer row, Integer col) {
            this.row = row;
            this.col = col;
        }

        public int hashCode() {
            return row.hashCode() + col.hashCode();
        }

        public boolean equals(Object obj) {
            RowCol objRowCol = (RowCol) obj;
            return objRowCol.row.equals(this.row) && objRowCol.col.equals(this.col);
        }

        public String toString() {
            return "Row: " + row + "Col: " + col;
        }
    }

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        int[][] sites = new int[n][n];
        int element = -1;
        for (int i = 0; i < sites.length; ++i) {
            for (int j = 0; j < sites.length; ++j) {
                rowColsToElements.put(new RowCol(i + 1, j + 1), ++element);
            }
        }
        thresholds = new double[trials];


        for (int i = 1; i <= trials; ++i) {
            Percolation percolation = new Percolation(n);
            double threshold = computePercThreshold(percolation, n);
            thresholds[i - 1] = threshold;
        }

    }

    private double computePercThreshold(Percolation percolation, int n) {
        //n indexed from 1

        List<Map.Entry<RowCol, Integer>> opens = new ArrayList<>();
        Iterator<Map.Entry<RowCol, Integer>> iter =  rowColsToElements.entrySet().iterator();
        while (iter.hasNext()) {
            opens.add(iter.next());
        }

        return nextComputation(percolation, n, 0, opens);
    }

    private double nextComputation(Percolation percolation, int n, int currentTrial, List<Map.Entry<RowCol, Integer>> opens) {
        currentTrial++;
        // 0 to opens.size - 1
        int toOpen = StdRandom.uniform(opens.size());

        Map.Entry<RowCol, Integer> siteToOpen = opens.get(toOpen);
        percolation.open(siteToOpen.getKey().row, siteToOpen.getKey().col);
        if (percolation.percolates()) {
            return Double.valueOf(currentTrial) / Double.valueOf(n * n);
        } else {
            opens.remove(toOpen);
            return nextComputation(percolation, n, currentTrial, opens);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        return mean - 1.96 * stddev;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        return mean + 1.96 * stddev;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
