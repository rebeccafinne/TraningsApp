package kandidat.trainingapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


//testing git again
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.create_button).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    //Testing git
    public void signIn(View view) {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // When Create account button is pressed
            case R.id.create_button:
                startActivity(new Intent(this,CreateAccount.class ));
                break;
        }
    }
}
