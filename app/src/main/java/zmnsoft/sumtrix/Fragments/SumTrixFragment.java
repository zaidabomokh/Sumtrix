package zmnsoft.sumtrix.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import zmnsoft.sumtrix.R;
import zmnsoft.sumtrix.SumTrix.SumTrixGame;
import zmnsoft.sumtrix.SumTrix.myButton;
import zmnsoft.sumtrix.SumTrix.myInteger;
import zmnsoft.sumtrix.SumTrix.myOnClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SumTrixFragment extends Fragment {

    public SumTrixFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sum_trix, container, false);

        View decorView = getActivity().getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        LinearLayout storeLayout = (LinearLayout) v.findViewById(R.id.storeLayout);
        GridLayout gridLayout = (GridLayout) v.findViewById(R.id.boardLayout);
        int size = getArguments().getInt("size");
        //int size = 7;
        gridLayout.setColumnCount(size);
        gridLayout.setRowCount(size);

        myButton[][] board = new myButton[size][size];
        myButton[] store = new myButton[size];
        SumTrixGame sumTrixGame = new SumTrixGame(size, board, store);
        myButton button;
        final myInteger[][] myBoard = sumTrixGame.getMyMatrix();
        final myInteger[] myStore = sumTrixGame.getNumbersStore();
        final myOnClickListener onClickListener = new myOnClickListener(this, sumTrixGame, myBoard);

        for(int i = 0; i < size; i++) {

            button = new myButton(getContext(), i);
            button.getBackground().setAlpha(140);
            button.setTextSize(25);
            button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            button.setText(myStore[i].toString());
            button.setLayoutParams(new ViewGroup.LayoutParams(151, 151));
            store[i] = button;
            storeLayout.addView(button);

            for(int j = 0; j < size; j++) {

                button = new myButton(getContext(), i, j);
                button.getBackground().setAlpha(140);
                button.setTextSize(25);
                button.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));

                if(myBoard[i][j].getInteger() == -1)
                    button.setText(" ");
                else button.setText(myBoard[i][j].toString());

                button.setLayoutParams(new ViewGroup.LayoutParams(151, 151));
                board[i][j] = button;
                gridLayout.addView(button);

                button.setOnClickListener(onClickListener);
            }
        }

        return v;
    }
}