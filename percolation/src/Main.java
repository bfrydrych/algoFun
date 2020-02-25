public class Main {

    public static void main(String[] args) {
       /* Percolation per = new Percolation(3);
        per.open(1, 1);
        per.open(3, 3);
        per.open(2, 1);
        per.open(3, 1);
        System.out.println(per.isOpen(1, 1));
        System.out.println(per.isFull(1, 1));
        System.out.println(per.numberOfOpenSites());

        System.out.println(per.isOpen(3, 3));
        System.out.println(per.isFull(3, 3));
        System.out.println(per.numberOfOpenSites());

        System.out.println(per.isOpen(2, 1));
        System.out.println(per.isFull(2, 1));
        System.out.println(per.numberOfOpenSites());

        System.out.println("Percolates: " + per.percolates());
        */
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
        //new PercolationStats(3, 2);
    }
}
