package sg.edu.rp.desmond.fypaircontrollers;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;


public class TimeSingleActivity extends AppCompatActivity {

    TextView etDate,etDirection,etTime,etFlight,etPlane;
    Button btnEdit;
    Spinner spinner;

    DatabaseReference mDatabase;

    String a = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_single);

        etDate = (TextView)findViewById(R.id.etDate);
//        etDirection = (EditText)findViewById(R.id.etDirection);
        etTime = (TextView)findViewById(R.id.etTime);
        etFlight = (TextView)findViewById(R.id.etFlight);
        etPlane = (TextView)findViewById(R.id.etPlane);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        spinner = (Spinner)findViewById(R.id.spinner);

        Intent i = this.getIntent();
        final String date = i.getStringExtra("date");
//        String direction = i.getStringExtra("direction");
        final String time = i.getStringExtra("time");
        String flight = i.getStringExtra("flight");
        String plane = i.getStringExtra("plane");
        final String gateID = i.getStringExtra("gateID");


        etDate.setText(date);
//        etDirection.setText(direction);
        etTime.setText(time);
        etFlight.setText(flight);
        etPlane.setText(plane);


        ArrayAdapter<String> aaDirection = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.directions));
        spinner.setAdapter(aaDirection);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Gate").child(gateID).child("DaySlot").child(date).child("Flight");
//                String updated = etDirection.getText().toString();


                if(spinner.getSelectedItemPosition()==0){
                    a = "Left";
                }else{
                    a = "Right";
                }

                mDatabase.child(time).child("direction").setValue(a);
            }
        });


    }
}
