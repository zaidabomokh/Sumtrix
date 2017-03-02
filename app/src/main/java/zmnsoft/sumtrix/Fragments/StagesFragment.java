package zmnsoft.sumtrix.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import zmnsoft.sumtrix.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StagesFragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private SharedPreferences sharedPreferences;

    public StagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stages, container, false);
        View layout = v.findViewById(R.id.layout);
        layout.setOnClickListener(this);

        View decorView = getActivity().getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        hideKeyboard();

        sharedPreferences = getActivity().getSharedPreferences("USER_NAME" , Context.MODE_PRIVATE);
        textView = (TextView) v.findViewById(R.id.welcome_textView);
        textView.setText("Welcome " + sharedPreferences.getString("USER_NAME", " "));

        final SumTrixFragment sumTrixFragment = new SumTrixFragment();
        final Bundle args = new Bundle();

        final Animation animAlpha = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_alpha);
        Button btn1 = (Button) v.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                animAlpha.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        args.putInt("size", 3);
                        sumTrixFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, sumTrixFragment).addToBackStack("sumTrix").commit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        final Animation animRotate = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_rotate);
        Button btn2 = (Button) v.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animRotate);
                animRotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        args.putInt("size", 4);
                        sumTrixFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, sumTrixFragment).addToBackStack("sumTrix").commit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        final Animation animScale = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_scale);
        Button btn3 = (Button) v.findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);
                animScale.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        args.putInt("size", 5);
                        sumTrixFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, sumTrixFragment).addToBackStack("sumTrix").commit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        final Animation animTranslate = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_translate);
        Button btn4 = (Button) v.findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animTranslate);
                animTranslate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        args.putInt("size", 6);
                        sumTrixFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, sumTrixFragment).addToBackStack("sumTrix").commit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        final Animation animComplex = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_alpha);
        Button btn5 = (Button) v.findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationSet sets = new AnimationSet(false);
                sets.addAnimation(animAlpha);
                sets.addAnimation(animScale);
                sets.addAnimation(animRotate);
                view.startAnimation(sets);
                animAlpha.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        args.putInt("size", 7);
                        sumTrixFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, sumTrixFragment).addToBackStack("sumTrix").commit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        return v;
    }

    private void hideKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        hideKeyboard();
    }
}