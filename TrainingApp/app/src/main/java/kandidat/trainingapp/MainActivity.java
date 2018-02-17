package kandidat.trainingapp;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//testing git again
public class MainActivity extends AppCompatActivity{
    private final String TAG = "FB_SIGNIN";
    private static final int RC_SIGN_IN = 100;
    EditText editEmail, editPassword;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        // Keeps track of if the user was signed in or not when the application was closed
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //If the user state is signed in show profile page.
                if(user != null){
                    Log.d(TAG, "User signed in " + user.getUid());
                    Intent loginIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                    finish();
                }else{
                    Log.d(TAG,"Signed out at the moment.");
                }
            }
        };

        findViewById(R.id.sign_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
                selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
                selectedProviders.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setProviders(selectedProviders)
                                .setIsSmartLockEnabled(true)
                                .build(),
                        RC_SIGN_IN);
            }
        });

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            handleSignInResponse(resultCode, data);
            return;
        }
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

    @MainThread
    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        Toast toast;

        // Successfully signed in
        if (resultCode == ResultCodes.OK) {

            startProfileIntent(response);


        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                toast = Toast.makeText(this, "Sign in was cancelled!", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                toast = Toast.makeText(this, "You have no internet connection", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                toast = Toast.makeText(this, "Unknown Error!", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        }
        toast = Toast.makeText(this, "Unknown Error!", Toast.LENGTH_LONG);
        toast.show();
    }

    private void startProfileIntent(IdpResponse response) {
        startActivity(ProfileActivity.createIntent(this, response ));
    }

    public static Intent createIntent(Context context) {
        Intent in = new Intent();
        in.setClass(context, MainActivity.class);
        return in;
    }
 }

