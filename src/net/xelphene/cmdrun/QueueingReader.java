package net.xelphene.cmdrun;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueingReader implements Runnable {

    LinkedBlockingQueue<IOMessage> mQueue;
    BufferedReader mSource;
    String mTag;

    public QueueingReader(
            LinkedBlockingQueue<IOMessage> queue,
            BufferedReader source,
            String tag
            )
    {
        mQueue = queue;
        mSource = source;
        mTag = tag;
    }

    public void run() {
        try{
            doIO();
        } catch(InterruptedException ie ) {
            // just exit
        }
    }

    public void doIO() throws InterruptedException
    {
        try {
            String line;
            while ((line = mSource.readLine()) != null) {
                mQueue.put(new IOMessage(false, mTag, line));
            }
            mQueue.put(new IOMessage(true, mTag, "eof"));
        } catch( IOException e ) {
            mQueue.put(new IOMessage(true, mTag, "ioerror"));
        }

    }
}
