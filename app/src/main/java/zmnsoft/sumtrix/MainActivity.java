package zmnsoft.sumtrix;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import zmnsoft.sumtrix.Fragments.AnimationStarter;
import zmnsoft.sumtrix.Fragments.StagesFragment;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener mAuthStateListener;
    private boolean isFirst = true;

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
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

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                }
                else {
                    // TODO: 1/23/2017 intent to the log in activity
                    Toast.makeText(MainActivity.this, "Not Logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        final EditText editText = (EditText) findViewById(R.id.UserName_edt);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            void hideKeyboard(){
                View view = MainActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    if(editText.getText().length() > 5 || editText.getText().length() == 0) {
                        Toast.makeText(MainActivity.this, "Insert correct name", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    else {
                        FirebaseAuth.getInstance().signInAnonymously().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "onFailure Logged in", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(MainActivity.this, "onSuccess Logged in", Toast.LENGTH_SHORT).show();

                                String UserName = editText.getText().toString();
                                Toast.makeText(MainActivity.this, "Welcome " + UserName, Toast.LENGTH_SHORT).show();
                                StagesFragment stagesFragment = new StagesFragment();
                                Bundle args = new Bundle();
                                hideKeyboard();
                                args.putString("UserName", UserName);
                                stagesFragment.setArguments(args);
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, stagesFragment).commit();
                                editText.setVisibility(View.GONE);

                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                if(currentUser != null) {
                                    User user = new User(currentUser.getUid(), currentUser.getDisplayName());
                                }

                                saveUserToDb(UserName);
                            }
                        });

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
        getSupportFragmentManager().beginTransaction().replace(R.id.container, animationStarter).addToBackStack("animationStarter").commit();
    }


    private User getUserFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("sumtrix", MODE_PRIVATE);
        String user = prefs.getString("User", null);
        String id = prefs.getString("UserID", null);
        return new User(user, id);
    }

    private void saveUserToDb(final String userName) {

        final SharedPreferences prefs = getSharedPreferences("sumtrix", MODE_PRIVATE);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(userName,userName);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").push();
        final String key = ref.getKey();

        ref.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("User", userName);
                edit.putString("UserID", key);
                edit.commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Not Saved Error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getAsciiCode(String name) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < name.length(); i++)
            str.append((int)(name.charAt(i)));

        return Integer.parseInt(str.toString());
    }
}