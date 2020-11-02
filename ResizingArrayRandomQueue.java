import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Random queue implementation using a resizing array.
public class ResizingArrayRandomQueue<Item> implements Iterable<Item> {
    private static final int MIN = 2;           // min capacity
    private int N;                              // size
    @SuppressWarnings("unchecked")
    private Item[] q = (Item[]) new Object[MIN];

    // Construct an empty queue.
    public ResizingArrayRandomQueue() {
        N = 0;
    }

    // Is the queue empty?
    public boolean isEmpty() {
        //CODE HERE
        return N == 0;
    }

    // The number of items on the queue.
    public int size() {
        //CODE HERE
        return N;
    }

    // Add item to the queue.
    public void enqueue(Item item) {
        //CODE HERE
        if (item == null) {
            throw new NullPointerException();
        }
        if (N == q.length) {                // if the array is full
            resize(2 * q.length);           // double the array size to store new items
        }
        q[N] = item;                        // store item into the end of array
        N++;                                // increment N by 1
    }

    // Remove and return a random item from the queue.
    public Item dequeue() {
        //CODE HERE
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        int randomIndex = StdRandom.uniform(0, N);  // random integer from interval [0,N)
        Item returnItem = q[randomIndex];           // assign the random value as the returnItem
        q[randomIndex] = q[N - 1];
        q[N - 1] = null;
        if (N > 0 && N == q.length / 4) {            // resize it by half if it is at quarter capacity
            resize(q.length / 2);
        }
        N--;
        return returnItem;
    }

    // Return a random item from the queue, but do not remove it.
    public Item sample() {
        //CODE HERE
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        int randomIndex = StdRandom.uniform(0, N);
        return q[randomIndex];
    }

    // An independent iterator over items in the queue in random order.
    public Iterator<Item> iterator() {
        //CODE HERE
        return new RandomQueueIterator();
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {
        //CODE HERE
        private int current;
        @SuppressWarnings("unchecked")
        private Item[] items = (Item[]) new Object[N];

        RandomQueueIterator() {
            //CODE HERE
            for (int i = 0; i < N; i++) {
                items[i] = q[i];                // get items from the original array
            }
            StdRandom.shuffle(items);
            current = 0;                        // the current points at index 0
        }

        public boolean hasNext() {
            //CODE HERE
            return current < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            //CODE HERE
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[current++];
        }
    }

    // A string representation of the queue.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Helper method for resizing the underlying array.
    private void resize(int max) {
        @SuppressWarnings("unchecked")
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            if (q[i] != null) {
                temp[i] = q[i];
            }
        }
        q = temp;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q =
                new ResizingArrayRandomQueue<Integer>();
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readInt());
        }
        int sum1 = 0;
        for (int x : q) {
            sum1 += x;
        }
        int sum2 = sum1;
        for (int x : q) {
            sum2 -= x;
        }
        int sum3 = 0;
        while (q.size() > 0) {
            sum3 += q.dequeue();
        }
        StdOut.println(sum1);
        StdOut.println(sum2);
        StdOut.println(sum3);
        StdOut.println(q.isEmpty());
    }
}
