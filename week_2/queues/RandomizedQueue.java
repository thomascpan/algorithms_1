/*---------------------------------------------------------
 *  Thomas Pan
 *  01/02/2017
 *  Randomized Queues: Exercise for modeling randomized queues.
 *  To use: Instantiate RandomizedQueue.
 *---------------------------------------------------------*/

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int head = 0;
    private int tail = 0;

    /*---------------------------------------------------------
     *  Construct an empty randomized queue.
     *---------------------------------------------------------*/
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    /*---------------------------------------------------------
     *  Is the queue empty?
     *---------------------------------------------------------*/
    public boolean isEmpty() {
        return tail == head;
    }

    /*---------------------------------------------------------
     *  Return the number of items on the queue.
     *---------------------------------------------------------*/
    public int size() {
        return tail-head;
    }

    /*---------------------------------------------------------
     *  Add the item.
     *---------------------------------------------------------*/
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (tail == s.length) {
            if (head == 0) {
                resize(2 * s.length);
            } else {
                shift();
            }
        }
        s[tail++] = item;
    }

    /*---------------------------------------------------------
     *  Rremove and return a random item.
     *---------------------------------------------------------*/
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(head, tail);
        Item item = s[index];
        s[index] = s[head];
        s[head++] = null;
        if (head > 0 && head == ((s.length * 3) / 4) - 1) {
            shift();
            resize(s.length/2);
        }
        return item;
    }

    /*---------------------------------------------------------
     *  Return (but do not remove) a random item.
     *---------------------------------------------------------*/
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(head, tail);
        Item item = s[index];
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < tail; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    private void shift() {
        Item[] copy = (Item[]) new Object[s.length];
        int tempF = head;
        for (int i = 0; i < tail; i++) {
            copy[i] = s[(tempF++ + s.length) % s.length];
        }
        s = copy;
        tail = tail - head;
        head = 0;
    }

    /*---------------------------------------------------------
     *  Return an iterator over items in order from front to end.
     *---------------------------------------------------------*/
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Item[] copy;
        private int i = 0;

        public ListIterator() {
            copy = (Item[]) new Object[size()];
            for (int j = 0; j < tail; j++) {
                copy[j] = s[j+head];
            }
            StdRandom.shuffle(copy);
        }

        public boolean hasNext() {
            return i < copy.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item = copy[i++];
            if (item == null) {
                throw new NoSuchElementException();
            }
            return item;
        }
    }
}