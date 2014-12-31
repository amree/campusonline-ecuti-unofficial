package io.github.amree.campusonline.ecuti;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;


// public class MainActivity extends ActionBarActivity {
public class LoginActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        EditText email = (EditText) findViewById(R.id.editTextEmail);
        EditText password = (EditText) findViewById(R.id.editTextPassword);

        new CampusOnlineTask().execute(email.getText().toString(),
                                       password.getText().toString());

    }

    private class CampusOnlineTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;

        private Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(LoginActivity.this, "", "Loading...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            if (this.exception != null) {

                AlertDialog msgDialog = new AlertDialog.Builder(LoginActivity.this).create();
                msgDialog.setTitle("Ralat");

                if (this.exception.getClass() == LoginException.class) {
                    msgDialog.setMessage(this.exception.getMessage());
                } else {
                    msgDialog.setMessage("Terdapat masalah. Sila cuba sekali lagi.");
                }

                msgDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                msgDialog.show();
            }
        }

        @Override
        protected Void doInBackground(String... params) {

            ECuti eCuti = new ECuti(params[0], params[1]);

            try {

                eCuti.gotoLogin();
                eCuti.doLogin();
                eCuti.gotoCuti();
                eCuti.gotoSahCuti();
                eCuti.setApplications();

                Intent intent = new Intent(LoginActivity.this, ListStaffApplicationActivity.class);
                startActivity(intent);

            } catch (IOException e) {

                this.exception = e;

            } catch (LoginException e) {

                this.exception = e;
            }

            return null;

        }
    }
}

