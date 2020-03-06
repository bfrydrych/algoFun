import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;

    private int size = 0;

    private class Node {
        public Item item;
        public Node nextNode;
        public Node prevNode;

        public String toString() {
            return item.toString();
        }
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        ++size;
        Node node = new Node();
        node.item = item;
        node.nextNode = head;
        if (head != null) {
            head.prevNode = node;
        }
        head = node;



        if (size == 1) {
           tail = node;
           //tail.prevNode = head;
        }

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        ++size;
        Node node = new Node();
        node.item = item;
        node.prevNode = tail;
        if (tail != null) {
            tail.nextNode = node;
        }
        tail = node;

        if (size == 1) {
            head = node;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        --size;
        Item toReturn = head.item;
        head = head.nextNode;
        if (head == null) {
            tail = null;
        } else {
            head.prevNode = null;
        }
        return toReturn;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        --size;
        Item toReturn = tail.item;
        tail = tail.prevNode;
        if (tail != null) {
            tail.nextNode = null;
        } else {
            head = null;
        }
        return toReturn;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node element = head;

            @Override
            public boolean hasNext() {
                return element != null;
            }

            @Override
            public Item next() {
                if (element == null) {
                    throw new NoSuchElementException();
                }
                Item toReturn = element.item;
                element = element.nextNode;
                return toReturn;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.println(deque.isEmpty());
        iterate(deque);
        deque.addFirst(1);
        System.out.println(deque.isEmpty());
        iterate(deque);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.removeFirst();
        iterate(deque);
        deque.addFirst(5);
        iterate(deque);
        deque.removeLast();
        iterate(deque);
        deque.removeLast();
        iterate(deque);
        deque.addFirst(6);
        deque.addFirst(7);
        iterate(deque);
        deque.removeLast();
        iterate(deque);
        deque.addLast(3);
        iterate(deque);
        deque.addLast(2);
        iterate(deque);

        deque.removeFirst();

        deque.removeFirst();
        System.out.println(deque.size());
        deque.iterator().remove();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        iterate(deque);
        System.out.println(deque.isEmpty());
        deque.removeFirst();






        /*
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        iterate(deque);
        System.out.println(deque.isEmpty());
        deque.removeLast();

         */
    }
    private static void iterate(Deque<Integer> deque) {
        System.out.println("Iterating");
        Iterator<Integer> iter = deque.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println();
    }
}
