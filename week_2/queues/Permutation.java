/*---------------------------------------------------------
 *  Thomas Pan
 *  01/02/2017
 *  Permutation: Exercise for modeling permutations.
 *  To use: Instantiate RandomizedQueue.
 *---------------------------------------------------------*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        String[] input = StdIn.readAllStrings();
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        for (String s : input) {
            queue.enqueue(s);
        }
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            StdOut.println(queue.dequeue());
        }
    }
}