import java.util.Iterator;

public class Main {

    public static void main(String[] args) {

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

       /* Percolation per = new Percolation(3);
        per.open(1, 1);
        per.open(3, 3);
        per.open(2, 1);
        per.open(3, 1);
        System.out.println(per.isOpen(1, 1));
        System.out.println(per.isFull(1, 1));
        System.out.println(per.numberOfOpenSites());

        System.out.println(per.isOpen(3, 3));
        System.out.println(per.isFull(3, 3));
        System.out.println(per.numberOfOpenSites());

        System.out.println(per.isOpen(2, 1));
        System.out.println(per.isFull(2, 1));
        System.out.println(per.numberOfOpenSites());

        System.out.println("Percolates: " + per.percolates());

        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
        */

        //new PercolationStats(3, 2);


/*
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
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        System.out.println(deque.size());
        iterate(deque);
        System.out.println(deque.isEmpty());
        deque.removeFirst();
*/
/*
        To Test
        Deque<Integer> deque = new Deque<Integer>()
         deque.addFirst(1)
         deque.removeLast()      ==> 1
         deque.isEmpty()         ==> false


         Deque<Integer> deque = new Deque<Integer>()
         deque.isEmpty()         ==> true
         deque.addFirst(2)
         deque.removeLast()      ==> 2
         deque.addFirst(4)
         deque.isEmpty()         ==> false
         deque.removeLast()      ==> 4
         deque.addFirst(7)
         deque.removeLast()      ==> 7
         deque.addFirst(9)
         deque.removeLast()      ==> 9
         deque.isEmpty()         ==> false




         Deque<Integer> deque = new Deque<Integer>()
         deque.addFirst(1)
         deque.addFirst(2)
         deque.removeLast()      ==> 1
         deque.removeLast()      ==> 2
         deque.addFirst(5)
         deque.removeLast()      ==> 5
         deque.isEmpty()         ==> false



         Deque<Integer> deque = new Deque<Integer>()
         deque.addLast(1)
         deque.removeFirst()     ==> 1
         deque.addLast(3)
         deque.isEmpty()         ==> false
         deque.removeFirst()     ==> 3
         deque.addLast(6)
         deque.removeFirst()     ==> 6
         deque.addLast(8)
         deque.removeFirst()     ==> 8
         deque.isEmpty()         ==> false
*/


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

    private static void iterate(RandomizedQueue<Integer> deque) {
        System.out.println("Iterating");
        Iterator<Integer> iter = deque.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println();
    }
}
