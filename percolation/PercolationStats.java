/* *****************************************************************************
 *  Name:              Mohamed Ashraf
 *  Coursera User ID:  123456
 *  Last modified:     6/18/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static double confidence = 1.96;
    private double xPar;
    private final double[] tempX;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n and trials must be bigger than 0");
        xPar = 0;
        tempX = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                if (!per.isOpen(randomRow, randomCol)) {
                    per.open(randomRow, randomCol);
                }
            }
            double xt = (double) per.numberOfOpenSites() / (double) (n * n);
            xPar += xt;
            tempX[i] = xt;
        }
        xPar /= trials;
    }

    // sample mean of percolation threshold
    public double mean() {
        return xPar;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double tmp = 0;
        for (int i = 0; i < tempX.length; i++) {
            tmp += (tempX[i] - xPar) * (tempX[i] - xPar);
        }
        tmp /= tempX.length - 1;
        return Math.sqrt(tmp);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double s = stddev();
        double t = Math.sqrt(tempX.length);
        return xPar - (confidence * s) / t;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double s = stddev();
        double t = Math.sqrt(tempX.length);
        return xPar + (confidence * s) / t;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n, t;
        n = Integer.parseInt(args[0]);
        t = Integer.parseInt(args[1]);
        PercolationStats obj = new PercolationStats(n, t);
        System.out.println("mean                    = " + obj.mean());
        System.out.println("stddev                  = " + obj.stddev());
        System.out.println(
                "95% confidence interval = [" + obj.confidenceLo() + ", " + obj.confidenceHi()
                        + "]");
    }
}
