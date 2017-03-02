package zmnsoft.sumtrix.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.Serializable;
import zmnsoft.sumtrix.Fragments.AnimationStarter;
import zmnsoft.sumtrix.Fragments.StagesFragment;
import zmnsoft.sumtrix.Models.AnimationListener;
import zmnsoft.sumtrix.Models.User;
import zmnsoft.sumtrix.R;

public class MainActivity extends AppCompatActivity implements AnimationListener {

    private SharedPreferences sharedPreferences;
    private String userName;
    private boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimationStarter animationStarter = new AnimationStarter();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, animationStarter).addToBackStack("animationStarter").commit();

        View decorView = getWindow().getDecorView();

        //init field:
        sharedPreferences = getSharedPreferences("USER_NAME" , Context.MODE_PRIVATE);
        userName = loadData();

        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();
    }

    public void onAnimationFinishListener()
    {
        final EditText editText = (EditText) findViewById(R.id.UserName_edt);

        if(userName.equals(" ")){
            Button LoginBtn = (Button) findViewById(R.id.LoginBtn);
            LoginBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String UserName = editText.getText().toString();
                    if(UserName != null && UserName.length() <= 5 && UserName.length() > 0) {
                        hideKeyboard();
                        saveData(UserName);
                        Toast.makeText(MainActivity.this, "Welcome " + UserName, Toast.LENGTH_SHORT).show();
                        isFirst = true;
                        StagesFragment stagesFragment = new StagesFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, stagesFragment).commit();
                        editText.setVisibility(View.GONE);
                    }
                }
            });
        }

        else {
            hideKeyboard();
            StagesFragment stagesFragment = new StagesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, stagesFragment).commit();
            editText.setVisibility(View.GONE);
        }
    }

    public void hideKeyboard() {
        View view = MainActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private String loadData() {
        String data = sharedPreferences.getString("USER_NAME", " ");
        return data;
    }

    private void showNickNameDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("USER INFO");
        alertDialog.setMessage("Enter User Name");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        userName = input.getText().toString();

                        Toast.makeText(MainActivity.this, "Welcome, " + userName, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        alertDialog.show();
    }

    private void saveData(String data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_NAME", data);
        editor.commit();
    }

    /*private User getUserFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("sumtrix", MODE_PRIVATE);
        String user = prefs.getString("User", null);
        String id = prefs.getString("UserID", null);
        return new User(user, id);
    }

    private void saveUserToDb(final String userName) {

        final SharedPreferences prefs = getSharedPreferences("sumtrix", MODE_PRIVATE);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(userName, userName);
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
    }*/
}