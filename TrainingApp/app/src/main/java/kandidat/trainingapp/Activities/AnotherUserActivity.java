package kandidat.trainingapp.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

import kandidat.trainingapp.R;

public class AnotherUserActivity extends AppCompatActivity {
    private TextView textName,textPoints,toolText;
    private Toolbar toolbar;
    private Button friendRequest,declineRequest;
    private int currentFriendState;

    //-------------- Database ---------------
    private DatabaseReference anotherUserRef,requestRef,friendsRef;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_user);

        /*---------------------------------------------
        * ---------------Instantiate widgets-----------
        *----------------------------------------------
        * ---------------------------------------------*/
        toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolText = (TextView) toolbar.findViewById(R.id.activity_text);
        textName = (TextView) findViewById(R.id.theDisplayName);
        textPoints = (TextView) findViewById(R.id.displayPoints);
        friendRequest = (Button) findViewById(R.id.sendRequestBtn);
        declineRequest = (Button) findViewById(R.id.declineRequestBtn);
        String userId = getIntent().getStringExtra("userId");


        //Hide decline button
        declineRequest.setVisibility(View.GONE);

        /*---------------------------------------------
        * -------------- Database Stuff ---------------
        *----------------------------------------------
        * ---------------------------------------------*/
       FirebaseDatabase db = FirebaseDatabase.getInstance();
        anotherUserRef = db.getReference().child("users").child(userId);
        requestRef = db.getReference().child("friend_request");
        friendsRef = db.getReference().child("friends");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        anotherUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String theUsersName = dataSnapshot.child("displayName").getValue().toString();
                String points = dataSnapshot.child("points").getValue().toString();
                toolText.setText(theUsersName + "'s Profile");
                textName.setText(theUsersName);
                textPoints.setText("Has collected " + points +" points");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child(currentUser.getUid()).child(userId).hasChild("friend state")){
                    friendRequest.setBackgroundColor(Color.GREEN);
                    currentFriendState = 0;
                }else{
                    currentFriendState = dataSnapshot.child(currentUser.getUid()).child(userId).child("friend state").getValue(Integer.class);
                    switch(currentFriendState){
                        //Not friends
                        case 0:
                            declineRequest.setVisibility(View.GONE);
                            friendRequest.setText("Send Friend Request");
                            friendRequest.setBackgroundColor(Color.GREEN);
                            friendRequest.setEnabled(true);
                            break;
                        // Friend Request Pending
                        case 1:
                            declineRequest.setVisibility(View.GONE);
                            friendRequest.setText("Cancel The Request");
                            friendRequest.setBackgroundColor(Color.RED);
                            friendRequest.setEnabled(true);
                             break;
                        // Friend Request Received
                        case 2:
                            declineRequest.setVisibility(View.VISIBLE);
                            friendRequest.setText("Accept Friend Request");
                            friendRequest.setBackgroundColor(Color.GREEN);
                            friendRequest.setEnabled(true);
                            break;
                        //Friends
                        case 3:
                            declineRequest.setVisibility(View.GONE);
                            friendRequest.setBackgroundColor(Color.RED);
                            friendRequest.setText("Remove Friend");
                            friendRequest.setEnabled(true);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        friendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendRequest.setEnabled(false);


                // Users are not friends
                if(currentFriendState == 0){
                    requestRef.child(currentUser.getUid()).child(userId).child("friend state").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                requestRef.child(userId).child(currentUser.getUid()).child("friend state").setValue(2).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AnotherUserActivity.this,
                                                "The request wasn't sent properly",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{
                                Toast.makeText(AnotherUserActivity.this,
                                        "The request wasn't sent",Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
                // This means that a friend request is pending
                if(currentFriendState == 1){
                    requestRef.child(currentUser.getUid()).child(userId).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        requestRef.child(userId).child(currentUser.getUid()).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            requestRef.child(userId).child(currentUser.getUid()).child("friend state").setValue(0)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            requestRef.child(currentUser.getUid()).child(userId).child("friend state")
                                                                                    .setValue(0).addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(AnotherUserActivity.this,
                                                                                            "Friend State wasn't set properly",
                                                                                            Toast.LENGTH_LONG).show();
                                                                                }
                                                                            });

                                                                        }
                                                                    });


                                                        }else{
                                                            Toast.makeText(AnotherUserActivity.this,"Friend Request wasn't canceled properly.",
                                                                    Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }else{Toast.makeText(AnotherUserActivity.this,"Friend Request wasn't canceled.",
                                            Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

                //This means that user has gotten a friend request
                if(currentFriendState == 2){
                    //Currently set the value of friend to when they accepted the friend request
                    final String theCurrentDate = DateFormat.getDateTimeInstance().format(new Date());
                    friendsRef.child(currentUser.getUid()).child(userId).setValue(theCurrentDate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                friendsRef.child(userId).child(currentUser.getUid()).setValue(theCurrentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        requestRef.child(currentUser.getUid()).child(userId)
                                                .child("friend state").setValue(3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                requestRef.child(userId).child(currentUser.getUid())
                                                        .child("friend state").setValue(3).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(AnotherUserActivity.this,
                                                                "Friend State wasn't set properly",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }else{
                                Toast.makeText(AnotherUserActivity.this,"Friend Request wasn't accepted properly.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
                // This means that they are friends
                if(currentFriendState == 3){
                    friendsRef.child(currentUser.getUid()).child(userId).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        friendsRef.child(userId).child(currentUser.getUid()).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            requestRef.child(userId).child(currentUser.getUid()).child("friend state").setValue(0)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            requestRef.child(currentUser.getUid()).child(userId)
                                                                                    .child("friend state").setValue(0)
                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(AnotherUserActivity.this,
                                                                                            "Friend State wasn't set properly",
                                                                                            Toast.LENGTH_LONG).show();
                                                                                }
                                                                            });
                                                                        }
                                                                    });



                                                        }else{
                                                            Toast.makeText(AnotherUserActivity.this,"Friend wasnt removed properly.",
                                                                    Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }else{Toast.makeText(AnotherUserActivity.this,"Friend wasn't removed.",
                                            Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        declineRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRef.child(userId).child(currentUser.getUid()).child("friend state").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        requestRef.child(currentUser.getUid()).child(userId).child("friend state").setValue(0).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AnotherUserActivity.this,
                                        "Friend State wasn't set properly",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });

    }

}
