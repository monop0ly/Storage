package com.example.vaibhav.storage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dto.Login;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {

    private String uniqueId;
    private String result;
    ProgressDialog dialog;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=this;
        new databaseTask().execute();

        }

    private class databaseTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPreExecute()
        {
            dialog =new ProgressDialog(MainActivity.this,ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            uniqueId=Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            FirebaseDatabase database=FirebaseDatabase.getInstance();
            final CountDownLatch latch=new CountDownLatch(1);
            DatabaseReference myRef=database.getReference(uniqueId);
            Log.d("id",uniqueId);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    result=dataSnapshot.getValue(String.class);
                    Log.d("TAG", "Value is: " + result);
                    latch.countDown();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("TAG", "Failed to read value.", databaseError.toException());
                    latch.countDown();
                }
            });

            try {
                latch.await();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String res)
        {
            dialog.hide();
            Login userLog=new Login();
            userLog.setUniqueId(uniqueId);
            Intent intent=new Intent(ctx,TaskActivity.class);
            userLog.setPassWord(result);
            intent.putExtra("loginObject",userLog);

            if(result!=null)
                intent.putExtra("Task","login");

            else
                intent.putExtra("Task","signup");


            startActivity(intent);

       }
    }
}
