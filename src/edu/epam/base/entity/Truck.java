package edu.epam.base.entity;

import edu.epam.base.util.IdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Truck implements Callable<String> {
    private static Logger logger = LogManager.getLogger(Truck.class);

    private long truckId;
    private String product;
    private boolean perishable;
    private Driver driver;
    private AtomicReference<Terminal> terminal;


    public Truck(String product, Driver driver, boolean perishable) {
        this.product = product;
        this.driver = driver;
        this.perishable = perishable;
        this.truckId = IdGenerator.getTruckCounter();
        this.terminal = new AtomicReference<>(null);
    }



    public Terminal getTerminal() {
        return terminal.get();
    }

    public void setTerminal(AtomicReference<Terminal> terminal) {
        this.terminal = terminal;
    }

    @Override
    public String call() {
        try {
            WaitingPlatform.getInstance().addTruck(this);
            while (true) {
                if (terminal.get() != null) {
                    logger.log(Level.INFO, this.toString() + "попал на терминал № " + terminal.get().getTerminalId() + "\n");
                    this.register(terminal.get());
                    logger.log(Level.INFO, this.toString() + "покинул терминал № " + terminal.get().getTerminalId() + "\n");
                    LogisticBase.getInstance().leaveTerminal(terminal.get());
                    break;
                }
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error with " + this.toString());
            Thread.currentThread().interrupt();
        }
        return this.toString();
    }

    public void register(Terminal terminal) {
        logger.log(Level.INFO, this.toString() + "проходит проверку на терминале № " + terminal.getTerminalId() + "\n");
        try {
            //имитация регистрации
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, this.toString() + "can`t register" + e);
            Thread.currentThread().interrupt();
        }
    }

    public boolean isPerishable() {
        return perishable;
    }

    public long getTruckId() {
        return truckId;
    }

    @Override
    public String toString() {
        return "Truck: " + driver.toString() +
                " Product: " + product + " " + perishable + "\n";
    }
}
