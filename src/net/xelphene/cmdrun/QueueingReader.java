package net.xelphene.cmdrun;

import net.xelphene.cmdrun.message.EOFMessage;
import net.xelphene.cmdrun.message.ErrorMessage;
import net.xelphene.cmdrun.message.IOMessage;
import net.xelphene.cmdrun.message.Message;

import java.io.Reader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

class QueueingReader implements Runnable {

    private static final int DEFAULT_BUFFER_SIZE=4096;

    private LinkedBlockingQueue<Message> mQueue;
    private Reader reader;
    private String tag;
    private char[] buf;

    QueueingReader(
            LinkedBlockingQueue<Message> queue,
            Reader sourceReader,
            String messageTag
            )
    {
        mQueue = queue;
        reader = sourceReader;
        tag = messageTag;
        buf = new char[DEFAULT_BUFFER_SIZE];
    }

    public void run() {
        try{
            doIO();
        } catch(InterruptedException ie ) {
            // just exit. nothing to clean up.
        }
    }

    private void doIO() throws InterruptedException
    {
        int numRead;
        try {

            do {
                numRead = reader.read(buf, 0, buf.length);
                if( numRead>=1 ) {
                    char[] data = java.util.Arrays.copyOf(buf, numRead);
                    mQueue.put(new IOMessage(tag, data));
                }
            } while( numRead>=0 );

            mQueue.put( new EOFMessage(tag));
        } catch( IOException e ) {
            mQueue.put(new ErrorMessage(tag, e));
        }

    }
}
