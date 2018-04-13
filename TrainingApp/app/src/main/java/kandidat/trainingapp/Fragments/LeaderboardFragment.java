package kandidat.trainingapp.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import kandidat.trainingapp.Activities.AddFriendActivity;
import kandidat.trainingapp.Activities.AnotherUserActivity;
import kandidat.trainingapp.Repositories.UserInformation;
import kandidat.trainingapp.R;


public class LeaderboardFragment extends Fragment {

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ListView leaderboardList;
    private FloatingActionButton addFriend;
    private FirebaseUser theCurrenUser = FirebaseAuth.getInstance().getCurrentUser();
    private UserInformation currentUser;



    public static LeaderboardFragment newInstance() {
        LeaderboardFragment fragment = new LeaderboardFragment();
        return fragment;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        currentUser = new UserInformation(theCurrenUser.getUid(),theCurrenUser.getDisplayName(),theCurrenUser.getEmail());
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        leaderboardList = (ListView) theView.findViewById(R.id.leaderList);
        addFriend = (FloatingActionButton) theView.findViewById(R.id.add_new_friend);
        ref.child("friends").child(theCurrenUser.getUid()).child(theCurrenUser.getUid()).setValue(currentUser);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFriendIntent = new Intent(getActivity(), AddFriendActivity.class);
                startActivity(addFriendIntent);
            }
        });



        FirebaseListAdapter<UserInformation> fListAdapter = new FirebaseListAdapter<UserInformation>(
                getActivity(),
                UserInformation.class,
                R.layout.leaderboard_representation,
                ref.child("friends").child(theCurrenUser.getUid()).orderByChild("negPoints")
        ) {
            TextView name;
            TextView points;

            @Override
            protected void populateView(View v, UserInformation model, int position) {
                name = v.findViewById(R.id.txt_name);
                points = v.findViewById(R.id.txt_points);


                if(model.getUserId().equals(theCurrenUser.getUid())){
                    name.setText(String.valueOf(model.getDisplayName()) + " (You)");
                    name.setTextColor(Color.rgb(0,108,0));
                    points.setText(String.valueOf(model.getPoints()));
                }else{
                    name.setText(String.valueOf(model.getDisplayName()));
                    name.setTextColor(Color.BLUE);
                    points.setText(String.valueOf(model.getPoints()));

                }



                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent profileIntent = new Intent(getActivity(),AnotherUserActivity.class);
                        String userId = getRef(position).getKey();
                        profileIntent.putExtra("userId", userId);
                        startActivity(profileIntent);

                    }
                });

            }
        };

        leaderboardList.setAdapter(fListAdapter);


        return theView;
    }


}
