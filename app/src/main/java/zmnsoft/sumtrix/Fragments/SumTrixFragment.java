package zmnsoft.sumtrix.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import zmnsoft.sumtrix.R;
import zmnsoft.sumtrix.SumTrix.SumTrixGame;
import zmnsoft.sumtrix.SumTrix.TimeTracker;
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

        TextView explodes = (TextView) v.findViewById(R.id.explodes);

        final TextView timeTracker = (TextView) v.findViewById(R.id.timeTracker);
        final TimeTracker tracker = new TimeTracker(0);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000); // 1 second
                        if (getActivity() == null){ break; }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tracker.increment();
                                timeTracker.setText(tracker.toString());
                            }
                        });
                    }
                } catch (InterruptedException e) {}
            }
        };

        t.start();

        LinearLayout storeLayout = (LinearLayout) v.findViewById(R.id.storeLayout);
        GridLayout gridLayout = (GridLayout) v.findViewById(R.id.boardLayout);
        int size = getArguments().getInt("size");
        gridLayout.setColumnCount(size);
        gridLayout.setRowCount(size);

        myButton[][] board = new myButton[size][size];
        myButton[] store = new myButton[size];
        SumTrixGame sumTrixGame = new SumTrixGame(size, board, store, tracker);
        myButton button;
        final myInteger[][] myBoard = sumTrixGame.getMyMatrix();
        final myInteger[] myStore = sumTrixGame.getNumbersStore();
        final myOnClickListener onClickListener = new myOnClickListener(this, sumTrixGame, myBoard, size, tracker, getArguments().getString("UserName"), explodes);

        Button helpBtn = (Button) v.findViewById(R.id.help_btn);
        final Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(100);
            }
        });

        Button streakHelpBtn = (Button) v.findViewById(R.id.streakhelp_btn);
        streakHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(100);
            }
        });

        final Button mainBtn = (Button) v.findViewById(R.id.main_btn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibe.vibrate(100);
                StagesFragment stagesFragment = new StagesFragment();
                Bundle args = new Bundle();
                stagesFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, stagesFragment).commit();
            }
        });

        for(int i = 0; i < size; i++) {

            button = new myButton(getContext(), i);
            button.getBackground().setAlpha(140);
            button.setTextSize(25);
            button.setBackgroundColor(0xc8d9f4ff);
            button.setText(myStore[i].toString());
            button.setLayoutParams(new ViewGroup.LayoutParams(151, 151));
            store[i] = button;
            storeLayout.addView(button);

            if(i == 0) {
                ShapeDrawable shapedrawable = new ShapeDrawable();
                shapedrawable.getPaint().setColor(Color.RED);
                shapedrawable.getPaint().setStrokeWidth(17f);
                shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    button.setBackground(shapedrawable);
                }
            }

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
                explodes.setText(String.valueOf(sumTrixGame.getExplodes()));
                button.setOnClickListener(onClickListener);
            }
        }

        return v;
    }
}