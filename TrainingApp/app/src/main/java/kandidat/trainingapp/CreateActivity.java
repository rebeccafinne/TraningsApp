package kandidat.trainingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail, editPassword, editUsername;
    public String text;
    FirebaseAuth mauth;
    DatabaseReference mrefUsers;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Listener for create account button
        findViewById(R.id.create_button).setOnClickListener(this);

        //Listener to when user already have account
        findViewById(R.id.have_text).setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editUsername = (EditText) findViewById(R.id.editUsername);

        //Firebase auth used to authorise a user
        mauth = FirebaseAuth.getInstance();
        //Firebase database reference used to store values in database
        mrefUsers = FirebaseDatabase.getInstance().getReference("users");

    }

    public void registerUser(){
        //Username will only be used as the displayable name
        if(editUsername.getText().toString().trim().isEmpty()){
            editUsername.setError("Please, choose a username of your liking.");
            editUsername.requestFocus();
            return;
        }
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
        createAccount(editEmail.getText().toString().trim(),editPassword.getText().toString().trim());
    }


    public void createAccount(String email, String password){
        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // if the user was put in correctly in database
                if(task.isSuccessful()){
                    addUsertoDatabase();
                    Intent loginIntent = new Intent(CreateActivity.this, ProfileActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                // generates a message why user wasn't put in the database
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addUsertoDatabase(){
        //Sets a unique id to the user that gets added
        String userId = mrefUsers.push().getKey();
        UserInformation theUser = new UserInformation(editUsername.getText().toString().trim(),editEmail.getText().toString().trim());
        mrefUsers.child(userId).setValue(theUser);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_button:

                registerUser();

                break;

            case R.id.have_text:
                startActivity(new Intent(this,MainActivity.class ));
                break;
        }

    }

}
