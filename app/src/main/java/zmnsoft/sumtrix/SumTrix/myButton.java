package zmnsoft.sumtrix.SumTrix;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Zaid on 1/17/2017.
 */

public final class myButton extends Button {

    private int x;
    private int y;

    public myButton(Context context, int x) {
        super(context);
        this.x = x;
        this.y = -1;
    }

    public myButton(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
    }

    public int getMyX() {
        return x;
    }

    public int getMyY() {
        return y;
    }
}
