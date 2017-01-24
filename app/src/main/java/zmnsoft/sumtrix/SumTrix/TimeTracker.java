package zmnsoft.sumtrix.SumTrix;

/**
 * Created by Zaid on 1/23/2017.
 */
public class TimeTracker {

    private int start;

    public int getStart() {
        return start;
    }

    public TimeTracker(int start) {
        this.start = start;
    }

    public void increment() {
        start ++;
    }

    @Override
    public String toString() {
        return String.valueOf(start);
    }
}
