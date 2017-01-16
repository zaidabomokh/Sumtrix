package zmnsoft.sumtrix.SumTrix;

/**
 * Created by Zaid on 1/12/2017.
 */

public class myInteger {

    private int integer;
    private int x;
    private int y;

    public myInteger(int integer, int x, int y)
    {
        this.integer = integer;
        this.x = x;
        this.y = y;
    }

    public myInteger(int integer)
    {
        this.integer = integer;
        this.x = -1;
        this.y = -1;
    }

    public void increement()
    {
        integer ++;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    @Override
    public String toString() {
        return "" + integer;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

