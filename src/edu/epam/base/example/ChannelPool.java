package edu.epam.base.example;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ChannelPool<T> {
    private static final int POOL_SIZE = 5;
    private Semaphore semaphore = new Semaphore(POOL_SIZE, true);
    private Queue<T> resources = new ArrayDeque<>();

    public ChannelPool(Queue<T> resources) {
        this.resources.addAll(resources);
    }

    public T getResource() {
        try {
            if (semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
                T res = resources.poll();
                return res;
            }
        } catch (InterruptedException e){
           e.printStackTrace();
        }
        throw new RuntimeException("Превышено время ожидания");
    }

    public void returnResource(T res){
        resources.offer(res);
        semaphore.release();
    }

}
