package zmnsoft.sumtrix;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import zmnsoft.sumtrix.Fragments.AnimationStarter;
import zmnsoft.sumtrix.Fragments.StagesFragment;
import zmnsoft.sumtrix.Fragments.SumTrixFragment;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "zaid";
    //private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean isFirst = true;

    @Override
    protected void onResume() {
        super.onResume();
        //getSupportActionBar().show();
        //FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();

        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 if (firebaseAuth.getCurrentUser()!=null && isFirst)    {
                     init();
                     isFirst = false;
                 }
                else if (firebaseAuth.getCurrentUser() == null){
                     FirebaseAuth.getInstance().signInAnonymously();
                 }
            }
        };*/

        final EditText editText = (EditText) findViewById(R.id.UserName_edt);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    if(editText.getText().length() > 5 || editText.getText().length() == 0) {
                        Toast.makeText(MainActivity.this, "Insert correct name", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    else {
                        String UserName = editText.getText().toString();
                        Toast.makeText(MainActivity.this, "Welcome " + UserName, Toast.LENGTH_SHORT).show();
                        StagesFragment stagesFragment = new StagesFragment();
                        Bundle args = new Bundle();
                        args.putString("UserName", UserName);
                        stagesFragment.setArguments(args);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, stagesFragment).commit();
                        return true;
                    }
                }
                return false;
            }
        });

        AnimationStarter animationStarter = new AnimationStarter();
        Bundle args = new Bundle();

        //  Put the fragment Acronyms' name converted from letters to Ascii
        int ID = getAsciiCode("AS");
        boolean signedIn = true;

        args.putInt("ID", ID);
        args.putBoolean("signedIn", signedIn);

        animationStarter.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, animationStarter).commit();
    }

    private int getAsciiCode(String name)
    {
        StringBuilder str = new StringBuilder();

        for (int i=0; i<name.length(); i++)
            str.append((int)(name.charAt(i)));

        return Integer.parseInt(str.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
    }

    private void init() {

        User user = new User("zaid");
        FirebaseDatabase.getInstance().getReference().child("Users").setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(TAG, task.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
        Log.i(TAG, "adding user is done!");
    }
}