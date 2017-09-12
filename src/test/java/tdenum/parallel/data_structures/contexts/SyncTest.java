package tdenum.parallel.data_structures.contexts;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncTest {

    @Test
    public void testNotifyOutsideSync()
    {
        Object mutex = new Object();

        Callable<Void> c1 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Thread.sleep(2000);

                synchronized (mutex)
                {
                    System.out.println("Thread " + Thread.currentThread().getId()  + " notifyng");
                    mutex.notify();
                }

                System.out.println("Thread " + Thread.currentThread().getId()  + " done");
                return null;

            }
        };

        Callable<Void> c2 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                synchronized (mutex)
                {
                    System.out.println("Thread " + Thread.currentThread().getId()  + " waiting");
                    mutex.wait();
                }

                System.out.println("Thread " + Thread.currentThread().getId()  + " done");
                return null;
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            executorService.invokeAll(Arrays.asList(c1, c2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
