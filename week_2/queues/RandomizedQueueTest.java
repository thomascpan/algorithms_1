public class RandomizedQueueTest {
    public static void main(String[] args) {
        // Initialize
        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        // isEmpty()
        System.out.println(queue.isEmpty() == true);

        // size()
        System.out.println(queue.size() == 0);

        // enqueue()
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        System.out.println(queue.size() == 3);

        // sample()
        System.out.println(queue.sample().getClass() == String.class);
        System.out.println(queue.size() == 3);

        // dequeue()
        queue.dequeue();
        queue.dequeue();
        System.out.println(queue.size() == 1);

        // iterator
        for (String s : queue) {
            System.out.println(s);
        }
    }
}