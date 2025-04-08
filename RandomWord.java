import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int words = 0;
        String champion = null;

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();

            if (StdRandom.bernoulli(1.0 / ++words)) {
                champion = word;
            }
        }

        StdOut.println(champion);
    }
}
