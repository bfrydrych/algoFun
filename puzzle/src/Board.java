import java.util.Iterator;

public class Board {

    private int[][] tilesRowCol;
    private int[][] goalTilesRowCol;
    private int[][] goalTilesCoordinatesRowCol;
    private int[] emptyTileCoordinateRowCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tilesRowCol) {
        initTiles(tilesRowCol);
    }

    private void initTiles(int[][] tilesRowCol) {
        this.goalTilesRowCol = new int[tilesRowCol.length][tilesRowCol.length];
        this.tilesRowCol = new int[tilesRowCol.length][tilesRowCol.length];
        this.goalTilesCoordinatesRowCol = new int[tilesRowCol.length * 2][2];
        this.emptyTileCoordinateRowCol = new int[2];
        int goalTileNr = 1;
        for (int row = 0; row < tilesRowCol.length; ++row) {
            for (int col = 0; col < tilesRowCol.length; ++col) {
                this.tilesRowCol[row][col] = tilesRowCol[row][col];
                if (tilesRowCol[row][col] == 0) {
                    emptyTileCoordinateRowCol[0] = row;
                    emptyTileCoordinateRowCol[1] = col;
                }

                if (row == tilesRowCol.length - 1 && col == tilesRowCol.length - 1) {
                    this.goalTilesRowCol[row][col] = 0;
                    this.goalTilesCoordinatesRowCol[0][0] = row;
                    this.goalTilesCoordinatesRowCol[0][1] = col;
                } else {
                    this.goalTilesRowCol[row][col] = goalTileNr;
                    this.goalTilesCoordinatesRowCol[goalTileNr][0] = row;
                    this.goalTilesCoordinatesRowCol[goalTileNr][1] = col;
                    goalTileNr++;
                }
            }
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(tilesRowCol.length)
                .append(System.lineSeparator())
                .append(" ");
        for (int row = 0; row < tilesRowCol.length; ++row) {
            for (int col = 0; col < tilesRowCol.length; ++col) {
                builder.append(this.tilesRowCol[row][col]);
                builder.append(" ");
            }
            builder.append(System.lineSeparator());
            builder.append(" ");
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return tilesRowCol.length;
    }

    // number of tiles out of place
    public int hamming() {
        int outOfPlace = 0;
        for (int col = 0; col < tilesRowCol.length; ++col) {
            for (int row = 0; row < tilesRowCol.length; ++row) {
                if (tilesRowCol[row][col] != goalTilesRowCol[row][col]) {
                    outOfPlace++;
                }
            }
        }
        return outOfPlace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int col = 0; col < tilesRowCol.length; ++col) {
            for (int row = 0; row < tilesRowCol.length; ++row) {
                if (tilesRowCol[row][col] != goalTilesRowCol[row][col]) {
                    int tile = tilesRowCol[row][col];
                    int goalRow = goalTilesCoordinatesRowCol[tile][0];
                    int goalCol = goalTilesCoordinatesRowCol[tile][1];
                    int rowDist;
                    if (row > goalRow) {
                        rowDist = row - goalRow;
                    } else {
                        rowDist = goalRow - row;
                    }

                    int colDist;
                    if (col > goalCol) {
                        colDist = col - goalCol;
                    } else {
                        colDist = goalCol - col;
                    }


                    int dist = rowDist + colDist;
                    sum = sum + dist;
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < tilesRowCol.length; ++row) {
            for (int col = 0; col < tilesRowCol.length; ++col) {
                if (tilesRowCol[row][col] != goalTilesRowCol[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y instanceof Board) {
            for (int col = 0; col < tilesRowCol.length; ++col) {
                for (int row = 0; row < tilesRowCol.length; ++row) {
                    if (tilesRowCol[row][col] != ((Board) y).tilesRowCol[row][col]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int row = emptyTileCoordinateRowCol[0];
        int col = emptyTileCoordinateRowCol[1];

        Board[] neighbors = new Board[4];
        int neighborsCount = 0;
        Board newBoard1 = moveZero(row + 1, col);
        neighborsCount = insertNeighbour(neighbors, neighborsCount, newBoard1);
        Board newBoard2 = moveZero(row - 1, col);
        neighborsCount = insertNeighbour(neighbors, neighborsCount, newBoard2);
        Board newBoard3 = moveZero(row, col + 1);
        neighborsCount = insertNeighbour(neighbors, neighborsCount, newBoard3);
        Board newBoard4 = moveZero(row, col - 1);
        neighborsCount = insertNeighbour(neighbors, neighborsCount, newBoard4);

        return new NeighborsIter(neighborsCount, neighbors);
    }

    private Board moveZero(int toRow, int toCol) {
        if (toRow < 0 || toCol < 0 || toRow == tilesRowCol.length || toCol == tilesRowCol.length) {
            return null;
        }
        Board newBoard = new Board(tilesRowCol);
        int tile = newBoard.tilesRowCol[toRow][toCol];
        int zeroRow = newBoard.emptyTileCoordinateRowCol[0];
        int zeroCol = newBoard.emptyTileCoordinateRowCol[1];
        newBoard.tilesRowCol[zeroRow][zeroCol] = tile;
        newBoard.tilesRowCol[toRow][toCol] = 0;

        newBoard.emptyTileCoordinateRowCol[0] = toRow;
        newBoard.emptyTileCoordinateRowCol[1] = toCol;
        return newBoard;
    }

    private static class NeighborsIter implements Iterable<Board> {

        private int totalCount;
        private int currentIndx = 0;
        private Board[] neighbors;

        public NeighborsIter(int totalCount, Board[] neighbors) {
            this.totalCount = totalCount;
            this.neighbors = neighbors;
        }

        @Override
        public Iterator<Board> iterator() {
            return new Iterator<Board>() {
                @Override
                public boolean hasNext() {
                    return currentIndx < totalCount;
                }

                @Override
                public Board next() {
                    return neighbors[currentIndx++];
                }
            };
        }
    }

    private int insertNeighbour(Board[] neighbores, int neighboresCount, Board newBoard1) {
        if (newBoard1 != null) {
            neighbores[neighboresCount++] = newBoard1;
        }
        return neighboresCount;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        Board newBoard = new Board(tilesRowCol);

        int firstRow = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);
        int firstCol = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);

        while (newBoard.tilesRowCol[firstRow][firstCol] == 0) {
            firstRow = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);
            firstCol = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);
        }

        int secondRow = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);
        int secondCol = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);
        while (newBoard.tilesRowCol[secondRow][secondCol] == 0) {
            secondRow = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);
            secondCol = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);
        }

        while (secondCol == firstCol && secondRow == firstRow) {
            while (newBoard.tilesRowCol[secondRow][secondCol] == 0) {
                secondRow = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);
                secondCol = edu.princeton.cs.algs4.StdRandom.uniform(0, newBoard.tilesRowCol.length - 1);
            }
        }

        int tile1 = newBoard.tilesRowCol[firstRow][firstCol];
        int tile2 = newBoard.tilesRowCol[secondRow][secondCol];
        newBoard.tilesRowCol[firstRow][firstCol] = tile2;
        newBoard.tilesRowCol[secondRow][secondCol] = tile1;

        return newBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 2;
        tiles[1][0] = 1;
        tiles[0][1] = 3;
        tiles[1][1] = 0;
        Board board = new Board(tiles);
        System.out.println(board.toString());

        testHamming();
        testManhattan();
        testIsGoal();
        testEquals();
        testNeighbors();
        testTwin();
    }

    private static void testHamming() {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 2;
        tiles[0][1] = 3;
        tiles[1][0] = 1;
        tiles[1][1] = 0;
        Board board = new Board(tiles);


        int actual = board.hamming();
        int expected = 3;
        assertMe(actual, expected);
        System.out.println("Test passed");
    }

    private static void testManhattan() {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 2;
        tiles[0][1] = 3;
        tiles[1][0] = 1;
        tiles[1][1] = 0;
        Board board = new Board(tiles);


        int actual = board.manhattan();
        int expected = 4;
        assertMe(actual, expected);
        System.out.println("Test passed");
    }

    private static void testIsGoal() {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 2;
        tiles[0][1] = 3;
        tiles[1][0] = 1;
        tiles[1][1] = 0;
        Board board = new Board(tiles);


        boolean actual = board.isGoal();
        boolean expected = false;
        assertMe(actual, expected);
        System.out.println("Test passed");
    }

    private static void testEquals() {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 2;
        tiles[0][1] = 3;
        tiles[1][0] = 1;
        tiles[1][1] = 0;
        Board board = new Board(tiles);

        int[][] tiles2 = new int[2][2];
        tiles[0][0] = 3;
        tiles[0][1] = 2;
        tiles[1][0] = 1;
        tiles[1][1] = 0;
        Board board2 = new Board(tiles2);


        boolean actual = board.equals(board2);
        boolean expected = false;
        assertMe(actual, expected);
        System.out.println("Test passed");
    }

    private static void testNeighbors() {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 2;
        tiles[0][1] = 3;
        tiles[1][0] = 1;
        tiles[1][1] = 0;
        Board board = new Board(tiles);

        int[][] neigh1 = new int[2][2];
        neigh1[0][0] = 2;
        neigh1[0][1] = 3;
        neigh1[1][0] = 0;
        neigh1[1][1] = 1;
        Board boardN1 = new Board(neigh1);

        int[][] neight2 = new int[2][2];
        neight2[0][0] = 2;
        neight2[0][1] = 0;
        neight2[1][0] = 1;
        neight2[1][1] = 3;
        Board boardN2 = new Board(neight2);


        Iterable<Board> actualNeighs = board.neighbors();
        Iterator<Board> actualNeightsIter = actualNeighs.iterator();

        int actualNumNeght = 0;
        while (actualNeightsIter.hasNext()) {
            actualNumNeght++;
            Board actualNeight = actualNeightsIter.next();
            if (actualNumNeght == 1) {
                boolean actualEqual = boardN2.equals(actualNeight);
                assertMe(actualEqual, true);
            }
            if (actualNumNeght == 2) {
                boolean actualEqual = boardN1.equals(actualNeight);
                assertMe(actualEqual, true);
            }
        }
        int expectedNumNeight = 2;
        assertMe(actualNumNeght, expectedNumNeight);

        System.out.println("Test passed");
    }

    private static void testTwin() {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 2;
        tiles[0][1] = 3;
        tiles[1][0] = 1;
        tiles[1][1] = 0;
        Board board = new Board(tiles);

        Board actualTwin = board.twin();

        int actualDiffs = 0;
        for (int row = 0; row < board.tilesRowCol.length; ++row) {
            for (int col = 0; col < board.tilesRowCol.length; ++col) {
                if (board.tilesRowCol[row][col] != actualTwin.tilesRowCol[row][col]) {
                    actualDiffs++;
                }
            }
        }

        int expectedDiffs = 2;
        assertMe(actualDiffs, expectedDiffs);

        System.out.println("Test passed");

    }

    private static void assertMe(int actual, int expected) {
        if (actual != expected) {
            throw new AssertionError("Actual: " + actual + " Expected: " + expected);
        }
    }

    private static void assertMe(boolean actual, boolean expected) {
        if (actual != expected) {
            throw new AssertionError("Actual: " + actual + " Expected: " + expected);
        }
    }
}
