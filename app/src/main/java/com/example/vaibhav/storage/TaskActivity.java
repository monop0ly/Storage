package com.example.vaibhav.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dto.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.util.EncryptUtil;

import java.util.concurrent.CountDownLatch;

public class TaskActivity extends AppCompatActivity {

    private Login userLog;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent=getIntent();
        userLog=(Login) intent.getSerializableExtra("loginObject");
        String task=intent.getStringExtra("Task");


        Log.d("userTAsk",userLog.toString());

        btn=(Button) findViewById(R.id.task_button);


        if(task.contentEquals("login"))
        btn.setText("Login");

        else
        btn.setText("SignUp");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void getStart(View view)
    {
            btn=(Button) findViewById(R.id.task_button);
            EditText passw=(EditText) findViewById(R.id.pass_text);
            Log.d("btn",btn.getText().toString());

          if(btn.getText().toString().equals("Login"))
            {
                if(userLog.getPassWord().equals(EncryptUtil.createPassword(passw.getText().toString())))
                {
                    Log.d("TASKACTIVITYUSEROBJECT",userLog.hashCode()+"");

                    Intent intent=new Intent(this,UserAcivity.class);
                    intent.putExtra("userObject",userLog);
                    startActivity(intent);
                }


            }
        else if(btn.getText().toString().equals("SignUp"))
                {
                    userLog.setPassWord(EncryptUtil.createPassword(passw.getText().toString()));
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference myRef=database.getReference(userLog.getUniqueId());

                    myRef.setValue(userLog.getPassWord());

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Log.d("change","data is changed");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

    }

}
