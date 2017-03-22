package postit.communication;

import postit.communication.*;

/**
 * Created by dog on 3/8/2017.
 */
public class ServerApp {
    public static void main(String[] args){
        int rePort = 2048;
        int outPort = 4880;

        ServerSender processor = new ServerSender(4880);
        ServerReceiver receiver = new ServerReceiver(2048, processor);
        Thread t1 = new Thread(processor);
        Thread t2 = new Thread(receiver);

        t1.start();
        t2.start();

    }
}