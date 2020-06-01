public class Main {
    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 2;
        tiles[1][0] = 1;
        tiles[0][1] = 3;
        tiles[1][1] = 0;
        Board board = new Board(tiles);
        System.out.println(board.toString());
    }
}
