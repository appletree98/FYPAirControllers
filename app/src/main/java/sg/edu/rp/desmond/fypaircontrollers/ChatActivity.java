package sg.edu.rp.desmond.fypaircontrollers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatActivity extends AppCompatActivity {

//    LinearLayout layout;
//    RelativeLayout layout_2;
//    ImageView sendButton;
//    EditText messageArea;
//    ScrollView scrollView;
//    Firebase reference1, reference2;
//    DatabaseReference mDatanase;
//    FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        mAuth = FirebaseAuth.getInstance();
//        layout = (LinearLayout) findViewById(R.id.layout1);
//        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
//        sendButton = (ImageView)findViewById(R.id.sendButton);
//        messageArea = (EditText)findViewById(R.id.messageArea);
//        scrollView = (ScrollView)findViewById(R.id.scrollView);
//
////        Intent i = this.getIntent();
////        final String x = i.getStringExtra("user");
////        final String chatWith = i.getStringExtra("chatwith");
//
//        Firebase.setAndroidContext(this);
//        reference1 = new Firebase("https://fyp2017-e119b.firebaseio.com/messages/");
////        reference2 = new Firebase("https://fyp2017-e119b.firebaseio.com/messages");
//
//        final String current = mAuth.getCurrentUser().getUid();
//        Toast.makeText(this, ""+current, Toast.LENGTH_SHORT).show();
//
////        mDatanase = FirebaseDatabase.getInstance().getReference().child("messages").child(x);
//
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String messageText = messageArea.getText().toString();
//
//                if(!messageText.equals("")){
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("message", messageText);
//                    map.put("user", current);
//                    reference1.push().setValue(map);
////                    reference2.push().setValue(map);
//                    messageArea.setText("");
//                }
//            }
//        });
//
//        reference1.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Map map = dataSnapshot.getValue(Map.class);
//                String message = map.get("message").toString();
//                String userName = map.get("user").toString();
//
//                if(userName.equals(current)){
//                    addMessageBox("You:-\n" + message, 1);
//                }
//                else{
//                    addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//    public void addMessageBox(String message, int type){
//        TextView textView = new TextView(ChatActivity.this);
//        textView.setText(message);
//
//        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp2.weight = 1.0f;
//
//        if(type == 1) {
//            lp2.gravity = Gravity.LEFT;
//            textView.setBackgroundResource(R.drawable.bubble_in);
//        }
//        else{
//            lp2.gravity = Gravity.RIGHT;
//            textView.setBackgroundResource(R.drawable.bubble_out);
//        }
//        textView.setLayoutParams(lp2);
//        layout.addView(textView);
//        scrollView.fullScroll(View.FOCUS_DOWN);
//    }
//}

    private Button add_room;
    private EditText room_name;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("messages");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        add_room = (Button) findViewById(R.id.btn_add_room);
//        room_name = (EditText) findViewById(R.id.room_name_edittext);
        listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_of_rooms);

        listView.setAdapter(arrayAdapter);

        Intent i = this.getIntent();
        final String userName = i.getStringExtra("name");
//        Log.i("CHATTAG", "onCreate: "+userName);

//        request_user_name();

//        add_room.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Map<String,Object> map = new HashMap<String, Object>();
//                map.put(room_name.getText().toString(),"");
//                root.updateChildren(map);
//
//            }
//        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((com.google.firebase.database.DataSnapshot)i.next()).getKey());
                }

                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),Chat_Room.class);
                intent.putExtra("room_name",((TextView)view).getText().toString() );
                intent.putExtra("user_name",userName);
                startActivity(intent);
            }
        });

    }

//    private void request_user_name() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Enter name:");
//
//        final EditText input_field = new EditText(this);
//
//        builder.setView(input_field);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                name = input_field.getText().toString();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//                request_user_name();
//            }
//        });
//
//        builder.show();
//    }


}
