/*---------------------------------------------------------
 *  Thomas Pan
 *  01/02/2017
 *  Deques: Exercise for modeling deques.
 *  To use: Instantiate Deque.
 *---------------------------------------------------------*/

import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size = 0;

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    /*---------------------------------------------------------
     *  Instantiates Deque Object.
     *---------------------------------------------------------*/
    public Deque() {

    }

    /*---------------------------------------------------------
     *  Is the deque empty?
     *---------------------------------------------------------*/
    public boolean isEmpty() {
        return size == 0;
    }

    /*---------------------------------------------------------
     *  Return the number of items on the deque.
     *---------------------------------------------------------*/
    public int size() {
        return size;
    }

    /*---------------------------------------------------------
     *  Add the item to the front.
     *---------------------------------------------------------*/
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.previous = first;
        }
        size++;
    }

    /*---------------------------------------------------------
     *  Add the item to the end.
     *---------------------------------------------------------*/
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    /*---------------------------------------------------------
     *  Remove and return the item from the front.
     *---------------------------------------------------------*/
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;

        Node oldFirst = first;
        first = first.next;
        if (first != null) {
            first.previous = null;
        }

        oldFirst.item = null;
        oldFirst.next = null;

        size--;
        if (isEmpty()) {
            Node oldLast = last;
            oldLast.item = null;
            oldLast.previous = null;
            last = null;
        }
        return item;
    }

    /*---------------------------------------------------------
     *  Remove and return the item from the end.
     *---------------------------------------------------------*/
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;

        Node oldLast = last;
        last = last.previous;
        if (last != null) {
            last.next = null;
        }

        oldLast.item = null;
        oldLast.previous = null;

        size--;
        if (isEmpty()) {
            Node oldFirst = first;
            oldFirst.item = null;
            oldFirst.next = null;
            first = null;
        }
        return item;
    }

    /*---------------------------------------------------------
     *  Return an iterator over items in order from front to end.
     *---------------------------------------------------------*/
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
//        Deque<Integer> deque = new Deque<>();
//
//        deque.addFirst(1);
//        deque.addFirst(2);
//        deque.addFirst(3);
//        deque.addLast(4);
//        deque.addFirst(5);
//        deque.addFirst(6);
//        deque.addLast(7);
//
//        for (Integer in : deque) {
//            System.out.println(in);
//        }
//
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeFirst());
//        System.out.println(deque.removeFirst());
    }
}