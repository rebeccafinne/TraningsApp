package kandidat.trainingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import kandidat.trainingapp.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView profileText;
    private UserInformation usr;
    DatabaseReference rootRef, nameRef;
    private Button chartButton, statsButton;


    public static ProfileFragment newInstance() {
        return  new ProfileFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    private void startMainIntent() {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profileText = (TextView) rootView.findViewById(R.id.nameInProfile);
        chartButton = (Button) rootView.findViewById(R.id.chartButton);
        chartButton.setOnClickListener(this);
        statsButton = (Button) rootView.findViewById(R.id.statsButton);
        statsButton.setOnClickListener(this);
        //A reference to Authentication in Firebase
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        //If there is no user signed in then it should return to mainActivity
        if(mAuth.getCurrentUser() == null){
            startMainIntent();

        }
        else {
            createName(FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()), mAuth);
        }

        return rootView;
    }


    public void createName(DatabaseReference userRef, final FirebaseAuth mAuth){


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                if(user.getDisplayName() == null){
                    profileText.setText("No user logged in");
                }else{
                    String userDisplayCap = user.getDisplayName();
                    profileText.setText(userDisplayCap);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getMessage());


            }
        });


    }

    @Override
    public void onClick(View view) {

        Intent intent;
        if(view.getId() == R.id.chartButton){
            intent = new Intent(getActivity(), ChartActivity.class);
        }else{
            intent = new Intent(getActivity(), StatsActivity.class);
        }

        startActivity(intent);


    }
}
