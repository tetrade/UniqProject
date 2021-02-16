package main;

public class StrIntPair {
    private String str;
    private int value;

    public StrIntPair(String str, int value) {
        this.str = str;
        this.value = value;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public StrIntPair incValue(){
        return new StrIntPair(str, value + 1);
    }
}
