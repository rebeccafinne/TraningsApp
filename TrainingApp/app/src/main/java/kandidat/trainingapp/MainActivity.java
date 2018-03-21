package kandidat.trainingapp;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//testing git again
public class MainActivity extends AppCompatActivity{
    private static final int RC_SIGN_IN = 100;
    EditText editEmail, editPassword;
    private FirebaseAuth mauth;
    //private  FirebaseAuth.AuthStateListener mAuthListener;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth = FirebaseAuth.getInstance();



        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mauth.getCurrentUser() != null){
                    //startMain();
                    finish();
                }
            }
        };*/

        findViewById(R.id.sign_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AuthUI.IdpConfig> providers = new ArrayList<>();
                providers.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
                providers.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setProviders(providers)
                                .setIsSmartLockEnabled(false)
                                .build(),
                                 RC_SIGN_IN);
            }
        });

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
    }

    /*@Override
    protected void onStart(){
        super.onStart();
        mauth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mauth.getCurrentUser() != null){
            mauth.removeAuthStateListener(mAuthListener);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            handleResponse(resultCode, data);
            return;
        }
    }


    @MainThread
    private void handleResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        Toast toast;

        // Successfully signed in
        if (resultCode == ResultCodes.OK) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("users");
            UserInformation userSigningIn = new UserInformation(
                    mauth.getCurrentUser().getUid(),
                    mauth.getCurrentUser().getDisplayName(),
                    mauth.getCurrentUser().getEmail()
            );

            myRef.child(mauth.getUid()).setValue(userSigningIn);
            startActivity(AppMainActivity.createIntent(this, response));
            finish();
            return;

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

            toast = Toast.makeText(this, "Unknown Error!", Toast.LENGTH_LONG);
            toast.show();
        }


    }

    public static Intent createIntent(Context context) {
        Intent in = new Intent();
        in.setClass(context, MainActivity.class);
        return in;
    }





    public void startMain() {
        startActivity(AppMainActivity.createIntent(this));
        finish();
        return;
    }
 }

