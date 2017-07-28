package net.xelphene.cmdrun;

public class IOMessage {
    private String mData;
    private boolean mSpecial;
    private String mTag;

    public IOMessage(boolean special, String tag, String data) {
        mData = data;
        mSpecial = special;
        mTag = tag;
    }

    public boolean getSpecial() {
        return mSpecial;
    }

    public String getData() {
        return mData;
    }

    public String getTag() {
        return mTag;
    }
}
