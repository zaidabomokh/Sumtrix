package zmnsoft.sumtrix.SumTrix;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import zmnsoft.sumtrix.Fragments.StagesFragment;
import zmnsoft.sumtrix.Fragments.SumTrixFragment;
import zmnsoft.sumtrix.Fragments.TopTenBoard;
import zmnsoft.sumtrix.R;

public final class myOnClickListener implements View.OnClickListener {

    private final SumTrixGame sumTrixGame;
    private final myInteger[][] myBoard;
    private final SumTrixFragment sumTrixFragment;
    private final int size;
    private final TextView explodes;
    private TimeTracker tracker;
    private String UserName;

    public myOnClickListener(SumTrixFragment sumTrixFragment, SumTrixGame sumTrixGame, myInteger[][] myBoard, int size, TimeTracker tracker, String UserName, TextView explodes) {
        this.myBoard = myBoard;
        this.sumTrixGame = sumTrixGame;
        this.sumTrixFragment = sumTrixFragment;
        this.size = size;
        this.tracker = tracker;
        this.UserName = UserName;
        this.explodes = explodes;
    }

    public void onClick(View view) {

        myButton btn = (myButton) view;
        int x = btn.getMyX();
        int y = btn.getMyY();

        if(myBoard[x][y].getInteger() == -1) {
            if(sumTrixGame.InsertNumber(x, y)) { //TODO : finish the game when player is Finished
                Toast.makeText(view.getContext(), "CONGRATULATIONS", Toast.LENGTH_LONG).show();
                Bundle args = new Bundle();
                args.putInt("size", size);
                args.putInt("time", tracker.getStart());
                args.putString("UserName", UserName);
                TopTenBoard topTenBoard = new TopTenBoard();
                topTenBoard.setArguments(args);
                sumTrixFragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, topTenBoard).commit();
            }
        }
        else Toast.makeText(view.getContext(), "Can't insert number here", Toast.LENGTH_SHORT).show();

        if(sumTrixGame.isStuck()) { //TODO : finish the game when player is stuck
            Toast.makeText(view.getContext(), "You Are Stuck", Toast.LENGTH_SHORT).show();
            StagesFragment stagesFragment = new StagesFragment();
            Bundle args = new Bundle();
            args.putString("UserName", sumTrixFragment.getArguments().getString("UserName"));
            stagesFragment.setArguments(args);
            sumTrixFragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, stagesFragment).commit();
        }

        explodes.setText(String.valueOf(sumTrixGame.getExplodes()));
    }
}