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

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editEmail, editPassword;
    public String text;
    FirebaseAuth mauth;




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

        mauth = FirebaseAuth.getInstance();

    }

    public void registerUser(){
        if(editEmail.getText().toString().trim().isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText().toString().trim()).matches()){
            editEmail.setError("Not a valid email");
            editEmail.requestFocus();
            return;
        }

        if(editPassword.getText().toString().trim().isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }

        if (editPassword.getText().toString().trim().length() < 6){
            editPassword.setError("Password should contain at least 6 characthers");
            editPassword.requestFocus();
            return;
        }

        createAccount(editEmail.getText().toString().trim(),editPassword.getText().toString().trim());
    }

    public void createAccount(String email, String password){
      mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
                  Intent loginIntent = new Intent(CreateActivity.this, ProfileActivity.class);
                  loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(loginIntent);
              }else{
                  Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
              }
          }
      });
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
