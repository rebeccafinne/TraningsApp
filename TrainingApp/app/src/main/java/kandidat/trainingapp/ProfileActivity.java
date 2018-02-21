package kandidat.trainingapp;

import android.content.Context;
import android.content.Intent;
import android.icu.util.TimeZone;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity{
    private final String TAG = "FB_PROFILE";

    private TextView welcomeText;
    private Button logInButton;
    private Button skipButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //A reference to Authentication in Firebase
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //If there is no user signed in then it should return to mainActivity
        if(mAuth.getCurrentUser() == null){
            startMainIntent();

        }

        createWelcome( FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()),mAuth);

        findViewById(R.id.logOut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        welcomeText = (TextView) findViewById(R.id.profile_text);
        skipButton.findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });



    }

    private void startMainIntent() {
        startActivity(MainActivity.createIntent(this));
        finish();
        return;
    }

    public static Intent createIntent(Context context, IdpResponse idpResponse) {
        Intent in = IdpResponse.getIntent(idpResponse);
        in.setClass(context, ProfileActivity.class);
        return in;
    }

    // Create customised welcomemessage with username
    public void createWelcome(DatabaseReference userRef, final FirebaseAuth mAuth){

       userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                //Capitalize username
               String userDisplayCap = mAuth.getCurrentUser().getDisplayName().substring(0,1).toUpperCase() +
                        mAuth.getCurrentUser().getDisplayName().substring(1);
                welcomeText.setText("Welcome " + userDisplayCap + "!");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void signOut(){
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(MainActivity.createIntent(ProfileActivity.this));
                            finish();
                        } else {
                            // Signout failed
                        }
                    }
                });
    }


}
