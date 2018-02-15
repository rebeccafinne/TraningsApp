package kandidat.trainingapp;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


//testing git again
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "FB_SIGNIN";
    EditText editEmail, editPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.create_button).setOnClickListener(this);
        findViewById(R.id.sign_button).setOnClickListener(this);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        mAuth = FirebaseAuth.getInstance();

        //Firebase authListener used to check if user is or isn't signed in.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //User is signed in
                if(user != null){
                    Log.d(TAG, "User signed in " + user.getUid());
                    Intent loginIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }else{
                    Log.d(TAG,"Signed out at the moment.");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        //Connect the AuthListener
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        //Disconenct the AuthListener
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    private void signIn() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("You can not leave the Email-field empty");
            editEmail.requestFocus();
            return;
        }

        //Makes check that the entered email is a valid one.
        if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString().trim()).matches()) {
            editEmail.setError("Not a valid email");
            editEmail.requestFocus();
            return;
        }

            if (password.isEmpty()) {
                editPassword.setError("Please fill in the Password-field");
                editPassword.requestFocus();
                return;
            }

            signInToAccount(email,password);

    }

    public void signInToAccount(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent loginIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //When Sign In button is pressed
            case R.id.sign_button:
                signIn();
                break;

            // When Create account button is pressed
            case R.id.create_button:
                startActivity(new Intent(this,CreateActivity.class ));
                break;
        }
    }
}
