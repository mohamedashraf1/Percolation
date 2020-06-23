/* *****************************************************************************
 *  Name:              Mohamed Ashraf
 *  Coursera User ID:  123456
 *  Last modified:     6/18/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF obj;
    private boolean[][] grid;
    private final int num;
    private int numOfOpenSites;
    // creates n-by-n grid, with all sites initially blocked

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be bigger than 0");
        }
        grid = new boolean[n][n];
        num = n;
        numOfOpenSites = 0;
        obj = new WeightedQuickUnionUF(num * num + 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row - 1 < 0 || col - 1 < 0 || row > num || col > num)
            throw new IllegalArgumentException("the site is out of range");
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            numOfOpenSites += 1;
        }
        int current = (row - 1) * num + col;

        if (row == 1) { // if it's in the first row connect it to the virtual upper point
            obj.union(current, 0);
        }
        if (row == num) { // if it's in the bottom row connect it to the virtual lower point
            obj.union(current, num * num + 1);
        }


        int above = (row - 2) * num + col;
        int left = (row - 1) * num + (col - 1);
        int right = (row - 1) * num + (col + 1);
        int below = row * num + col;
        if (exist(row - 2, col - 1)) // above
            if (isOpen(row - 1, col))
                obj.union(current, above);
        if (exist(row - 1, col - 2)) // left
            if (isOpen(row, col - 1))
                obj.union(current, left);
        if (exist(row - 1, col)) // right
            if (isOpen(row, col + 1))
                obj.union(current, right);
        if (exist(row, col - 1)) // below
            if (isOpen(row + 1, col))
                obj.union(current, below);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row - 1 < 0 || col - 1 < 0 || row > num || col > num)
            throw new IllegalArgumentException("the site is out of range");
        return (grid[row - 1][col - 1]);
    }

    private boolean exist(int row, int col) {
        return !(row < 0 || col < 0 || row > num - 1 || col > num - 1);

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row - 1 < 0 || col - 1 < 0 || row > num || col > num)
            throw new IllegalArgumentException("the site is out of range");
        int current = (row - 1) * num + col;
        return (obj.find(current) == obj.find(0));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return (obj.find(num * num + 1) == obj.find(0));
    }

    // test client (optional)
    public static void main(String[] args) {
        // i left it empty

    }
}
