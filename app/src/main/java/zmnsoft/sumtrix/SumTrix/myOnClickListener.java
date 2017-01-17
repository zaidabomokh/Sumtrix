package zmnsoft.sumtrix.SumTrix;

import android.view.View;
import android.widget.Toast;

public final class myOnClickListener implements View.OnClickListener {

    private final SumTrixGame sumTrixGame;
    private final myInteger[][] myBoard;

    public myOnClickListener(SumTrixGame sumTrixGame, myInteger[][] myBoard) {
        this.myBoard = myBoard;
        this.sumTrixGame = sumTrixGame;
    }

    public void onClick(View view) {
        myButton btn = (myButton) view;
        int x = btn.getMyX();
        int y = btn.getMyY();
        if(myBoard[x][y].getInteger() == -1)
            sumTrixGame.InsertNumber(x, y);
        else Toast.makeText(view.getContext(), "Can't insert number here", Toast.LENGTH_SHORT).show();

        if(sumTrixGame.isStuck()) { //TODO : finish the game when player is stuck
            Toast.makeText(view.getContext(), "You Are Stuck", Toast.LENGTH_SHORT).show();
        }
    }
}