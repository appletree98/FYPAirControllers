package sg.edu.rp.desmond.fypaircontrollers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ManageGate extends AppCompatActivity {

    ListView lvGate;

    DatabaseReference mDatabase;
    ArrayList<Gate> gates = new ArrayList<>();
    GateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_gate);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        lvGate = (ListView)findViewById(R.id.lvGate);

        adapter = new GateAdapter(this, retrieve());
        lvGate.setAdapter(adapter);



    }


    public ArrayList<Gate> retrieve(){

        mDatabase.child("Gate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Gate gate = ds.getValue(Gate.class);
                    gates.add(gate);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return gates;

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
                final List<Gate> gateSearchList = new ArrayList<Gate>();
                ListView lvSearch;

                lvSearch = (ListView)findViewById(R.id.lvGate);
                for (Gate gate : gates) {
                    if (gate.getGateName().toLowerCase().contains(newText.toLowerCase())) {
                        gateSearchList.add(gate);
                        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Gate gate = gateSearchList.get(position);
                                Intent intent = new Intent(getApplicationContext(), ManageTimeSlot.class);
                                intent.putExtra("terminalname", gate.getTerminalName());
                                intent.putExtra("gatename", gate.getGateName());
                                startActivity(intent);
                            }
                        });
                    }
                }

                adapter = new GateAdapter(ManageGate.this, gateSearchList);
                lvSearch.setAdapter(adapter);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
