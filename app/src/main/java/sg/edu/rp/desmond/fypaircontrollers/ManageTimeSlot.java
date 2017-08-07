package sg.edu.rp.desmond.fypaircontrollers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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


        Intent i = this.getIntent();
        final String gateID = i.getStringExtra("gateName");

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.item_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Date> DateSearchList = new ArrayList<Date>();
                ListView lvSearch;

                lvTime = (ListView)findViewById(R.id.lvTimeSlot);
                for (Date date : times) {
                    if (date.getDate().toLowerCase().contains(newText.toLowerCase())) {
                        DateSearchList.add(date);
                        lvTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Date date = DateSearchList.get(position);
                                Intent intent = new Intent(getApplicationContext(), ManageTimeSlot.class);
                                intent.putExtra("date", date.getDate());
                                intent.putExtra("gateID", date.getGateID());
                                startActivity(intent);
                            }
                        });
                    }
                }

                adapter = new DateAdapter(ManageTimeSlot.this, DateSearchList);
                lvTime.setAdapter(adapter);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}


