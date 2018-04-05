package kandidat.trainingapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import kandidat.trainingapp.Models.BasicWorkout;
import kandidat.trainingapp.Models.Timer;
import kandidat.trainingapp.R;


public class CardioActivity extends AppCompatActivity {

    //**********************************************************************************************
    //*****************************Common stuff*****************************************************
    //**********************************************************************************************

    private Context context;
    private final String TAG = "FB_TRAINING";
    private BasicWorkout workout;

    //**********************************************************************************************
    //******************************Timer and stuff for that****************************************
    //********************************************************************************************
    private TextView timerText;
    private Button btnTimerStart;
    private Button btnTimerStop;
    private Button btnTimerPaus;

    private Timer timer;
    private Thread timerThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio);

        workout = new BasicWorkout();
        context = this;

        //******************************************************************************************
        //**************************For the timer **************************************************
        //******************************************************************************************
        timerText = findViewById(R.id.timer_text);
        btnTimerStart = findViewById(R.id.btn_timer_start);
        btnTimerStop = findViewById(R.id.btn_timer_stop);
        btnTimerPaus = findViewById(R.id.btn_timer_paus);

        btnTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked start " , Toast.LENGTH_SHORT).show(); //TODO remove when everything works.
                if(timer == null){
                    timer = new Timer(context);
                    timerThread = new Thread(timer);
                    timerThread.start();
                }
                timer.startTimer();
            }
        });

        btnTimerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked stop" , Toast.LENGTH_SHORT).show();
                if(timer != null){
                    workout.setDuration(timer.stopTimer());
                    timerThread.interrupt();
                    timerThread = null;
                    timer = null;
                }
            }
        });

        btnTimerPaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked Paus", Toast.LENGTH_SHORT).show();
                if(timer != null) timer.pausTimer();
            }
        });


    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timerText.setText(time);
            }
        });
    }

}
