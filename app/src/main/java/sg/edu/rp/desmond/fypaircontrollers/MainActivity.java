package sg.edu.rp.desmond.fypaircontrollers;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;


    private DatabaseReference mDatabaseCurrentUser;

    private DownloadManager.Query mQuery;

    ArrayList<User> users = new ArrayList<>();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnMG, btnMsg;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMG = (Button)findViewById(R.id.btnManageGate);
        btnMsg = (Button)findViewById(R.id.btnMessage);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                } else {

                    String currentUserId = mAuth.getCurrentUser().getUid();
                    mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                    retrieve();

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        btnMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ManageGateIntent = new Intent(MainActivity.this, ManageGate.class);
                ManageGateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ManageGateIntent);

            }
        });

        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(MainActivity.this, ChatActivity.class);
                x.putExtra("name",name);
                startActivity(x);
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if(item.getItemId() == R.id.action_logout){

            logout();

        }


        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        mAuth.signOut();

    }
    public ArrayList<User> retrieve(){

        mDatabaseCurrentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                if(user.getRole().equals("Air Traffic Controller")){
                    Toast.makeText(MainActivity.this, "Welcome " + user.getName(), Toast.LENGTH_SHORT).show();
                    name = user.getName().toString();
                    Log.i("TAG", "onDataChange: "+name);
                } else {
                    Toast.makeText(MainActivity.this, "Error your account is not eligible to login", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return users;

    }
}
