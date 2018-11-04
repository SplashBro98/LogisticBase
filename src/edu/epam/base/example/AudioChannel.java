package edu.epam.base.example;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AudioChannel {
    private int channelId;

    public AudioChannel(int channelId) {
        this.channelId = channelId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void using(){
        try{
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
