package it.unisa.razzolo.model;

public class Point
{
    private final int i;
    private final int j;
    private final char value;
    private final int index;

    public Point(int i, int j, char c)
    {
        this.i = i;
        this.j = j;
        this.value = c;
        this.index = 0;
    }

    public Point(int i, int j)
    {
        this.i = i;
        this.j = j;
        this.value = 0;
        this.index = 0;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public char getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}