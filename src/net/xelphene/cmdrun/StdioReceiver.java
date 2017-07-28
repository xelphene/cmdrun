package net.xelphene.cmdrun;

public interface StdioReceiver {
    void onStdoutReceived(String line);
    void onStderrReceived(String line);
}
