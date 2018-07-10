package tdk_enum.graph.data_structures.parallel.contexts;

import org.junit.Test;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class TdContextTest {


    Set<Set< MinimalSeparator>> concurrentSet = ConcurrentHashMap.newKeySet();
    Set set1 = new HashSet();
    Set set2 = new HashSet();
    Set set3 = new HashSet();
    Set set4 = new HashSet();
    Set set5 = new HashSet();
    Set set6 = new HashSet();
    Set set7 = new HashSet();

    @Test
    public void testConcurrentHashSet()
    {


        MinimalSeparator m1 = new MinimalSeparator();
        MinimalSeparator m2 = new MinimalSeparator();
        MinimalSeparator m3 = new MinimalSeparator();

        m1.add(new Node(1));
        m1.add(new Node(2));
        m1.add(new Node(3));

        m2.add(new Node(2));
        m2.add(new Node(3));
        m2.add(new Node(4));

        m3.add(new Node(3));
        m3.add(new Node(4));
        m3.add(new Node(5));



        set1.add(m1);
        set2.add(m2);
        set3.add(m3);

        set4.add(m1);
        set4.add(m2);

        set5.add(m1);
        set5.add(m3);

        set6.add(m2);
        set6.add(m3);

        set7.add(m1);
        set7.add(m2);
        set7.add(m3);

        concurrentSet.add(set1);
        concurrentSet.add(set2);
        concurrentSet.add(set3);

        Iterator iter = concurrentSet.iterator();
        iter.next();

        Runnable run1 = new Runnable() {
            @Override
            public void run() {

                concurrentSet.remove(set1);
                System.out.println("removed set1");
                concurrentSet.remove(set2);
                System.out.println("removed set2");
                concurrentSet.remove(set3);
                System.out.println("removed set3");
                assertEquals(0, concurrentSet.size());
            }
        };

        Thread thread1 = new Thread(run1);
        thread1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(iter.hasNext());
        while(iter.hasNext())
        {
            System.out.println("iter next is " + iter.next());
        }

        concurrentSet.add(set1);
        concurrentSet.add(set2);
        concurrentSet.add(set3);


        Callable<Void> run2 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                concurrentSet.remove(set1);
                System.out.println(Thread.currentThread().getId() + " removed set1");
                concurrentSet.remove(set2);
                System.out.println(Thread.currentThread().getId() +" removed set2");
                concurrentSet.remove(set3);
                System.out.println(Thread.currentThread().getId() + " removed set3");
                assertEquals(0, concurrentSet.size());
                return null;
            }
        };

        Callable<Void> run3 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (Object obj : concurrentSet)
                {
                    System.out.println(Thread.currentThread().getId() + " got" + obj + "set size " + concurrentSet.size());
                }
                return null;
            }
        };




        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            executorService.invokeAll(Arrays.<Callable<Void>>asList(run2, run3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();


        concurrentSet.add(set1);
        concurrentSet.add(set2);
        concurrentSet.add(set3);

        Callable<Void> run4 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                concurrentSet.add(set4);
                System.out.println(Thread.currentThread().getId() + " added set4");
                concurrentSet.add(set5);
                System.out.println(Thread.currentThread().getId() +" added set5");
                concurrentSet.add(set6);
                System.out.println(Thread.currentThread().getId() + " added set6");
                assertEquals(6, concurrentSet.size());
                return null;
            }
        };

        Callable<Void> run5 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (Object obj : concurrentSet)
                {
                    System.out.println(Thread.currentThread().getId() + " got" + obj + "set size " + concurrentSet.size());
                }
                return null;
            }
        };



        executorService = Executors.newCachedThreadPool();

        try {
            executorService.invokeAll(Arrays.<Callable<Void>>asList(run4, run5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

    }


    class TestContext extends TdContext implements Callable<Integer>
    {
        List<Integer> list = new ArrayList<>();
        AtomicBoolean interupted = new AtomicBoolean(false);
        int i = 0;


        @Override
        public Integer call() {


            System.out.println(Thread.currentThread().getId() + " start " + i);
            while(!Thread.currentThread().isInterrupted())
            {
                list.add(i);
                i++;
            }

            for (int j = 0; j < 1000000; j++)
            {

            }
            System.out.println(Thread.currentThread().getId() + " done " + i);
            return  i;
        }
    }

    @Test
    public void testFreezing()
    {
        List<Callable<Integer>> callableList = Arrays.asList(new TestContext(), new TestContext(), new TestContext());

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Integer>> futures = new ArrayList<>();

        for (Callable c : callableList)
        {
            futures.add(executorService.submit(c));
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Future f : futures)
        {

            if(!f.isDone())
            {
                f.cancel(true);
            }

            else
            {
                try {
                    System.out.println(f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Callable c :  callableList)
        {
            System.out.println(((TestContext)c).i);
        }

        futures.clear();

        for (Callable c : callableList)
        {
            futures.add(executorService.submit(c));
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Future f : futures)
        {

            if(!f.isDone())
            {
                f.cancel(true);
            }

            else
            {
                try {
                    System.out.println(f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Callable c :  callableList)
        {
            System.out.println(((TestContext)c).i);
        }


    }






}