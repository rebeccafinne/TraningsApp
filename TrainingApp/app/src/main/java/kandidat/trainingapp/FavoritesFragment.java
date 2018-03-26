package kandidat.trainingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FavoritesFragment extends Fragment {

    private ListView listView;

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

        // Initializing a new String Array
        //Ta detta från databasen senare, viktigt att det är en String array
        String[] fruits = new String[] {
                "Cape Gooseberry",
                "Capuli cherry", "hej","hej","hej","hej","hej","hej","hej","hej","hej","hej","hej","hej"
        };

        // Create a List from String Array elements
        final List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_multiple_choice, fruits_list);

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);
        return rootView;
    }


}
