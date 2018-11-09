package edu.epam.base.util;

public class IdGenerator {
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
