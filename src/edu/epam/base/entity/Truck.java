package edu.epam.base.entity;

import edu.epam.base.util.IdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Truck implements Callable<String> {

    private long truckId;
    private String product;
    private boolean perishable;
    private Driver driver;
    private AtomicBoolean state;
    private Terminal terminal;
    private static Logger logger = LogManager.getLogger();


    public Truck(String product, Driver driver, boolean perishable) {
        this.product = product;
        this.driver = driver;
        this.perishable = perishable;
        this.truckId = IdGenerator.getTruckCounter();
        this.state = new AtomicBoolean(false);
        this.terminal = null;
    }

    public AtomicBoolean getState() {
        return state;
    }

    public void setState(AtomicBoolean state) {
        this.state = state;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public String call() {
        try {
            WaitingPlatform.getInstance().addTruck(this);
            while (true) {
                if (state.get() && terminal != null) {
                    logger.log(Level.INFO, this.toString() + "попал на терминал № " + terminal.getTerminalId() + "\n");
                    this.registate(terminal);
                    logger.log(Level.INFO, this.toString() + "покинул терминал № " + terminal.getTerminalId() + "\n");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.toString();
    }

    public void registate(Terminal terminal) {
        logger.log(Level.INFO, this.toString() + "проходит проверку на терминале № " + terminal.getTerminalId() + "\n");
        try {
            TimeUnit.SECONDS.sleep(5);
            LogisticBase.getInstance().leaveTerminal(terminal);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
