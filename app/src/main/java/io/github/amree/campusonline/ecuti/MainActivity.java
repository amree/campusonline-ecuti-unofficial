package io.github.amree.campusonline.ecuti;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;


// public class MainActivity extends ActionBarActivity {
public class MainActivity extends Activity {

    ProgressDialog progressDialog;

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doLogin(View view) {

        String email = findViewById(R.id.editTextEmail).toString();
        String password = findViewById(R.id.editTextPassword).toString();

        progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");

        new Thread() {
            public void run() {
                try {

                    CampusOnline campusOnline = new CampusOnline("", "");

                    campusOnline.gotoLogin();
                    campusOnline.doLogin();
                    campusOnline.gotoCuti();
                    campusOnline.gotoSahCuti();
                    campusOnline.setApplications();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }

                progressDialog.dismiss();
            }
        }.start();

    }
}

