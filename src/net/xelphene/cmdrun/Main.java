package net.xelphene.cmdrun;

import java.io.IOException;

public class Main {

    public static void main(String[] args)
    throws java.io.IOException
    {
        StringBuffer allStdout = new StringBuffer();
        StringBuffer allStderr = new StringBuffer();

        if( args.length==0 ) {
            System.out.println("no argv specified in arguments");
            return;
        }

        final int[] exit = new int[1];

	    StdioReceiver receiver = new StdioReceiver() {
            @Override
            public void onStdoutReceived(char[] data) {
                allStdout.append(data);
                String s = new String(data);
                System.out.println("STDOUT: "+data.length+" : "+s);
            }

            @Override
            public void onStderrReceived(char[] data) {
                allStderr.append(data);
                String s = new String(data);
                System.out.println("STDERR: "+data.length+" : "+s);
            }

            @Override
            public void onExit(int exitStatus) {
                System.out.println("exit status: "+exitStatus);
                exit[0]= exitStatus;
            }

            @Override
            public void onException(IOException e) {
                System.out.println("IO ERROR: "+e);
            }
        };

        CommandRunner.runCommand(args, receiver);
        System.out.println("runCommand returned");
        System.out.println("---stdout---");
        System.out.println(allStdout);
        System.out.println("---stderr---");
        System.out.println(allStderr);
        System.out.println("---");
        System.out.println("exit status: "+exit[0]);
    }
}
