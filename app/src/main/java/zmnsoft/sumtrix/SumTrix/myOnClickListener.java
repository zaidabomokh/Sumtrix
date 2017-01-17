package zmnsoft.sumtrix.SumTrix;

import android.view.View;
import android.widget.Toast;

import zmnsoft.sumtrix.Fragments.StagesFragment;
import zmnsoft.sumtrix.Fragments.SumTrixFragment;
import zmnsoft.sumtrix.Fragments.TopTenBoard;
import zmnsoft.sumtrix.R;

public final class myOnClickListener implements View.OnClickListener {

    private final SumTrixGame sumTrixGame;
    private final myInteger[][] myBoard;
    private final SumTrixFragment sumTrixFragment;

    public myOnClickListener(SumTrixFragment sumTrixFragment, SumTrixGame sumTrixGame, myInteger[][] myBoard) {
        this.myBoard = myBoard;
        this.sumTrixGame = sumTrixGame;
        this.sumTrixFragment = sumTrixFragment;
    }

    public void onClick(View view) {
        myButton btn = (myButton) view;
        int x = btn.getMyX();
        int y = btn.getMyY();
        if(myBoard[x][y].getInteger() == -1) {
            if(sumTrixGame.InsertNumber(x, y)) { //TODO : finish the game when player is Finished
                Toast.makeText(view.getContext(), "CONGRATULATIONS", Toast.LENGTH_LONG).show();
                sumTrixFragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new TopTenBoard()).commit();
            }
        }
        else Toast.makeText(view.getContext(), "Can't insert number here", Toast.LENGTH_SHORT).show();

        if(sumTrixGame.isStuck()) { //TODO : finish the game when player is stuck
            Toast.makeText(view.getContext(), "You Are Stuck", Toast.LENGTH_SHORT).show();
            sumTrixFragment.getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(sumTrixFragment.getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();
        }
    }
}