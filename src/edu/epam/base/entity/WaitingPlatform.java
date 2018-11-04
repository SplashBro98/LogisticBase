package edu.epam.base.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class WaitingPlatform implements Runnable {

    private static Logger logger = LogManager.getLogger();
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
        logger.log(Level.INFO,truck.toString() + "зарегистрировался на платформе");
    }

    @Override
    public void run() {
        Terminal terminal;
        boolean flag = false;
        try {
            while (!flag) {
                while (!truckDeque.isEmpty()) {
                    flag = true;
                    terminal = LogisticBase.getInstance().takeTerminal();
                    Truck truck = truckDeque.pollFirst();
                    truck.setTerminal(terminal);
                    truck.getState().set(true);
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
