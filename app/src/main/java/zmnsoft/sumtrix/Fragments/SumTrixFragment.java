package zmnsoft.sumtrix.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import zmnsoft.sumtrix.R;
import zmnsoft.sumtrix.SumTrix.SumTrixGame;
import zmnsoft.sumtrix.SumTrix.myInteger;



/**
 * A simple {@link Fragment} subclass.
 */
public class SumTrixFragment extends Fragment {

    private Button[][] board;
    private Button[] store;
    private SumTrixGame sumTrixGame;

    public SumTrixFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sum_trix, container, false);

        LinearLayout storeLayout = (LinearLayout) v.findViewById(R.id.storeLayout);
        GridLayout gridLayout = (GridLayout) v.findViewById(R.id.boardLayout);
        //int size = getArguments().getInt("size");
        int size = 7;
        sumTrixGame = new SumTrixGame(size);
        gridLayout.setColumnCount(size);
        gridLayout.setRowCount(size);

        board = new Button[size][size];
        store = new Button[size];
        Button button;
        myInteger[][] myBoard = sumTrixGame.getMyMatrix();
        myInteger[] myStore = sumTrixGame.getNumbersStore();

        for(int i = 0; i < size; i++) {

            button = new Button(getContext());
            button.getBackground().setAlpha(140);
            button.setTextSize(25);
            button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            button.setText(myStore[i].toString());
            store[i] = button;
            button.setLayoutParams(new ViewGroup.LayoutParams(151, 151));
            storeLayout.addView(button);

            for(int j = 0; j < size; j++) {

                button = new Button(getContext());
                button.getBackground().setAlpha(140);
                button.setTextSize(25);
                button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));

                if(myBoard[i][j].getInteger() == -1) {
                    button.setText(" ");
                    button.setBackgroundColor(Color.BLACK);
                }
                else button.setText(myBoard[i][j].toString());

                button.setLayoutParams(new ViewGroup.LayoutParams(151, 151));
                board[i][j] = button;
                gridLayout.addView(button);
            }
        }

        return v;
    }
}
