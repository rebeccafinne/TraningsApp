package kandidat.trainingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kandidat.trainingapp.Activities.MainActivity;
import kandidat.trainingapp.Repositories.UserInformation;

//This class isn't used at the moment and don't think it will be

public class CreateActivity extends AppCompatActivity{
    private final String TAG = "FB_CREATE";
    private EditText editEmail, editPassword, editUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        final DatabaseReference mrefUsers = FirebaseDatabase.getInstance().getReference("users");;
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //Listener for create account button
        findViewById(R.id.create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(mrefUsers,mAuth);
            }
        });

        //Listener to when user already have account
        findViewById(R.id.have_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateActivity.this,MainActivity.class ));
            }
        });

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editUsername = (EditText) findViewById(R.id.editUsername);
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    public void registerUser(DatabaseReference mrefUser,FirebaseAuth mauth){

        //Makes the check that user typed in something in email-field.
        if(editEmail.getText().toString().trim().isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }
        //Makes check that the entered email is a valid one.
        if(!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString().trim()).matches()){
            editEmail.setError("Not a valid email");
            editEmail.requestFocus();
            return;
        }
        //Username will only be used as the displayable name
        if(editUsername.getText().toString().trim().isEmpty()){
            editUsername.setError("Please, choose a username of your liking.");
            editUsername.requestFocus();
            return;
        }

        //Makes check that user typed in something in password-field.
        if(editPassword.getText().toString().trim().isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        //Makes check that the typed password is at least 6 characters long.
        if (editPassword.getText().toString().trim().length() < 6){
            editPassword.setError("Password should contain at least 6 characthers");
            editPassword.requestFocus();
            return;
        }

        //If all checks is ok then create the account.
        createAccount(editEmail.getText().toString().trim(),editPassword.getText().toString().trim(),mrefUser,mauth);
    }


    public void createAccount(final String email, final String password, final DatabaseReference mrefUser, final FirebaseAuth mAuth){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // if the user was put in correctly in database
                if(task.isSuccessful()){
                    addUsertoDatabase(mrefUser,mAuth);
                    signIn(email,password,mAuth);

                // generates a message why user wasn't put in the database
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addUsertoDatabase(DatabaseReference mrefUser, FirebaseAuth mAuth){
        //Sets a unique id to the user that gets added
        String userId = mAuth.getCurrentUser().getUid();
        UserInformation theUser = new UserInformation(userId,editUsername.getText().toString().trim(),editEmail.getText().toString().trim(), 0);
        mrefUser.child(userId).setValue(theUser);
    }

    public void signIn(String email, String password,FirebaseAuth mAuth){
        mAuth.signInWithEmailAndPassword(email,password);
        Intent loginIntent = new Intent(CreateActivity.this, ProfileActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);

    }
}
