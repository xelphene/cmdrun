package net.xelphene.cmdrun;

import java.io.IOException;

public interface StdioReceiver {
    void onStdoutReceived(char[] data);
    void onStderrReceived(char[] data);
    void onExit(int exitStatus);
    void onException(IOException exception);
}
