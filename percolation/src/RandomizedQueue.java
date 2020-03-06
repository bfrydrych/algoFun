import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] elements = (Item[]) new Object[20];
    private int nextIndex = 0;
    // construct an empty randomized queue

    public RandomizedQueue() {

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return nextIndex == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return nextIndex;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (nextIndex == elements.length) {
            Item[] newElements = (Item[]) new Object[elements.length + (int)(elements.length * 0.5)];
            System.arraycopy(elements, 0, newElements, 0, nextIndex);
            elements = newElements;
        }
        elements[nextIndex++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int indxToRemove = StdRandom.uniform(nextIndex);
        --nextIndex;
        Item toReturn = elements[indxToRemove];
        for (int i = indxToRemove; i < nextIndex; ++i) {
            elements[i] = elements[i + 1];
        }
        elements[nextIndex] = null;
        return toReturn;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements[StdRandom.uniform(nextIndex)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        RandomizedQueue<Item> queue = new RandomizedQueue<>();
        queue.elements = (Item[]) new Object[nextIndex];
        queue.nextIndex = nextIndex;
        System.arraycopy(elements, 0, queue.elements, 0, nextIndex);

        return new Iterator<Item>() {
            RandomizedQueue<Item> iter = queue;

            @Override
            public boolean hasNext() {
                return !iter.isEmpty();
            }

            @Override
            public Item next() {
                if (iter.isEmpty()) {
                    throw new NoSuchElementException();
                }
                return iter.dequeue();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);

        System.out.println(queue.size());
        System.out.println(queue.sample());
        System.out.println(queue.isEmpty());
        queue.dequeue();
        iterate(queue);
        queue.enqueue(5);
        iterate(queue);

        System.out.println();
        System.out.println("---------Testing resizng-------------");
        RandomizedQueue<Integer> queueResize = new RandomizedQueue<>();
        for (int i = 1; i <= 22; ++i) {
            queueResize.enqueue(i);
        }

        System.out.println(queueResize.size());
        System.out.println(queueResize.sample());
        System.out.println(queueResize.isEmpty());
        System.out.println("Dequeing: " + queueResize.dequeue());
        iterate(queueResize);
        queueResize.enqueue(23);
        iterate(queueResize);
        System.out.println("Size: " + queueResize.size() + " Expected: " + 22);
        for (int i = 1; i <= 22; ++i) {
            queueResize.dequeue();
        }
        System.out.println("Size: " + queueResize.size() + " Expected: " + 0);
        System.out.println(queueResize.isEmpty());
        queueResize.dequeue();
    }

    private static void iterate(RandomizedQueue<Integer> deque) {
        System.out.println("Iterating");
        Iterator<Integer> iter = deque.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println();
    }
}
