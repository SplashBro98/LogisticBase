package edu.epam.base.reader;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TruckReader {
    private static Logger logger = LogManager.getLogger();
    private static final String PATH = "input\\in.txt";

    public List<String> readTruck(String filepath){
        List<String> result;
        FileReader fileReader;
        try{
            File file = new File(filepath);
            if(file.exists()){
                fileReader = new FileReader(filepath);
            }
            else{
                fileReader = new FileReader(PATH);
            }
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Stream<String> stream = bufferedReader.lines();
            result = stream.collect(Collectors.toList());
            return result;

        }catch (IOException e){
            logger.log(Level.FATAL,"Wrong filepath: " + filepath, e);
            throw new RuntimeException(e);
        }
    }

}
