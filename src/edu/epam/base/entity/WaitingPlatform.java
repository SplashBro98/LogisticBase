package edu.epam.base.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class WaitingPlatform implements Runnable {

    private static Logger logger = LogManager.getLogger(WaitingPlatform.class);
    private static WaitingPlatform instance;
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private ConcurrentLinkedDeque<Truck> truckDeque;

    private WaitingPlatform() {
        truckDeque = new ConcurrentLinkedDeque<>();
    }

    public ConcurrentLinkedDeque<Truck> getTruckDeque() {
        return truckDeque;
    }


    public void addTruck(Truck truck) {
        if (truck.isPerishable()) {
            truckDeque.addFirst(truck);
        } else {
            truckDeque.addLast(truck);
        }
        logger.log(Level.INFO, truck.toString() + "зарегистрировался на платформе");
    }

    @Override
    public void run() {
        Terminal terminal;
        boolean flag = false;
        while (!flag) {
            while (!truckDeque.isEmpty()) {
                flag = true;
                System.out.println("SIZE: " + truckDeque.size());
                terminal = LogisticBase.getInstance().takeTerminal();
                Truck truck = truckDeque.pollFirst();
                truck.setTerminal(new AtomicReference<>(terminal));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    logger.log(Level.FATAL, "Waiting platform is not working" + e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static WaitingPlatform getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new WaitingPlatform();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
}
