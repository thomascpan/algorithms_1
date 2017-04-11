import edu.princeton.cs.algs4.Queue;

public class RedBlackTree<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private boolean color;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.color = RED;
        }
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val);

        int cmp = key.compareTo(x.key);
        if (cmp == 0) x.val = val;
        else if (cmp < 0) x.left = put(x.left, key, val);
        else x.right = put(x.right, key, val);

        if (isRed(x.right)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);

        return x;
    }

    public Value get(Key key) {
        Node x = root;

        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    public Value get(Node h, Key key) {
        Node x = h;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    public Value min() {
        Node x = root;
        while (x != null) {
            if (x.left == null) return x.val;
            else x = x.left;
        }
        return null;
    }

    private Node min(Node x) {
        Node node = x;
        while (node != null) {
            if (node.left == null) return node;
            else node = node.left;
        }
        return null;
    }

    public Value max() {
        Node x = root;
        while (x != null) {
            if (x.right == null) return x.val;
            else x = x.right;
        }
        return null;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);

        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);

        Node t = ceiling(x.left, key);
        if (t != null) return t;
        else return x;
    }

//    public int rank(Key key) {
//        return rank(root, key);
//    }

//    private int rank(Node x, Key key) {
//        if (x == null) return 0;
//        int cmp = key.compareTo(x.key);
//        if (cmp < 0) return rank(x.left, key);
//        if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
//        else return size(x.left);
//    }

    public void delete(Key key) {
        root = delete(root, key);
        root.color = BLACK;
    }

    public Node delete(Node h, Key key) {
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) h = rotateRight(h);
            if (cmp == 0 && h.right == null) return null;
            if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h);
            if (cmp == 0) {
                h.val = get(h.right, min(h.right).key);
                h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return fixUp(h);
    }

    public void deleteMax() {
        root = deleteMax(root);
        root.color = BLACK;
    }

    public Node deleteMax(Node h) {
        if (isRed(h.left)) h = rotateRight(h);
        if (h.right == null) return null;
        if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h);
        h.right = deleteMax(h.right);
        return fixUp(h);
    }

    public void deleteMin() {
        root = deleteMin(root);
        root.color = BLACK;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) return null;
        if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return fixUp(h);
    }

    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    public Iterable<Key> iterator() {
        Queue<Key> q = new Queue<>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node fixUp(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        return h;
    }

    public static void main(String[] args) {
        RedBlackTree<String, Integer> rbt = new RedBlackTree<>();
        rbt.put("S", 1);
        rbt.put("X", 2);
        rbt.put("E", 3);
        rbt.put("A", 4);
        rbt.put("R", 5);
        rbt.put("C", 6);
        rbt.put("H", 7);
        rbt.put("G", 8);
        rbt.put("M", 9);

        for (String k : rbt.iterator()) {
            System.out.println(k);
        }
        System.out.println("");

        rbt.delete("X");

        for (String k : rbt.iterator()) {
            System.out.println(k);
        }
    }
}