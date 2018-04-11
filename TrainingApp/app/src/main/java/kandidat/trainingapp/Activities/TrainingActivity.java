package kandidat.trainingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;

import kandidat.trainingapp.Models.BasicWorkout;
import kandidat.trainingapp.Models.Exercise;
import kandidat.trainingapp.Models.Points;
import kandidat.trainingapp.Models.Timer;
import kandidat.trainingapp.Models.Workout;
import kandidat.trainingapp.R;

public class TrainingActivity extends AppCompatActivity {

    //**********************************************************************************************
    //*****************************Common stuff*****************************************************
    //**********************************************************************************************
    private Context context;
    private Points points;

    //**********************************************************************************************
    //******************************Timer and stuff for that****************************************
    //**********************************************************************************************
    private TextView timerText;
    private ImageButton btnTimerStart;
    private ImageButton btnTimerStop;
    private Timer timer;
    private Thread timerThread;
    private Boolean timerOn;

    //**********************************************************************************************
    //******************************Stuff for the listview *****************************************
    //**********************************************************************************************
    private Workout workout;
    private FloatingActionButton btnAddExercise;
    private Button btnAddRow;
    private Button btnDone;
    private Exercise currentExercise; //used when editing one exercise.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        workout = new Workout();
        timerOn = false;
        points = new Points();
        context = this;

        //******************************************************************************************
        //**************************For the timer **************************************************
        //******************************************************************************************
        timerText = findViewById(R.id.timer_text);
        btnTimerStart = findViewById(R.id.btn_timer_start_pause);
        btnTimerStop = findViewById(R.id.btn_timer_stop);

        btnTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerOn) {
                    Toast.makeText(getApplicationContext(), "Clicked start ",
                            Toast.LENGTH_SHORT).show(); //TODO remove when everything works.
                    if (timer == null) {
                        timer = new Timer(context);
                        timerThread = new Thread(timer);
                        timerThread.start();
                    }
                    timer.startTimer();
                    timerOn = true;
                    btnTimerStart.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                }else{
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

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(TrainingActivity.this);
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

        //******************************************************************************************
        //**************************For the listview ***********************************************
        //******************************************************************************************
        btnAddExercise = findViewById(R.id.btn_add_ex);

        workout = new Workout();

        ListView lstExercises = findViewById(R.id.list_view_gym_exercies);
        final ListExerciseAdapter listAdapter = new ListExerciseAdapter();
        final RowAdapter rowAdapter = new RowAdapter();

        lstExercises.setAdapter(listAdapter);

        lstExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                workout.getExercise("Random").setName("Nytt namn");
//                listAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Clickade", Toast.LENGTH_SHORT).show(); //TODO remove when working
            }
        });


        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentExercise = new Exercise();
                workout.addNewExercise(currentExercise);

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(TrainingActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_exercise, null);

                final TextView mExerciseHeader = mView.findViewById(R.id.ex_name);
                final ListView lstRows = mView.findViewById(R.id.lst_rows);
                lstRows.setAdapter(rowAdapter);
                final TextView description = mView.findViewById(R.id.description_edit);

                mBuilder.setView(mView);
                final AlertDialog exerciseDialog = mBuilder.create();
                exerciseDialog.show();

                btnAddRow = mView.findViewById(R.id.btn_add_row);
                btnAddRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        View temporaryView;
                        EditText setEt, repEt, weightEt;
                        for (int i = 0; i < rowAdapter.getCount(); i++) {
                            temporaryView = lstRows.getChildAt(i);

                            setEt = (EditText) temporaryView.findViewById(R.id.set_set);
                            repEt = (EditText) temporaryView.findViewById(R.id.set_rep);
                            weightEt = (EditText) temporaryView.findViewById(R.id.set_weight);

                            int setValue = 0;
                            int repValue = 0;
                            int weightValue = 0;

                            if(setEt.getText().toString() != null && !setEt.getText().toString().matches("")){
                                setValue = Integer.parseInt(setEt.getText().toString());
                            }
                            if(repEt.getText().toString() != null && !repEt.getText().toString().matches("")){
                                repValue = Integer.parseInt(repEt.getText().toString());
                            }
                            if(weightEt.getText().toString() != null && !weightEt.getText().toString().matches("") ){
                                weightValue = Integer.parseInt(weightEt.getText().toString());
                            }

                            workout.setRow(currentExercise,i, setValue, repValue, weightValue);
                        }

                        workout.newRow(currentExercise);
                        rowAdapter.notifyDataSetChanged();
                    }
                });

                btnDone = mView.findViewById(R.id.btn_add_ex_done);
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View temporaryView;
                        EditText setEt, repEt, weightEt;
                        for (int i = 0; i < rowAdapter.getCount(); i++) {
                            temporaryView = lstRows.getChildAt(i);

                            setEt = (EditText) temporaryView.findViewById(R.id.set_set);
                            repEt = (EditText) temporaryView.findViewById(R.id.set_rep);
                            weightEt = (EditText) temporaryView.findViewById(R.id.set_weight);

                            int setValue = 0;
                            int repValue = 0;
                            int weightValue = 0;

                            if (setEt.getText().toString() != null && !setEt.getText().toString().matches("")) {
                                setValue = Integer.parseInt(setEt.getText().toString());
                            }
                            if (repEt.getText().toString() != null && !repEt.getText().toString().matches("")) {
                                repValue = Integer.parseInt(repEt.getText().toString());
                            }
                            if (weightEt.getText().toString() != null && !weightEt.getText().toString().matches("")) {
                                weightValue = Integer.parseInt(weightEt.getText().toString());
                            }

                            workout.setRow(currentExercise, i, setValue, repValue, weightValue);
                        }

                        if(!mExerciseHeader.getText().toString().matches( "") ){
                            currentExercise.setName(mExerciseHeader.getText().toString());
                        }
                        if(!description.getText().toString().matches("")){
                            currentExercise.setDescription(description.getText().toString());
                        }
                        exerciseDialog.dismiss();
                        listAdapter.notifyDataSetChanged();
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


    class ListExerciseAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return workout.nbrOfExercises();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            // inflate the layout for each list row
            Exercise tmpExercise = workout.getExercise(i);
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.costum_layout, null);
            }

            TextView exName = view.findViewById(R.id.text_sets);

            LinearLayout rows = view.findViewById(R.id.ll_rows);
            rows.removeAllViews();

            LinearLayout tmpLayout;
            TextView tv_set;
            TextView tv_reps;
            TextView tv_weight;
            for (int j = 0; j < workout.nbrOfRows(tmpExercise); j++) {
                tmpLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.exercise_show_layout, null);
                tmpLayout.setId(i);

                tv_set = tmpLayout.findViewById(R.id.tv_set);
                tv_set.setText("" + workout.getSets(tmpExercise, j));

                tv_reps = tmpLayout.findViewById(R.id.tv_reps);
                tv_reps.setText("" + workout.getReps(tmpExercise, j));

                tv_weight = tmpLayout.findViewById(R.id.tv_weight);
                tv_weight.setText("" + workout.getWeight(tmpExercise, j));

                rows.addView(tmpLayout);
            }

            if (workout.nbrOfExercises() != 0) {
                exName.setText(workout.getExercise(i).getName());
            }

            return view;
        }
    }

    public static Intent createIntent(Context context, IdpResponse idpResponse) {
        Intent in = IdpResponse.getIntent(idpResponse);
        in.setClass(context, TrainingActivity.class);
        return in;
    }

    public static Intent createIntent(Context context) {
        Intent in = new Intent();
        in.setClass(context, TrainingActivity.class);
        return in;
    }

    class RowAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return workout.nbrOfRows(currentExercise);
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public int getSets(int i){
            return workout.getSets(currentExercise, i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.exercise_edit_layout, null);

            TextView setsView = view.findViewById(R.id.set_set);
            TextView repsView = view.findViewById(R.id.set_rep);
            TextView weightView = view.findViewById(R.id.set_weight);


            if(workout.getSets(currentExercise, i) != 0) setsView.setText(""+ workout.getSets(currentExercise, i));
            if(workout.getReps(currentExercise, i) != 0) repsView.setText(""+ workout.getReps(currentExercise, i));
            if(workout.getWeight(currentExercise, i) != 0) weightView.setText(""+ workout.getWeight(currentExercise, i));

            return view;
        }
    }

}
