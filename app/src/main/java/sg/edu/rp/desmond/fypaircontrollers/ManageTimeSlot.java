package sg.edu.rp.desmond.fypaircontrollers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageTimeSlot extends AppCompatActivity {

    FloatingActionButton fab;
    ListView lvTime;
    EditText etSearch;
    Button btnSearch;

    DatabaseReference mDatabase;
    ArrayList<Date> times = new ArrayList<>();
    DateAdapter adapter;
    java.util.Date todayDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_time_slot);

//        fab = (FloatingActionButton)findViewById(R.id.fab);
        lvTime = (ListView)findViewById(R.id.lvTimeSlot);
//        etSearch = (EditText)findViewById(R.id.etSearchDate);
//        btnSearch = (Button)findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = etSearch.getText().toString();

            }
        });

        Intent i = this.getIntent();
        final String gateID = i.getStringExtra("gateID");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Gate").child(gateID).child("DaySlot");

        adapter = new DateAdapter(this, retrieve());
        lvTime.setAdapter(adapter);



    }
    public ArrayList<Date> retrieve(){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Date date = ds.getValue(Date.class);
                    times.add(date);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return times;

    }

}


