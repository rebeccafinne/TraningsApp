package kandidat.trainingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kandidat.trainingapp.Activities.AppMainActivity;
import kandidat.trainingapp.Adapter.FavoriteAdapter;
import kandidat.trainingapp.Models.Favorites;
import kandidat.trainingapp.Repositories.FavoriteData;
import kandidat.trainingapp.Models.FavoriteModel;
import kandidat.trainingapp.R;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FavoritesFragment extends Fragment {

    private ListView listView;
    private FavoriteData favoriteData;
    private CheckBox checkBox;


    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        listView = (ListView) rootView.findViewById(R.id.favorite_list);
        checkBox = (CheckBox) rootView.findViewById(R.id.checkBox);

        favoriteData = (FavoriteData) getActivity().getApplicationContext();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = db.getReference("favorites");
        DatabaseReference myRef = ref.child(user.getUid());

        DatabaseReference pointRef = db.getReference("users").child(user.getUid()).child("points");
        DatabaseReference userRef = db.getReference("users");


        ArrayList<FavoriteModel> allFavoriteItems = new ArrayList<>();

        myRef.orderByChild("favorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String busStops = "Walked bus stops";
                Set<FavoriteModel> bus = new HashSet<>();
                String stairs = "Walked flight of stairs";
                Set<FavoriteModel> stairsList = new HashSet<>();
                String stand = "Minutes standing up";
                Set<FavoriteModel> standList = new HashSet<>();

                for(DataSnapshot ds : dataSnapshot.child("BusStop").getChildren()){
                    Integer dsVal = (int) (long) ds.getValue();
                    bus.add(new FavoriteModel(busStops, dsVal));

                }
                for(DataSnapshot ds : dataSnapshot.child("Stairs").getChildren()){
                    Integer dsVal = (int) (long) ds.getValue();
                    stairsList.add(new FavoriteModel(stairs, dsVal));

                }
                for(DataSnapshot ds : dataSnapshot.child("standing").getChildren()){
                    Integer dsVal = (int) (long) ds.getValue();
                    standList.add(new FavoriteModel(stand, dsVal));

                }

                allFavoriteItems.clear();
                allFavoriteItems.addAll(bus);
                allFavoriteItems.addAll(stairsList);
                allFavoriteItems.addAll(standList);



                FavoriteAdapter mAdapter = new FavoriteAdapter(getContext(),
                        R.layout.layout_favorite_row, R.id.activity_text, allFavoriteItems);

                listView.setAdapter(mAdapter);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                FavoriteModel itemClicked = (FavoriteModel) item;

                pointRef.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Integer currentData = mutableData.getValue(Integer.class);
                        if(currentData == 0){
                            pointRef.setValue(itemClicked.getValue());

                        }else{
                            currentData = currentData + itemClicked.getValue();
                            pointRef.setValue(currentData);
                        }
                        return null;
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                    }
                });
                Context context = getApplicationContext();

                CharSequence text = "Favorite complete registered!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });









        return rootView;
    }


}
