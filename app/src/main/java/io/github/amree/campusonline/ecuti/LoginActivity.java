package io.github.amree.campusonline.ecuti;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;


// public class MainActivity extends ActionBarActivity {
public class LoginActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void doLogin(View view) {

        // Hide the keyboard first
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(
                (null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        EditText email = (EditText) findViewById(R.id.editTextEmail);
        EditText password = (EditText) findViewById(R.id.editTextPassword);

        if ((email.getText().toString().trim().isEmpty()) ||
                (!email.getText().toString().trim().endsWith("@usm.my"))) {

            Context context = getApplicationContext();
            CharSequence text = "Sila isi email @usm.my anda.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        if (password.getText().toString().trim().isEmpty()) {

            Context context = getApplicationContext();
            CharSequence text = "Sila isi kata laluan anda.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            return;
        }

        // Everything passed, let's try logging in
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

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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

