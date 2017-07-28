package net.xelphene.cmdrun;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandRunner {
    public static int runCommand(String[] argv, StdioReceiver receiver)
    throws java.io.IOException
    {
        Process proc = Runtime.getRuntime().exec(argv);
        OutputStream out = proc.getOutputStream();
        out.close();
        InputStream stdout = proc.getInputStream();
        InputStream stderr = proc.getErrorStream();

        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
        BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));

        LinkedBlockingQueue<IOMessage> queue = new LinkedBlockingQueue<>();

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

        return proc.exitValue();
    }

    private static void ioLoop(LinkedBlockingQueue<IOMessage> queue, StdioReceiver receiver)
            throws InterruptedException
    {
        boolean stderrThreadRunning = true;
        boolean stdoutThreadRunning = true;

        while(true) {
            IOMessage message = queue.take();
            if( message.getSpecial() ) {
                if( message.getTag().equals("stdout") ) {
                    stdoutThreadRunning=false;
                }
                if( message.getTag().equals("stderr") ) {
                    stderrThreadRunning=false;
                }
            } else {
                if(message.getTag().equals("stdout")) {
                    receiver.onStdoutReceived(message.getData());
                }
                if(message.getTag().equals("stderr")) {
                    receiver.onStderrReceived(message.getData());
                }
            }
            if( (!stderrThreadRunning) && (!stdoutThreadRunning)) {
                return;
            }
        }
    }
}
