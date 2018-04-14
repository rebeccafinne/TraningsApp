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
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.ValueEventListener;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import kandidat.trainingapp.Activities.AddFriendActivity;
import kandidat.trainingapp.Activities.AnotherUserActivity;
import kandidat.trainingapp.Adapter.LeaderboardAdapter;
import kandidat.trainingapp.Models.LeaderboardModel;
import kandidat.trainingapp.Repositories.UserInformation;
import kandidat.trainingapp.R;

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

        friendRef = ref.child("friends");
        userRef= ref.child("users");
        dataModels = new ArrayList<>();


        /*List<String> friendUIDS = new ArrayList<String>();
        ArrayAdapter<String> arrayA = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                friendUIDS);*/

        // Can't use this.
        // ref.child("friends").child(theCurrenUser.getUid()).child(theCurrenUser.getUid()).setValue(currentUser);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFriendIntent = new Intent(getActivity(), AddFriendActivity.class);
                startActivity(addFriendIntent);
            }
        });

        friendRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                sortList();
                LeaderboardFragment.newInstance().getView();
                userRef.child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                leaderboardAdapter.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    userRef.child(ds.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String userName = dataSnapshot.child("displayName").getValue().toString();
                            String points = dataSnapshot.child("points").getValue().toString();
                            String UID = dataSnapshot.child("userId").getValue().toString();

                            dataModels.add(new LeaderboardModel(userName,points,UID));
                            LeaderboardFragment.newInstance().getView();
                            sortList();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                LeaderboardFragment.newInstance().getView();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                LeaderboardFragment.newInstance().getView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       /* friendRef.child(currentUser.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    userRef.child(ds.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String userName = dataSnapshot.child("displayName").getValue().toString();
                            String points = dataSnapshot.child("points").getValue().toString();
                            String UID = ds.getKey().toString();

                            dataModels.add(new LeaderboardModel(userName,points,UID));
                            leaderboardAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                leaderboardAdapter.clear();
                LeaderboardFragment.newInstance().getView();
                friendRef.child(currentUser.getUserId()).child(currentUser.getUserId()).setValue(Math.random()*2000000000);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        leaderboardAdapter = new LeaderboardAdapter(dataModels,getApplicationContext());

        /*friendRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    userRef.child(ds.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            friendUIDS.add(dataSnapshot.getValue(UserInformation.class).getDisplayName().toString() + "\t" +
                                    dataSnapshot.getValue(UserInformation.class).getPoints());
                            arrayA.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                arrayA.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        FirebaseListAdapter<UserInformation> fListAdapter = new FirebaseListAdapter<UserInformation>(
                getActivity(),
                UserInformation.class,
                R.layout.leaderboard_representation,
                userRef
        ) {
            TextView name;
            TextView points;

            @Override
            protected void populateView(View v, UserInformation model, int position) {
                name = v.findViewById(R.id.txt_name);
                points = v.findViewById(R.id.thePointsCollected);

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

        leaderboardList.setAdapter(leaderboardAdapter);
        return theView;
    }

    private void sortList() {
        Collections.sort(dataModels);
        Collections.reverse(dataModels);
        leaderboardAdapter.notifyDataSetChanged();
    }


}
