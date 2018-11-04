package edu.epam.base.runner;

import edu.epam.base.entity.Driver;
import edu.epam.base.entity.ExampleThread;
import edu.epam.base.entity.Truck;
import edu.epam.base.entity.WaitingPlatform;
import edu.epam.base.example.AudioChannel;
import edu.epam.base.example.ChannelPool;
import edu.epam.base.example.Client;
import edu.epam.base.reader.TruckReader;
import edu.epam.base.transformer.TruckTransformer;


import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        Thread platform = new Thread(WaitingPlatform.getInstance());
        platform.start();

        TruckTransformer truckTransformer = new TruckTransformer();


        List<Truck> truckList = truckTransformer.createTrucks(new TruckReader().readTruck("input\\in.txt"));
        ExecutorService executor = Executors.newFixedThreadPool(6);

        for (int i = 0; i < truckList.size(); i++) {
            executor.submit(truckList.get(i));
        }

        executor.shutdown();


    }
}
