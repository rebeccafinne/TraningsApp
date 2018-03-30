package kandidat.trainingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import kandidat.trainingapp.Activities.AnotherUserActivity;
import kandidat.trainingapp.Repositories.UserInformation;
import kandidat.trainingapp.R;


public class LeaderboardFragment extends Fragment {

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ListView leaderboardList;
    private List<String> users;
    private List<String> uids;
    private FirebaseAuth.AuthStateListener mauthListener;
    private FirebaseAuth mauth;
    private RecyclerView rec;

    public static LeaderboardFragment newInstance() {
        LeaderboardFragment fragment = new LeaderboardFragment();
        return fragment;
    }

    @Override
    public void onStart(){
        super.onStart();
        mauth.addAuthStateListener(mauthListener);
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
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        users = new ArrayList<String>();




        uids = new ArrayList<String>();
        leaderboardList = (ListView) theView.findViewById(R.id.leaderList);


        mauth = FirebaseAuth.getInstance();
        mauthListener = (FirebaseAuth.AuthStateListener) (FirebaseAuth) ->{
            if(FirebaseAuth.getCurrentUser() != null){

            }

        };

        FirebaseListAdapter<UserInformation> fListAdapter = new FirebaseListAdapter<UserInformation>(
                getActivity(),
                UserInformation.class,
                R.layout.leaderboard_representation,
                ref.child("users")
        ) {
            TextView name;
            TextView points;

            @Override
            protected void populateView(View v, UserInformation model, int position) {
                name = v.findViewById(R.id.txt_name);
                points = v.findViewById(R.id.txt_points);
                name.setText(String.valueOf(model.getDisplayName()));
                points.setText(String.valueOf(model.getPoints()));

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
