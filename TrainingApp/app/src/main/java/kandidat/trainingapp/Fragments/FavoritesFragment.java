package kandidat.trainingapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kandidat.trainingapp.Activities.AppMainActivity;
import kandidat.trainingapp.Adapter.FavoriteAdapter;
import kandidat.trainingapp.Models.Favorites;
import kandidat.trainingapp.Repositories.FavoriteData;
import kandidat.trainingapp.Models.FavoriteModel;
import kandidat.trainingapp.R;


public class FavoritesFragment extends Fragment {

    private ListView listView;
    private FavoriteData favoriteData;
    private FirebaseDatabase db;

    private DatabaseReference ref;

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

        favoriteData = (FavoriteData) getActivity().getApplicationContext();
        db = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref = db.getReference("favorites");
        DatabaseReference myRef = ref.child(user.getUid());

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
               //HashMap<String, Object> dsVal = new HashMap<>();

                for(DataSnapshot ds : dataSnapshot.child("BusStop").getChildren()){
                    System.out.println(ds.getValue());
                    Integer dsVal = (int) (long) ds.getValue();
                    bus.add(new FavoriteModel(busStops, dsVal));

                }
                for(DataSnapshot ds : dataSnapshot.child("Stairs").getChildren()){
                    System.out.println(ds.getValue());
                    Integer dsVal = (int) (long) ds.getValue();
                    stairsList.add(new FavoriteModel(stairs, dsVal));

                }
                for(DataSnapshot ds : dataSnapshot.child("standing").getChildren()){
                    System.out.println(ds.getValue());
                    Integer dsVal = (int) (long) ds.getValue();
                    standList.add(new FavoriteModel(stand, dsVal));

                }

                allFavoriteItems.clear();
                allFavoriteItems.addAll(bus);
                allFavoriteItems.addAll(stairsList);
                allFavoriteItems.addAll(standList);

                System.out.println("All favorites now: " + allFavoriteItems);
                System.out.println("All bus favorites now: " + favoriteData.getBusList());

                FavoriteAdapter mAdapter = new FavoriteAdapter(getContext(), R.layout.layout_favorite_row, R.id.activity_text, allFavoriteItems);

                listView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Initializing a new String Array
        //Ta detta från databasen senare, viktigt att det är en String array
       /* String[] fruits = new String[] {
                "Cape Gooseberry",
                "Capuli cherry", "hej","hej","hej","hej","hej","hej","hej","hej","hej","hej","hej","hej"
        };*/
      /* Integer[] favorites = new Integer[favoriteData.getBusList().size()];
       String tmp;
        for (int i = 0; i <= favoriteData.getBusList().size(); i++) {
           // tmp = favoriteData.getBusList().get(i).toString();
            favorites[i] = favoriteData.getBusList().get(i);
        }


        // Create a List from String Array elements
        final List<Integer> bus_list = new ArrayList<Integer>(favorites);

        // Create an ArrayAdapter from List
        final ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>()
                (getContext(), android.R.layout.simple_list_item_multiple_choice, favoriteData.getBusList());*/

        // DataBind ListView with items from ArrayAdapter
    //    listView.setAdapter(arrayAdapter);


    /*    ArrayList<FavoriteModel> bus = new ArrayList<FavoriteModel>();
        ArrayList<FavoriteModel> floorsItems = new ArrayList<FavoriteModel>();
        ArrayList<FavoriteModel> standingItems = new ArrayList<FavoriteModel>();
        bus.clear();
        floorsItems.clear();
        standingItems.clear();



        //Make a listview with the bus favorites if not null
        if(favoriteData.getBusList() != null && favoriteData.getBusList().size() != 0){
            String busStops = "Walked bus stops";
            Integer[] busFavoritesValues = new Integer[favoriteData.getBusList().size()];
            for(int i = 0; i < favoriteData.getBusList().size(); i++){
                //tmp = favoriteData.getBusList().get(i).toString();
                busFavoritesValues[i] = favoriteData.getBusList().get(i);
            }
            // construct the list of model object for your rows:

            for (int i = 0; i < busFavoritesValues.length; i++) {
                bus.add(new FavoriteModel(busStops, busFavoritesValues[i]));// I guess the counter starts at 0
            }
        }


        if(favoriteData.getStairsList() != null && favoriteData.getStairsList().size() != 0){
            String floorsString = "Walked flight of stairs";
            Integer[] floorsFavoritesValues = new Integer[favoriteData.getStairsList().size()];
            for(int i = 0; i < favoriteData.getStairsList().size(); i++){
                //tmp = favoriteData.getBusList().get(i).toString();
                floorsFavoritesValues[i] = favoriteData.getStairsList().get(i);
            }
            // construct the list of model object for your rows:
            for (int i = 0; i < floorsFavoritesValues.length; i++) {
                floorsItems.add(new FavoriteModel(floorsString, floorsFavoritesValues[i]));// I guess the counter starts at 0
            }
        }

        if(favoriteData.getStandingList() != null && favoriteData.getStandingList().size() != 0){
            String standingString = "Minutes standing up";
            Integer[] standingFavoritesValues = new Integer[favoriteData.getStandingList().size()];
            for(int i = 0; i < favoriteData.getStandingList().size(); i++){
                standingFavoritesValues[i] = favoriteData.getStandingList().get(i);
            }
            // construct the list of model object for your rows:
            for (int i = 0; i < standingFavoritesValues.length; i++) {
                standingItems.add(new FavoriteModel(standingString, standingFavoritesValues[i]));// I guess the counter starts at 0
            }
        }
*/




        return rootView;
    }


}
