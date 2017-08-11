package net.xelphene.cmdrun.message;

import java.io.IOException;

public class IOMessage extends Message {
    private char[] mData;
    private String mTag;

    public IOMessage(String tag, char[] data) {
        mData = data;
        mTag = tag;
    }

    public IOMessage(String tag, String data) {
        mData = data.toCharArray();
        mTag = tag;
    }
    public String getTag() { return mTag; }
    public char[] getData() {
        return mData;
    }
    public IOException getException() { return null; }

    public boolean isIo() { return true; }
    public boolean isEof() { return false; }
    public boolean isError() { return false; }
}
