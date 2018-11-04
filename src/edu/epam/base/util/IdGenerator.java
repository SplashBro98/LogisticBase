package edu.epam.base.util;

public class IdGenerator {
    private static final long MAX_TERMINAL_ID = 100000;
    private static final long MAX_TRUCK_ID = 100000;
    private static long truckCounter = 1;
    private static long terminalCounter = 1;

   public static long getTruckCounter(){
       return truckCounter++;
   }

   public static long getTerminalCounter(){
       return terminalCounter++;
   }

    private IdGenerator() {
    }
}
