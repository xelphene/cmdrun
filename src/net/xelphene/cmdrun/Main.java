package net.xelphene.cmdrun;

public class Main {

    public static void main(String[] args)
    throws java.io.IOException
    {
	    //String[] cmd = new String[] {"/me/home/bin/multiiotest"};

        if( args.length==0 ) {
            System.out.println("no argv specified in arguments");
        }

	    StdioReceiver receiver = new StdioReceiver() {
            @Override
            public void onStdoutReceived(String line) {
                System.out.println("STDOUT: "+line);
            }

            @Override
            public void onStderrReceived(String line) {
                System.out.println("STDERR: "+line);
            }
        };

        int exitStatus = CommandRunner.runCommand(args, receiver);
        System.out.println("runCommand returned");
        System.out.println("exitStatus: "+exitStatus);
    }


}
