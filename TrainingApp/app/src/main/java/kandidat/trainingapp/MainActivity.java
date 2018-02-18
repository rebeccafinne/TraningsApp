package kandidat.trainingapp;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
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
    private final String TAG = "FB_SIGNIN";
    private static final int RC_SIGN_IN = 100;
    EditText editEmail, editPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
            getSignInResponse(resultCode, data);
            return;
        }
    }


    @MainThread
    private void getSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);
        Toast toast;

        // Successfully signed in
        if (resultCode == ResultCodes.OK) {
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference userRef = database.getReference("users");
            UserInformation theUser = new UserInformation(
                    mAuth.getCurrentUser().getUid(),
                    mAuth.getCurrentUser().getDisplayName(),
                    mAuth.getCurrentUser().getEmail());
            userRef.child(mAuth.getCurrentUser().getUid()).setValue(theUser);

            startActivity(ProfileActivity.createIntent(this, response));
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
 }

