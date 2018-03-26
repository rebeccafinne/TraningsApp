package kandidat.trainingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kandidat.trainingapp.Activities.CardioActivity;
import kandidat.trainingapp.Activities.DailyActivity;
import kandidat.trainingapp.Activities.TrainingActivity;
import kandidat.trainingapp.R;


public class TrainingFragment extends Fragment implements View.OnClickListener {

    Button gymButton, cardioButton, dailyButton;


    public static TrainingFragment newInstance() {
        TrainingFragment fragment = new TrainingFragment();
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_training, container, false);

        gymButton = (Button) rootView.findViewById(R.id.gymButton);
        gymButton.setOnClickListener(this);
        cardioButton = (Button) rootView.findViewById(R.id.cardioButton);
        cardioButton.setOnClickListener(this);
        dailyButton = (Button) rootView.findViewById(R.id.dailyButton);
        dailyButton.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View view) {
        Intent intent;

        if(view.getId() == R.id.gymButton){
            intent = new Intent(getActivity(), TrainingActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.cardioButton){
            intent = new Intent(getActivity(), CardioActivity.class);
            startActivity(intent);
        }else{
            intent = new Intent(getActivity(), DailyActivity.class);
            startActivity(intent);
        }
    }




}
