package edu.epam.base.example;

public class Client extends Thread {
    private boolean reading;
    private ChannelPool<AudioChannel> pool;
    private int clientId;


    public Client(ChannelPool<AudioChannel> pool, int clientId) {
        this.pool = pool;
        this.clientId = clientId;
    }

    @Override
    public void run() {
       AudioChannel channel = null;
       try{
           channel = pool.getResource();
           reading = true;
           System.out.println("Client #" + this.getClientId() + "took Channel #" + channel.getChannelId());
           channel.using();
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           if(channel != null){
               reading = false;
               System.out.println("Client #" + this.clientId + "is finished work with Channel #" + channel.getChannelId());
               pool.returnResource(channel);
           }
       }
    }

    public int getClientId() {
        return clientId;
    }
}
