package edu.epam.base.transformer;

import edu.epam.base.entity.Driver;
import edu.epam.base.entity.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TruckTransformer {
    public static final int DRIVER_NAME_POSITION = 0;
    public static final int DRIVER_SURNAME_POSITION = 1;
    public static final String SPLIT_REGEX = " ";
    private static Logger logger = LogManager.getLogger();

    public List<Truck> createTrucks(List<String> input){
        List<Truck> result = new ArrayList<>();
        for(String string : input){
            String[] array = string.split(SPLIT_REGEX);
            Driver driver = new Driver(array[DRIVER_NAME_POSITION], array[DRIVER_SURNAME_POSITION]);
            String product = array[2];
            boolean perishable = Boolean.parseBoolean(array[3]);
            Truck truck = new Truck(product, driver, perishable);
            result.add(truck);
        }
        return result;
    }

}
