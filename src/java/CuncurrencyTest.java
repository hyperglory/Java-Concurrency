/**
 * @author hyperglory
 * @date 2017/1/11 16:06
 */
public class CuncurrencyTest {

    private static final long COUNT = 100000001l;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    public static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int a = 0;
            for (long i = 0; i < COUNT; i++) {
                a += 5;
            }
        });
        thread.start();
        int b = 0;
        for (long i = 0; i < COUNT; i++) {
            b--;
        }
        long stop = System.currentTimeMillis();
        thread.join();
        System.out.println(stop - start);
    }

    public static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < COUNT; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < COUNT; i++) {
            b--;
        }
        long stop = System.currentTimeMillis();
        System.out.println(stop - start);
    }
}
