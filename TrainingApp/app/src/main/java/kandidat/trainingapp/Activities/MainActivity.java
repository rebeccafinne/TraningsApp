package kandidat.trainingapp.Activities;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kandidat.trainingapp.Repositories.UserInformation;
import kandidat.trainingapp.R;

//testing git again
public class MainActivity extends AppCompatActivity{
    private static final int RC_SIGN_IN = 100;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth mauth = FirebaseAuth.getInstance();


        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
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

        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPassword = findViewById(R.id.editPassword);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.the_main_menu, menu);


        return true;
    }
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
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference userRef = db.getReference("users");
            DatabaseReference putUserRef= db.getReference("users").child(user.getUid());
            DatabaseReference putYourselfRef = db.getReference("friends").child(user.getUid()).child(user.getUid());


            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(user.getUid())) {
                        //Create and add the user to database.
                        String UID = user.getUid();
                        String email = user.getEmail();
                        String displayName = user.getDisplayName();


                        UserInformation theUser = new UserInformation(UID, displayName, email);
                        //theUser.getFriends().add(UID);
                        putUserRef.setValue(theUser);
                        putYourselfRef.setValue("myself");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

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

