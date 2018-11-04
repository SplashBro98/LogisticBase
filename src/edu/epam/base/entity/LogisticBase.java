package edu.epam.base.entity;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
    private static LogisticBase instance;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    public static final AtomicInteger QUEUE_MAX_SIZE = new AtomicInteger(3);
    private AtomicInteger counter = new AtomicInteger(0);
    private Semaphore semaphore = new Semaphore(QUEUE_MAX_SIZE.get(), true);
    private Queue<Terminal> terminals = new ConcurrentLinkedDeque<>();


    private LogisticBase() {
        init();
    }

    private void init(){
        for (int i = 0; i < QUEUE_MAX_SIZE.get(); i++) {
            terminals.add(new Terminal());
        }
    }


    public Terminal takeTerminal() {
        try {
            semaphore.acquire();
            Terminal current = terminals.poll();
            return current;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void leaveTerminal(Terminal terminal) {
        terminals.offer(terminal);
        this.counter.set(counter.get() + 1);
        //System.out.println("Counter = " + counter + "\n");
        semaphore.release();
    }

    public AtomicInteger getCounter() {
        return counter;
    }


    public static LogisticBase getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new LogisticBase();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instance != null) {
            throw new CloneNotSupportedException();
        }
        return super.clone();
    }
}
