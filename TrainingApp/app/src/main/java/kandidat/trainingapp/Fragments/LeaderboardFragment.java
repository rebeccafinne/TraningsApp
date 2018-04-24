package kandidat.trainingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import kandidat.trainingapp.Activities.AddFriendActivity;
import kandidat.trainingapp.Adapter.LeaderboardAdapter;
import kandidat.trainingapp.Models.LeaderboardModel;
import kandidat.trainingapp.R;
import kandidat.trainingapp.Repositories.UserInformation;

import static com.facebook.FacebookSdk.getApplicationContext;


public class LeaderboardFragment extends Fragment {

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private DatabaseReference friendRef;
    private DatabaseReference userRef;
    private ListView leaderboardList;
    private FloatingActionButton addFriend;
    private FirebaseUser theCurrenUser = FirebaseAuth.getInstance().getCurrentUser();
    private UserInformation currentUser;
    private ArrayList<LeaderboardModel> dataModels;
    private LeaderboardAdapter leaderboardAdapter;


    public static LeaderboardFragment newInstance() {
        LeaderboardFragment fragment = new LeaderboardFragment();
        return fragment;
    }

    @Override
    public void onStart() {
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
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        leaderboardList = (ListView) theView.findViewById(R.id.leaderList);
        addFriend = (FloatingActionButton) theView.findViewById(R.id.add_new_friend);
        friendRef = ref.child("friends");
        userRef = ref.child("users");
        dataModels = new ArrayList<>();

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFriendIntent = new Intent(getActivity(), AddFriendActivity.class);
                startActivity(addFriendIntent);
            }
        });


        friendRef.child(theCurrenUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                userRef.child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            String userName = dataSnapshot.child("displayName").getValue().toString();
                            String points = dataSnapshot.child("points").getValue().toString();
                            String UID = dataSnapshot.child("userId").getValue().toString();
                        Iterator<LeaderboardModel> theIteration = dataModels.iterator();

                        while (theIteration.hasNext()) {
                            LeaderboardModel lm = theIteration.next();
                            if (lm.getUID().equals(UID)) {
                                theIteration.remove();
                            }
                        }

                            dataModels.add(new LeaderboardModel(userName, points, UID));
                            sortList();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        leaderboardAdapter = new LeaderboardAdapter(dataModels, getApplicationContext());
        leaderboardList.setAdapter(leaderboardAdapter);
        return theView;
    }

    private void sortList() {
        Collections.sort(dataModels);
        Collections.reverse(dataModels);
        leaderboardAdapter.notifyDataSetChanged();
    }

}

