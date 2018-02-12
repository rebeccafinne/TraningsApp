package kandidat.trainingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener{

    EditText editEmail, editPassword;
    String email, password;
    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();


    }

    private void registrerUser(){
        email = editEmail.getText().toString().trim();
        password = editPassword.getText().toString().trim();

        if(email.isEmpty()){
            editEmail.setError("You can not leave the Emailfield empty");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editPassword.setError("Please fill in the Passwordfield");
            editPassword.requestFocus();
            return;
        }
        //Firebase does not accept passwords with less than 6 chars
        if(password.length()<6){
            editPassword.setError("Please choose an password with at least 6 charachters");
            editPassword.requestFocus();
            return;
        }
        // Checks if the entered email matches an valid email pattern error otherwise
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please enter an valid email");
            editEmail.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //If the user get successfully registered
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User is successfully registered",Toast.LENGTH_SHORT).show();
                    mAuth.signInWithEmailAndPassword(email,password);
                    Intent loginIntent = new Intent(CreateAccount.this, ProfileActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }else{
                    //If the email is already in database
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"Email has already been used!", Toast.LENGTH_SHORT).show();
                        //If some other error occurs
                    }else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.create_button:
                registrerUser();
                break;
            case R.id.have_text:
                //If already has account go back to the mainActivity
                startActivity(new Intent(this,MainActivity.class ));
                break;
        }
    }
}
