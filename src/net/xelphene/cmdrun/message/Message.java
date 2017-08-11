package net.xelphene.cmdrun.message;

import java.io.IOException;

public abstract class Message {
    public abstract String getTag();
    public abstract char[] getData();
    public abstract IOException getException();

    public abstract boolean isIo();
    public abstract boolean isEof();
    public abstract boolean isError();

}
