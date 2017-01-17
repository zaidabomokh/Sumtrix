package zmnsoft.sumtrix.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zmnsoft.sumtrix.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopTenBoard extends Fragment {

    List<TextView> textViews;

    public TopTenBoard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top_ten_board, container, false);

        View decorView = getActivity().getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        final Animation animAlpha = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_scale_fast);
        Button restartBtn = (Button) v.findViewById(R.id.res_btn);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                animAlpha.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        SumTrixFragment sumTrixFragment = new SumTrixFragment();
                        Bundle args = new Bundle();
                        args.putInt("size", getArguments().getInt("size"));
                        sumTrixFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, sumTrixFragment).commit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        Button stagesBtn = (Button) v.findViewById(R.id.stages_btn);
        stagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });

        LinearLayout myLinearLayout = (LinearLayout) v.findViewById(R.id.textViews_Layout);
        textViews = new ArrayList<TextView>();

        TextView newText = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        newText.setLayoutParams(params);
        newText.setTextSize(21);
        newText.setTextColor(Color.MAGENTA);
        newText.setTypeface(Typeface.MONOSPACE, Typeface.BOLD_ITALIC);
        newText.setText(String.format("Name: %10s Score:", "") + "\n");
        myLinearLayout.addView(newText);
        textViews.add(newText);

        for (int i = 0; i < 10; i++)
        {
            newText = new TextView(getContext());
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            newText.setLayoutParams(params);
            newText.setTextSize(21);
            newText.setTextColor(Color.CYAN);
            newText.setTypeface(Typeface.MONOSPACE, Typeface.BOLD_ITALIC);
            newText.setText(String.format("%s%16s", "Zaid", "456"));
            myLinearLayout.addView(newText);
            textViews.add(newText);
        }

        return v;
    }
}
