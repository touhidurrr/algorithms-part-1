public class WeightedQuickUnion {

    private final int[] parent;
    private final int[] size;

    public WeightedQuickUnion(int n) {
        parent = new int[n];
        size = new int[n];

        for (int i = 0; i < n; ++i) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int p) {
        while (parent[p] != p) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public boolean connected(int p, int q) {
        while (parent[p] != p) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }

        while (parent[q] != q) {
            parent[q] = parent[parent[q]];
            q = parent[q];
        }

        return p == q;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP != rootQ) {
            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            } else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
        }
    }
}
