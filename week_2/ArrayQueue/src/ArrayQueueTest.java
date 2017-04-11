import java.util.Arrays;

/**
 * Created by thomaspan on 12/18/16.
 */
public class ArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        queue.enqueue("A");
        System.out.println(Arrays.toString(queue.s));
        queue.enqueue("B");
        System.out.println(Arrays.toString(queue.s));
        queue.dequeue();
        System.out.println(Arrays.toString(queue.s));
        queue.enqueue("C");
        System.out.println(Arrays.toString(queue.s));
        queue.enqueue("D");
        System.out.println(Arrays.toString(queue.s));
        queue.dequeue();
        System.out.println(Arrays.toString(queue.s));
        queue.dequeue();
        System.out.println(Arrays.toString(queue.s));
        queue.dequeue();
        System.out.println(Arrays.toString(queue.s));
    }
}
