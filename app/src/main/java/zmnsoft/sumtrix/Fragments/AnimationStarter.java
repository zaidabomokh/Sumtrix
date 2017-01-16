package zmnsoft.sumtrix.Fragments;

import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import zmnsoft.sumtrix.R;


public class AnimationStarter extends Fragment {

    private int ID;
    public AnimationStarter() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_animation_starter, container, false);
        String path = "android.resource://" + getContext().getPackageName() + "/" + R.raw.numbers;

        final VideoView vidView = (VideoView)view.findViewById(R.id.numbers);
        vidView.setVideoURI(Uri.parse(path));
        vidView.start();

        final VideoView vidView2 = (VideoView)view.findViewById(R.id.numbers2);
        vidView2.setVideoURI(Uri.parse(path));
        vidView2.start();

        final VideoView vidView3 = (VideoView)view.findViewById(R.id.numbers3);
        vidView3.setVideoURI(Uri.parse(path));
        vidView3.start();

        //TODO: WHEN THE VIDEO IS FINISHED:
        vidView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer)
            {
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();

                if(getArguments() != null) {
                    if (getArguments().getBoolean("signedIn"))
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container)).commit();

                    else
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new StagesFragment()).commit();
                }
            }
        });

        return view;
    }
}
