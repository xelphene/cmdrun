package net.xelphene.cmdrun.message;

import java.io.IOException;

public class ErrorMessage extends Message {
    private String mTag;
    private IOException mException;

    public ErrorMessage(String tag, IOException exception) {
        mTag = tag;
        mException = exception;
    }

    public String getTag() { return mTag; }
    public char[] getData() { return new char[0]; }
    public IOException getException() { return mException; }

    public boolean isIo() { return false ; }
    public boolean isEof() { return false; }
    public boolean isError() { return true; }

}
