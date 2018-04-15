package kandidat.trainingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

import kandidat.trainingapp.Models.BasicWorkout;
import kandidat.trainingapp.Models.Exercise;
import kandidat.trainingapp.Models.ExerciseRow;
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
    private Timer timer;
    private Thread timerThread;
    private Boolean timerOn;

    //**********************************************************************************************
    //******************************Stuff for the list view *****************************************
    //**********************************************************************************************
    private Workout workout;
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.include);
        TextView toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        toolText.setText(R.string.addGymFavorite);

        //******************************************************************************************
        //**************************For the timer **************************************************
        //******************************************************************************************
        timerText = findViewById(R.id.timer_text);
        btnTimerStart = findViewById(R.id.btn_timer_start_pause);
        ImageButton btnTimerStop = findViewById(R.id.btn_timer_stop);

        btnTimerStart.setOnClickListener(view -> {
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
                Toast.makeText(getApplicationContext(), "Clicked Pause", Toast.LENGTH_SHORT).show();
                if(timer != null){
                    timer.pausTimer();
                    timerOn = false;
                    btnTimerStart.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                }
            }
        });

        btnTimerStop.setOnClickListener(view -> {

            if(timer == null){
                Toast.makeText(getApplicationContext(), "No workout started", Toast.LENGTH_SHORT).show();
                return;
            }

            if(timer != null) timer.pausTimer(); //Pause timer, should be able to go back.

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(TrainingActivity.this);
            View mView = View.inflate(context, R.layout.activity_how_challenging, null);

            CheckBox btnEasy = mView.findViewById(R.id.btn_easy);
            CheckBox btnMed = mView.findViewById(R.id.btn_med);
            CheckBox btnHard = mView.findViewById(R.id.btn_hard);
            Button btnDone = mView.findViewById(R.id.btn_done);
            btnEasy.setOnClickListener(view1 -> {
                if (btnEasy.isChecked()){
                    btnMed.setChecked(false); btnHard.setChecked(false);
                    workout.setDifficulty(BasicWorkout.Difficulty.EASY);
                }
            });
            btnMed.setOnClickListener(view12 -> {
                if (btnMed.isChecked()){
                    btnEasy.setChecked(false); btnHard.setChecked(false);
                    workout.setDifficulty(BasicWorkout.Difficulty.MEDIUM);
                }
            });
            btnHard.setOnClickListener(view13 -> {
                if (btnHard.isChecked()){
                    btnEasy.setChecked(false); btnMed.setChecked(false);
                    workout.setDifficulty(BasicWorkout.Difficulty.HARD);
                }
            });

            mBuilder.setView(mView);
            final AlertDialog howHardDialog = mBuilder.create();
            howHardDialog.show();

            btnDone.setOnClickListener(view14 -> {
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
            });

        });

        //******************************************************************************************
        //**************************For the list view ***********************************************
        //******************************************************************************************
        FloatingActionButton btnAddExercise = findViewById(R.id.btn_add_ex);

        workout = new Workout();

        ListView lstExercises = findViewById(R.id.list_view_gym_exercise);
        final ListExerciseAdapter listAdapter = new ListExerciseAdapter();

        lstExercises.setAdapter(listAdapter);

        lstExercises.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(getApplicationContext(), "Pressed", Toast.LENGTH_SHORT).show(); //TODO remove when working
        });


        btnAddExercise.setOnClickListener(view -> {
            final RowAdapter rowAdapter = new RowAdapter();
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(TrainingActivity.this);
            View mView = View.inflate(context, R.layout.dialog_exercise, null);

            final TextView mExerciseHeader = mView.findViewById(R.id.ex_name);
            final ListView lstRows = mView.findViewById(R.id.lst_rows);
            lstRows.setItemsCanFocus(true);
            lstRows.setAdapter(rowAdapter);
            final TextView description = mView.findViewById(R.id.description_edit);

            mBuilder.setView(mView);
            final AlertDialog exerciseDialog = mBuilder.create();
            exerciseDialog.show();

            btnAddRow = mView.findViewById(R.id.btn_add_row);
            btnAddRow.setOnClickListener(view15 -> {

                rowAdapter.myItems.add(new ExerciseRow(0,0,0));
                rowAdapter.notifyDataSetChanged();

            });

            btnDone = mView.findViewById(R.id.btn_add_ex_done);
            btnDone.setOnClickListener(view16 -> {
                //TODO om namnet redan finns, neka.
                if (mExerciseHeader.getText().toString().equals("")){
                    currentExercise = new Exercise();
                }else {
                    currentExercise = new Exercise(mExerciseHeader.getText().toString());
                }
                workout.addNewExercise(currentExercise, rowAdapter.myItems);
                exerciseDialog.dismiss();
                listAdapter.notifyDataSetChanged();
            });
         });

    }

    public void updateTimerText(final String time){
        runOnUiThread(() -> timerText.setText(time));
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
                view = View.inflate(context, R.layout.costum_layout, null);
            }

            TextView exName = view.findViewById(R.id.text_sets);

            LinearLayout rows = view.findViewById(R.id.ll_rows);
            rows.removeAllViews();

            LinearLayout tmpLayout;
            TextView tv_set;
            TextView tv_reps;
            TextView tv_weight;
            for (int j = 0; j < workout.nbrOfRows(tmpExercise); j++) {
                tmpLayout = (LinearLayout) View.inflate(context, R.layout.exercise_show_layout, null);
                tmpLayout.setId(i);

                String tmpString;
                tv_set = tmpLayout.findViewById(R.id.tv_set);
                tmpString = "" + workout.getSets(tmpExercise, j);
                tv_set.setText(tmpString);

                tv_reps = tmpLayout.findViewById(R.id.tv_reps);
                tmpString = "" + workout.getReps(tmpExercise, j);
                tv_reps.setText(tmpString);

                tv_weight = tmpLayout.findViewById(R.id.tv_weight);
                tmpString = "" + workout.getWeight(tmpExercise, j);
                tv_weight.setText(tmpString);

                rows.addView(tmpLayout);
            }

            if (workout.nbrOfExercises() != 0) {
                exName.setText(workout.getExercise(i).getName());
            }

            return view;
        }
    }

 /*   public static Intent createIntent(Context context, IdpResponse idpResponse) {
        Intent in = IdpResponse.getIntent(idpResponse);
        in.setClass(context, TrainingActivity.class);
        return in;
    }

    public static Intent createIntent(Context context) {
        Intent in = new Intent();
        in.setClass(context, TrainingActivity.class);
        return in;
    }*/

    class RowAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        public ArrayList<ExerciseRow> myItems = new ArrayList();

        public RowAdapter(){
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ExerciseRow tmpRow = new ExerciseRow(0,0,0);
            myItems.add(tmpRow);

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return myItems.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.exercise_edit_layout, null);
                holder.set = convertView.findViewById(R.id.set_set);
                holder.rep = convertView.findViewById(R.id.set_rep);
                holder.weight = convertView.findViewById(R.id.set_weight);
                //TODO for rep och weight
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if(myItems.get(i).getSets() != 0 ) {
                holder.set.setText("" + myItems.get(i).getSets());
            }else{
                holder.set.setText("");
            }
            holder.set.setId(i);

            if(myItems.get(i).getReps() != 0 ) {
                holder.rep.setText("" + myItems.get(i).getReps());
            }else{
                holder.rep.setText("");
            }
            holder.rep.setId(i);

            if(myItems.get(i).getWeight() != 0 ) {
                holder.weight.setText("" + myItems.get(i).getWeight());
            }else{
                holder.weight.setText("");
            }
            holder.weight.setId(i);

            holder.set.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        final int position = v.getId();
                        final EditText Caption = (EditText) v;
                        if(!Caption.getText().toString().equals("") ){
                            myItems.get(position).setSets(Integer.parseInt(Caption.getText().toString()));
                        }else{
                            myItems.get(position).setSets(0);
                        }
                    }
                }
            });

            holder.rep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        final int position = v.getId();
                        final EditText Caption = (EditText) v;
                        if(!Caption.getText().toString().equals("") ){
                            myItems.get(position).setReps(Integer.parseInt(Caption.getText().toString()));
                        }else{
                            myItems.get(position).setReps(0);
                        }
                    }
                }
            });

            holder.weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus){
                        final int position = v.getId();
                        final EditText Caption = (EditText) v;
                        if(!Caption.getText().toString().equals("") ){
                            myItems.get(position).setWeight(Integer.parseInt(Caption.getText().toString()));
                        }else{
                            myItems.get(position).setWeight(0);
                        }
                    }
                }
            });

            return convertView;
        }
    }

    class ViewHolder {
        EditText set;
        EditText rep;
        EditText weight;
    }
}
