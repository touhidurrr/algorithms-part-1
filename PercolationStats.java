import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double Z_SCORE = 1.96;
    private final double[] openSitesRatio;
    private double meanCache;
    private double stddevCache;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException();

        this.openSitesRatio = new double[trials];

        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                while (true) {
                    int x = StdRandom.uniformInt(1, n + 1);
                    int y = StdRandom.uniformInt(1, n + 1);
                    if (!p.isOpen(x, y)) {
                        p.open(x, y);
                        break;
                    }
                }
            }

            openSitesRatio[i] = ((double) p.numberOfOpenSites()) / (n * n);
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

    // sample mean of percolation threshold
    public double mean() {
        if (meanCache == 0) meanCache = StdStats.mean(openSitesRatio);
        return meanCache;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddevCache == 0) stddevCache = StdStats.stddev(openSitesRatio);
        return stddevCache;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (Z_SCORE * this.stddev()) / Math.sqrt(this.openSitesRatio.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (Z_SCORE * this.stddev()) / Math.sqrt(this.openSitesRatio.length);
    }
}
