package tdk_enum.graph.independent_set.parallel.nested;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TaskManager {
    AtomicInteger doneThreads = new AtomicInteger(0);

    int threadNumber = Runtime.getRuntime().availableProcessors();

    boolean allDone = false;

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }


    void setAllDone(boolean allDone) {
        this.allDone = allDone;
    }

    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    Lock readLock = rwl.readLock();
    Lock writeLock = rwl.writeLock();
    Condition waiting = writeLock.newCondition();

    Set<Long> waitingList = ConcurrentHashMap.newKeySet();

    void signal() {
        readLock.lock();
        if (waitingList.size() > 0) {
            readLock.unlock();
            try {
                writeLock.lock();
                waiting.signalAll();
                readLock.lock();
            } finally {
                writeLock.unlock();
            }
        }
        readLock.unlock();
    }


    void done() {
        writeLock.lock();
        try {
            if (waitingList.size() == threadNumber - 1) {
                setAllDone(true);
                waiting.signalAll();
            } else {
                waitingList.add(Thread.currentThread().getId());
                waiting.await();
                waitingList.remove(Thread.currentThread().getId());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
