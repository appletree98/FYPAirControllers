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


    ListView lvTime;

    DatabaseReference mDatabase;
    ArrayList<TimeSlot> times = new ArrayList<>();
    TimeSlotAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_time_slot);

        lvTime = (ListView)findViewById(R.id.lvTimeSlot);


        Intent i = this.getIntent();
        final String id = i.getStringExtra("id");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Gate").child(id).child("TimeSlot");

        adapter = new TimeSlotAdapter(this, retrieve());
        lvTime.setAdapter(adapter);



    }
    public ArrayList<TimeSlot> retrieve(){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    TimeSlot time = ds.getValue(TimeSlot.class);
                    times.add(time);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return times;

    }

}
