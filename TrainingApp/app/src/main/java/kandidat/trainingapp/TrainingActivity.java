package kandidat.trainingapp;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    //**********************************************************************************************
    //*****************************Common stuff*****************************************************
    //**********************************************************************************************
    private Context context;
    private final String TAG = "FB_TRAINING";

    //**********************************************************************************************
    //******************************Timer and stuff for that****************************************
    //**********************************************************************************************
    private TextView timerText;
    private Button btnTimerStart;
    private Button btnTimerStop;
    private Button btnTimerPaus;

    private Timer timer;
    private Thread timerThread;

    //**********************************************************************************************
    //******************************Stuff for the listview *****************************************
    //**********************************************************************************************
    private Workout workout;
    private Button btnAddExercise;
    private Button btnAddRow;
    private Button btnDone;
    private Exercise currentExercise; //used when editing one exercise.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        workout = new Workout();

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
                Toast.makeText(getApplicationContext(), "Clicked start " ,
                        Toast.LENGTH_SHORT).show(); //TODO remove when everything works.
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
                    timer.stopTimer();
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

                rowAdapter.notifyDataSetChanged();

                mBuilder.setView(mView);
                final AlertDialog exerciseDialog = mBuilder.create();
                exerciseDialog.show();

                listAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Klickade Add Exercise", Toast.LENGTH_SHORT).show(); //TODO remove when working

                btnAddRow = mView.findViewById(R.id.btn_add_row);
                btnAddRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        View temporaryView;
                        EditText et;
                        for (int i = 0; i < rowAdapter.getCount(); i++) {
                            temporaryView = lstRows.getChildAt(i);
//                            Toast.makeText(getApplicationContext(), ""+i, Toast.LENGTH_SHORT).show();

                            et = (EditText) temporaryView.findViewById(R.id.set_set);
                            workout.setSet(currentExercise,i, 44);
                            Toast.makeText(getApplicationContext(), ""+ workout.getSets(currentExercise, i), Toast.LENGTH_SHORT).show();

                            // mannschaftsnamen.add(et.getText().toString());
                        }

                        workout.newRow(currentExercise, 0,0,0);
                        Toast.makeText(getApplicationContext(), "Klickade AddRow", Toast.LENGTH_SHORT).show();
                        rowAdapter.notifyDataSetChanged();
                    }
                });

                btnDone = mView.findViewById(R.id.btn_add_ex_done);
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Pressed Done", Toast.LENGTH_SHORT).show();
                        String exName = mExerciseHeader.getText().toString();
                        currentExercise.setName(exName);
                        exerciseDialog.dismiss();
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
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.costum_layout, null);
            }

     //       TextView repsView = view.findViewById(R.id.text_reps);
            TextView setsView = view.findViewById(R.id.text_sets);
   //         TextView weightView = view.findViewById(R.id.text_weight);

            if ( workout.nbrOfExercises() != 0) {
 //               repsView.setText(workout.getExercise(i).getName());
                setsView.setText(workout.getExercise(i).getName());
 //               weightView.setText(workout.getExercise(i).getName());
            }

            return view;
        }
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


            setsView.setText(""+ workout.getSets(currentExercise, i));

            return view;
        }
    }
}
