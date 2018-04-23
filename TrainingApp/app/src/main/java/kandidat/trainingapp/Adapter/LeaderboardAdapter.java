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

import kandidat.trainingapp.Models.LeaderboardModel;
import kandidat.trainingapp.R;

/**
 * Created by rasti on 2018-04-12.
 */

public class LeaderboardAdapter extends ArrayAdapter<LeaderboardModel> implements View.OnClickListener {
    private ArrayList<LeaderboardModel> theData;
    private FirebaseUser currentUser;
    Context theContext;

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        LeaderboardModel model = (LeaderboardModel) object;
    }

    private static class ViewHolder {
        TextView userNameTxt;
        TextView pointsTxt;
    }

    public LeaderboardAdapter(ArrayList<LeaderboardModel> data, Context context) {
        super(context, R.layout.leaderboard_representation, data);
        this.theData = data;
        this.theContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LeaderboardModel model = getItem(position);

        //Gets the user that is logged in right now
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.leaderboard_representation, parent, false);
            viewHolder.userNameTxt = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.pointsTxt = (TextView) convertView.findViewById(R.id.thePointsCollected);


            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        if(currentUser.getUid().equals(model.getUID())){

            String userName = model.getUserName() + " (You)";
            String points = model.getPoints();
            int color = Color.GREEN;

            setText(viewHolder, userName,points,color);

        }else{
            String userName = model.getUserName();
            String points = model.getPoints();
            int color = Color.WHITE;

            setText(viewHolder, userName,points,color);
        }

        return convertView;
    }

    private void setText(ViewHolder viewHolder, String userName, String points, int color) {
        viewHolder.userNameTxt.setTextColor(color);
        viewHolder.userNameTxt.setText(userName);
        viewHolder.pointsTxt.setText(points);
    }


}


