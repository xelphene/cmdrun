package net.xelphene.cmdrun.message;

import java.io.IOException;

public class EOFMessage extends Message {
    private String mTag;

    public EOFMessage(String tag) {
        mTag = tag;
    }

    public String getTag() { return mTag; }
    public char[] getData() { return new char[0]; }
    public IOException getException() { return null; }

    public boolean isIo() { return false ; }
    public boolean isEof() { return true; }
    public boolean isError() { return false; }

}
