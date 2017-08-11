package net.xelphene.cmdrun;

import net.xelphene.cmdrun.message.Message;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandRunner {
    public static void runCommand(String[] argv, StdioReceiver receiver)
    throws java.io.IOException
    {
        Process proc = Runtime.getRuntime().exec(argv);
        OutputStream out = proc.getOutputStream();
        out.close();
        InputStream stdout = proc.getInputStream();
        InputStream stderr = proc.getErrorStream();

        Reader stdoutReader = new InputStreamReader(stdout);
        Reader stderrReader = new InputStreamReader(stderr);

        LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();

        Thread stdoutThread = new Thread(new QueueingReader(
                queue, stdoutReader, "stdout"));
        Thread stderrThread = new Thread(new QueueingReader(
                queue, stderrReader, "stderr"));

        stdoutThread.start();
        stderrThread.start();
        try {
            ioLoop(queue, receiver);
        } catch( InterruptedException e ) {

        }

        receiver.onExit(proc.exitValue());
    }

    private static void ioLoop(LinkedBlockingQueue<Message> queue, StdioReceiver receiver)
            throws InterruptedException
    {
        boolean stderrThreadRunning = true;
        boolean stdoutThreadRunning = true;

        while(true) {
            Message message = queue.take();

            if( message.isIo() ) {
                if(message.getTag().equals("stdout")) {
                    receiver.onStdoutReceived(message.getData());
                }
                if(message.getTag().equals("stderr")) {
                    receiver.onStderrReceived(message.getData());
                }
            }

            if( message.isEof() || message.isError() ) {
                if( message.getTag().equals("stdout") ) {
                    stdoutThreadRunning=false;
                }
                if( message.getTag().equals("stderr") ) {
                    stderrThreadRunning=false;
                }
            }

            if( message.isError() ) {
                receiver.onException(message.getException());
            }

            if( (!stderrThreadRunning) && (!stdoutThreadRunning)) {
                return;
            }
        }
    }
}
