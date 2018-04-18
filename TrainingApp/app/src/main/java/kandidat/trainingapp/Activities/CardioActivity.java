package kandidat.trainingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import kandidat.trainingapp.Models.BasicWorkout;
import kandidat.trainingapp.Models.Points;
import kandidat.trainingapp.Models.Timer;
import kandidat.trainingapp.R;


public class CardioActivity extends AppCompatActivity {

    //**********************************************************************************************
    //*****************************Common stuff*****************************************************
    //**********************************************************************************************

    private Context context;
    private final String TAG = "FB_TRAINING";
    private BasicWorkout workout;

    private Points points;
    //**********************************************************************************************
    //******************************Timer and stuff for that****************************************
    //********************************************************************************************
    private TextView timerText;
    private ImageButton btnTimerStart;
    private ImageButton btnTimerStop;
    private Boolean timerOn;

    private Timer timer;
    private Thread timerThread;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio);

        points = new Points();
        workout = new BasicWorkout();
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        TextView toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText("Cardio");
        setSupportActionBar(toolbar);


        //******************************************************************************************
        //**************************For the timer **************************************************
        //******************************************************************************************
        timerText = findViewById(R.id.timer_text);
        btnTimerStart = findViewById(R.id.btn_timer_start_pause);
        btnTimerStop = findViewById(R.id.btn_timer_stop);

        timerOn = false;


        btnTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerOn) {
                    Toast.makeText(getApplicationContext(), "Clicked start ", Toast.LENGTH_SHORT).show(); //TODO remove when everything works.
                    if (timer == null) {
                        timer = new Timer(context);
                        timerThread = new Thread(timer);
                        timerThread.start();
                    }
                    timer.startTimer();
                    timerOn = true;
                    btnTimerStart.setBackgroundResource(R.drawable.ic_pause_black_24dp);

                } else{
                    Toast.makeText(getApplicationContext(), "Clicked Paus", Toast.LENGTH_SHORT).show();
                    if(timer != null){
                        timer.pausTimer();
                        timerOn = false;
                        btnTimerStart.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    }

                }

            }
        });

        btnTimerStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(timer == null){
                    Toast.makeText(getApplicationContext(), "No workout started", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(timer != null) timer.pausTimer(); //Pause timer, should be able to go back.

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(CardioActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_how_challenging, null);

                CheckBox btnEasy = mView.findViewById(R.id.btn_easy);
                CheckBox btnMed = mView.findViewById(R.id.btn_med);
                CheckBox btnHard = mView.findViewById(R.id.btn_hard);
                Button btnDone = mView.findViewById(R.id.btn_done);
                btnEasy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (btnEasy.isChecked()){
                            btnMed.setChecked(false); btnHard.setChecked(false);
                            workout.setDifficulty(BasicWorkout.Difficulty.EASY);
                        }
                    }
                });
                btnMed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (btnMed.isChecked()){
                            btnEasy.setChecked(false); btnHard.setChecked(false);
                            workout.setDifficulty(BasicWorkout.Difficulty.MEDIUM);
                        }
                    }
                });
                btnHard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (btnHard.isChecked()){
                            btnEasy.setChecked(false); btnMed.setChecked(false);
                            workout.setDifficulty(BasicWorkout.Difficulty.HARD);
                        }
                    }
                });

                mBuilder.setView(mView);
                final AlertDialog howHardDialog = mBuilder.create();
                howHardDialog.show();

                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        howHardDialog.dismiss();

                        if(timer != null){
                            workout.setDuration(timer.stopTimer());
                            timerThread.interrupt();
                            timerThread = null;
                            timer = null;
                        }

                        points.calcualtePoints(workout.getPoints());


                        Toast.makeText(getApplicationContext(), "You just earned " + workout.getPoints()
                                + " points!", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });

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
