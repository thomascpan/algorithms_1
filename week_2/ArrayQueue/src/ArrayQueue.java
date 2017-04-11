/**
 * Created by thomaspan on 12/18/16.
 */
public class ArrayQueue {
    public String[] s;
    private int head = 0;
    private int tail = 0;

    public ArrayQueue() {
        s = new String[1];
    }

    public boolean isEmpty() {
        return tail == 0;
    }

    public void enqueue(String item) {
        if (tail == s.length) {
            if (head == 0) {
                resize(2 * s.length);
            } else {
                shift();
            }
        }
        s[tail++] = item;
    }

    public String dequeue() {
        String item = s[head];
        s[head++] = null;
        if (head > 0 && head == ((s.length * 3) / 4) - 1){
            shift();
            resize(s.length/2);
        }
        return item;
    }

    private void resize(int capacity) {
        String [] copy = new String[capacity];
        for (int i = 0; i < tail; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    private void shift() {
        String [] copy = new String[s.length];
        int tempF = head;
        for (int i = 0; i < tail; i++) {
            copy[i] = s[(tempF++ + s.length) % s.length];
        }
        s = copy;
        tail = tail - head;
        head = 0;
    }
}

