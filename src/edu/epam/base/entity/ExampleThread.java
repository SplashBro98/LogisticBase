package edu.epam.base.entity;

import java.util.concurrent.TimeUnit;

public class ExampleThread extends Thread {

    @Override
    public void run() {
        try{
            if(isDaemon()){
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println("This is daemon");
            }else {
                System.out.println("This is Sparta");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            if(!isDaemon()){
                System.out.println("Finish Sparta");
            }else {
                System.out.println("Finish daemon");
            }
        }
    }
}
