import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final byte OPEN = 1; // 0b001
    private static final byte TOP = 1 << 1; // 0b010
    private static final byte BOTTOM = 1 << 2; // 0b100
    private static final byte FULL = OPEN | TOP;
    private static final byte PERCOLATING = OPEN | TOP | BOTTOM;
    private final int n;
    private final WeightedQuickUnionUF wQuickUnion;
    private final byte[] status;
    private int openSites;
    private boolean isPercolating;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException();

        this.n = n;
        int size = flatten(n, n) + 1;
        this.status = new byte[size];
        this.wQuickUnion = new WeightedQuickUnionUF(size);

        for (int i = 1; i <= this.n; ++i) {
            status[flatten(1, i)] = TOP;
            status[flatten(n, i)] |= BOTTOM;
        }
    }

    private int flatten(int x, int y) {
        return (x - 1) * this.n + y - 1;
    }

    private boolean isOutsideRange(int x, int y) {
        return (x < 1) || (y < 1) || (x > this.n) || (y > this.n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (this.isOpen(row, col)) return;

        openSites++;
        int pos = flatten(row, col);
        this.status[pos] |= 1;
        byte curStatus = this.status[pos];

        if ((row - 1 > 0) && ((this.status[pos - n] & OPEN) != 0)) { // up
            curStatus |= this.status[this.wQuickUnion.find(pos - n)];
            this.wQuickUnion.union(pos, pos - n);
        }
        if ((row + 1 <= this.n) && ((this.status[pos + n] & OPEN) != 0)) { // down
            curStatus |= this.status[this.wQuickUnion.find(pos + n)];
            this.wQuickUnion.union(pos, pos + n);
        }
        if ((col - 1 > 0) && ((this.status[pos - 1] & OPEN) != 0)) { // left
            curStatus |= this.status[this.wQuickUnion.find(pos - 1)];
            this.wQuickUnion.union(pos, pos - 1);
        }
        if ((col + 1 <= this.n) && ((this.status[pos + 1] & OPEN) != 0)) { // right
            curStatus |= this.status[this.wQuickUnion.find(pos + 1)];
            this.wQuickUnion.union(pos, pos + 1);
        }

        int root = this.wQuickUnion.find(pos);
        this.status[root] |= curStatus;
        isPercolating |= this.status[root] == PERCOLATING;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isOutsideRange(row, col)) throw new IllegalArgumentException();

        return (this.status[flatten(row, col)] & OPEN) != 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOutsideRange(row, col)) throw new IllegalArgumentException();

        return (this.status[this.wQuickUnion.find(flatten(row, col))] & FULL) == FULL;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolating;
    }
}
