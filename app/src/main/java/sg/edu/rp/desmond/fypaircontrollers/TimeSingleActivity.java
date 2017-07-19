package sg.edu.rp.desmond.fypaircontrollers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;


public class TimeSingleActivity extends AppCompatActivity {

    EditText etDate,etDirection,etTime,etFlight,etPlane;
    Button btnEdit;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_single);

        etDate = (EditText)findViewById(R.id.etDate);
        etDirection = (EditText)findViewById(R.id.etDirection);
        etTime = (EditText)findViewById(R.id.etTime);
        etFlight = (EditText)findViewById(R.id.etFlight);
        etPlane = (EditText)findViewById(R.id.etPlane);

        Intent i = this.getIntent();
        String date = i.getStringExtra("date");
        String direction = i.getStringExtra("direction");
        String time = i.getStringExtra("time");
        String flight = i.getStringExtra("flight");
        String plane = i.getStringExtra("plane");
        final String id = i.getStringExtra("id");

        etDate.setText(date);
        etDirection.setText(direction);
        etTime.setText(time);
        etFlight.setText(flight);
        etPlane.setText(plane);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updated = etDirection.getText().toString();

                mDatabase.child(id).child("Timeslot").setValue(updated);
            }
        });


    }
}
