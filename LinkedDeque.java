import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Deque implementation using a linked list.
public class LinkedDeque<Item> implements Iterable<Item> {
    //CODE HERE
    private int n; // numbers of elements
    private Node head;
    private Node tail;

    // Helper doubly-linked list class.
    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item item) {
            this.item = item;
        }
    }

    // Construct an empty deque.
    public LinkedDeque() {
        //CODE HERE
        this.n = 0;
        this.head = null;
        this.tail = null;
    }

    // Is the dequeue empty?
    public boolean isEmpty() {
        //CODE HERE
        return head == null;
    }

    // The number of items on the deque.
    public int size() {
        //CODE HERE
        return n;
    }

    // Add item to the front of the deque.
    public void addFirst(Item item) {
        //CODE HERE
        Node newNode = new Node(item);

        if (item == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            this.head = newNode;
        }
        n++; // increment the size
    }

    // Add item to the end of the deque.
    public void addLast(Item item) {
        //CODE HERE
        Node newNode = new Node(item);

        if (item == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            this.tail = newNode;
        }
        n++;
    }

    // Remove and return item from the front of the deque.
    public Item removeFirst() {
        //CODE HERE
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        Item item = head.item;

        head = head.next;           // head = right node now
        if (head == null) {
            tail = null;            // the deque is empty now
        } else {
            head.prev = null;
        }
        n--;                        // decrease the size
        return item;
    }

    // Remove and return item from the end of the deque.
    public Item removeLast() {
        //CODE HERE
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        Item item = tail.item;

        tail = tail.prev;           // tail = left node now
        if (tail == null) {
            head = null;            // the deque is empty now
        } else {
            tail.next = null;
        }
        n--;
        return item;
    }

    // An iterator over items in the queue in order from front to end.
    public Iterator<Item> iterator() {
        //CODE HERE
        return new DequeIterator();
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class DequeIterator implements Iterator<Item> {
        //CODE HERE
        private Node current;

        DequeIterator() {
            //CODE HERE
            this.current = head;
        }

        public boolean hasNext() {
            return current != null; // Returns true if the iteration has more elements.
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            //CODE HERE
            if (!hasNext()) {
                throw new NoSuchElementException();     // no more items to return
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // A string representation of the deque.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its "
                + "several powers, having been originally breathed into a few "
                + "forms or into one; and that, whilst this planet has gone "
                + "cycling on according to the fixed law of gravity, from so "
                + "simple a beginning endless forms most beautiful and most "
                + "wonderful have been, and are being, evolved. ~ "
                + "Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.println(deque.isEmpty());
        StdOut.printf("(%d characters) ", deque.size());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        StdOut.println(deque.isEmpty());
    }
}
