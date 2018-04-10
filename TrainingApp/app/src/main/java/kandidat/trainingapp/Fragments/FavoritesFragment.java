package kandidat.trainingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

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
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
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

        DatabaseReference pointRef = db.getReference("users").child(user.getUid()).child("points");
        DatabaseReference negPointRef = db.getReference("users").child(user.getUid()).child("negPoints");

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        listView = (ListView) rootView.findViewById(R.id.favorite_list);

        headerText = (TextView) rootView.findViewById(R.id.empty_favorites);


        LayoutInflater inflateheader = getLayoutInflater();
        //ViewGroup header = (ViewGroup)inflateheader.inflate(R.layout.layout_favorites_header, listView,false);


       // listView.addHeaderView(header);

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
                          //  emptyText = (TextView) h.findViewById(R.id.empty_favorites);
                            headerText.setText(getString(R.string.empty_favorites_string));
                        }
                    }else{
                        if(isAdded()) {
                       //     emptyText = (TextView) header.findViewById(R.id.empty_favorites);
                            headerText.setText(getString(R.string.explain_favorites));
                        }

                    }

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
                    points.calcualtePoints(itemClicked.getValue());

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
