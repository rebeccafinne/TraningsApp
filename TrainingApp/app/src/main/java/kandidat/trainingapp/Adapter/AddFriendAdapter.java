package kandidat.trainingapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import kandidat.trainingapp.Models.AddFriendModel;
import kandidat.trainingapp.Models.LeaderboardModel;
import kandidat.trainingapp.R;

/**
 * Created by rasti on 2018-04-14.
 */

public class AddFriendAdapter extends ArrayAdapter<AddFriendModel> implements View.OnClickListener {

    private ArrayList<AddFriendModel> theData;
    private FirebaseUser theCurrenUser = FirebaseAuth.getInstance().getCurrentUser();
    Context theContext;

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        AddFriendModel model = (AddFriendModel) object;
    }

    private static class ViewHolder {
        TextView userNameTxt;
        TextView pointsTxt;
    }

    public AddFriendAdapter(ArrayList<AddFriendModel> data, Context context) {
        super(context, R.layout.alluser_representation, data);
        this.theData = data;
        this.theContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AddFriendModel model = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        AddFriendAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new AddFriendAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.leaderboard_representation, parent, false);
            viewHolder.userNameTxt = (TextView) convertView.findViewById(R.id.txt_name);



            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (AddFriendAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        if(theCurrenUser.getUid().equals(model.getUID())){
            viewHolder.userNameTxt.setText(model.getUserName() + " (You)");


        }else{
            viewHolder.userNameTxt.setText(model.getUserName());

        }
        viewHolder.userNameTxt.setTag(position);
        viewHolder.userNameTxt.setOnClickListener(this);
        return convertView;
    }

}
