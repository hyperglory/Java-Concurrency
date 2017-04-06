import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hyperglory
 * @date 05/03/2017 16:02
 *
 * a Counter comparing between a
 * Thread-safe method @safeCount() based on CAS
 * and a non Thread-safe method @count()
 */
public class Counter {

    private int i = 0;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        Counter counter = new Counter();
        ArrayList<Thread> threads = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    counter.count();
                    counter.safeCount();
                }
            });
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        // wait all threads finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(counter.i);
        System.out.println(counter.atomicInteger.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    public void count() {
        i++;
    }

    public void safeCount() {
        for (; ; ) {
            int i = atomicInteger.get();
            boolean suc = atomicInteger.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }

    }
}
