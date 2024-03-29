package kandidat.trainingapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import kandidat.trainingapp.Activities.AddFavoritesActivity;
import kandidat.trainingapp.Adapter.FavoriteAdapter;
import kandidat.trainingapp.Models.FavoriteModel;
import kandidat.trainingapp.Models.Points;
import kandidat.trainingapp.R;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FavoritesFragment extends Fragment {

    private ListView listView;
    private Context context;
    private Points points;
    private TextView headerText;


    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;

        points = new Points();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = db.getReference("favorites");
        DatabaseReference myRef = ref.child(user.getUid());

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        listView = (ListView) rootView.findViewById(R.id.favorite_list);

        headerText = (TextView) rootView.findViewById(R.id.empty_favorites);

        ArrayList<FavoriteModel> allFavoriteItems = new ArrayList<>();

            myRef.orderByChild("favorites").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String busStops = "Walked bus stops";
                    Set<FavoriteModel> bus = new TreeSet<>();
                    String stairs = "Walked flight of stairs";
                    Set<FavoriteModel> stairsList = new TreeSet<>();
                    String stand = "Minutes standing up";
                    Set<FavoriteModel> standList = new TreeSet<>();

                    for (DataSnapshot ds : dataSnapshot.child("BusStop").getChildren()) {
                        Integer dsVal = (int) (long) ds.getValue();
                        bus.add(new FavoriteModel(busStops, dsVal));

                    }
                    for (DataSnapshot ds : dataSnapshot.child("Stairs").getChildren()) {
                        Integer dsVal = (int) (long) ds.getValue();
                        stairsList.add(new FavoriteModel(stairs, dsVal));

                    }
                    for (DataSnapshot ds : dataSnapshot.child("standing").getChildren()) {
                        Integer dsVal = (int) (long) ds.getValue();
                        standList.add(new FavoriteModel(stand, dsVal));

                    }

                    allFavoriteItems.clear();
                    allFavoriteItems.addAll(bus);
                    allFavoriteItems.addAll(stairsList);
                    allFavoriteItems.addAll(standList);




                    FavoriteAdapter mAdapter = new FavoriteAdapter(context,
                            R.layout.layout_favorite_row, R.id.activity_text, allFavoriteItems);
                    if(allFavoriteItems.isEmpty()){
                        if(isAdded()) {
                            headerText.setText(getString(R.string.empty_favorites_string));
                            headerText.setBackgroundColor(getResources().getColor(R.color.almostTransparent));
                            listView.setBackgroundColor(Color.TRANSPARENT);
                        }
                    }else{
                        if(isAdded()) {
                            headerText.setText("");
                            headerText.setBackgroundColor(Color.TRANSPARENT);
                            listView.setBackgroundColor(getResources().getColor(R.color.almostTransparent));

                        }

                    }

                    listView.setAdapter(mAdapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFavoritesActivity.class);
                startActivity(intent);
            }
        });



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Object item = adapterView.getItemAtPosition(i);
                    FavoriteModel itemClicked = (FavoriteModel) item;
                    Integer finalPoints;
                    if(itemClicked.getActivity().equals("Walked bus stops")){
                        finalPoints = itemClicked.getValue() * 5;
                        points.calcualtePoints(finalPoints);
                    }else{
                        finalPoints = itemClicked.getValue();
                        points.calcualtePoints(finalPoints);
                    }

                    Context context = getApplicationContext();

                    CharSequence text = "You earned " + finalPoints + " points!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });

        return rootView;
    }


}
