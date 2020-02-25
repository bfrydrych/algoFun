
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.*;

public class Percolation {

    private static final int SITE_BLOCKED = 0;
    private static final int SITE_OPEN = 1;

    private int[][] sites = null;

    private Set<Integer> tops = new HashSet<>();
    private Set<Integer> bottoms = new HashSet<>();

    private int openSites = 0;

    private WeightedQuickUnionUF weightedQuickUnionUF;


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


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        sites = new int[n][n];
        int element = -1;
        for (int i = 0; i < sites.length; ++i) {
            for (int j = 0; j < sites.length; ++j) {
                sites[i][j] = SITE_BLOCKED;
                rowColsToElements.put(new RowCol(i + 1, j + 1), ++element);
            }
        }

        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBounds(row, col);
        sites[row - 1][col - 1] = SITE_OPEN;
        unionWithUpIfOpen(row, col);
        unionWithDownIfOpen(row, col);
        unionWithLeftIfOpen(row, col);
        unionWithRightIfOpen(row, col);

        // if is Top, mark it
        if (row - 1 == 0) {
            tops.add(col);
        }

        // if is Bottom, mark it
        if (row == sites.length) {
            bottoms.add(col);
        }
        ++openSites;
    }



    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return sites[row - 1][col - 1] == SITE_OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        Iterator<Integer> topsIter = tops.iterator();
        while (topsIter.hasNext()) {
            Integer topCol = topsIter.next();
            if (weightedQuickUnionUF.connected(rowColsToElements.get(new RowCol(row, col)), rowColsToElements.get(new RowCol(1, topCol)))) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (!bottoms.isEmpty() && !tops.isEmpty()) {
            Iterator<Integer> topsIter = tops.iterator();
            while (topsIter.hasNext()) {
                Iterator<Integer> bottomsIter = bottoms.iterator();
                Integer topCol = topsIter.next();
                while (bottomsIter.hasNext()) {
                    Integer bottomCol = bottomsIter.next();
                    if (weightedQuickUnionUF.connected(rowColsToElements.get(new RowCol(sites.length, bottomCol)), rowColsToElements.get(new RowCol(1, topCol)))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private void unionWithDownIfOpen(int row, int col) {
        if (row + 1  > sites.length) {
            return;
        }
        if (isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(rowColsToElements.get(new RowCol(row + 1, col)), rowColsToElements.get(new RowCol(row, col)));
        }
    }

    private void unionWithRightIfOpen(int row, int col) {
        if (col + 1 > sites[0].length) {
            return;
        }
        if (isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(rowColsToElements.get(new RowCol(row, col + 1)), rowColsToElements.get(new RowCol(row, col)));
        }
    }

    private void unionWithLeftIfOpen(int row, int col) {
        if (col -1 - 1 < 0) {
            return;
        }
        if (isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(rowColsToElements.get(new RowCol(row, col - 1)), rowColsToElements.get(new RowCol(row, col)));
        }
    }

    private void unionWithUpIfOpen(int row, int col) {
        if (row - 1 - 1 < 0) {
            return;
        }
        if (isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(rowColsToElements.get(new RowCol(row - 1, col)), rowColsToElements.get(new RowCol(row, col)));
        }
    }

    private void checkBounds(int row, int col) {
        if (sites.length < row || sites[0].length < col) {
            throw new IllegalArgumentException();
        }
    }
}
