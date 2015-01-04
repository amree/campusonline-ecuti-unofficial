package io.github.amree.campusonline.ecuti;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class PermohonanPengesahanActivity extends ActionBarActivity {

    private static final String TAG = "PermohonanPengesahanActivity";
    public static final String FORCE_RELOAD = "FORCE_RELOAD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan_pengesahan);

        setTitle("Permohonan");

        Intent intent = getIntent();
        String message = intent.getStringExtra(SenaraiPermohonanPengesahanActivity.APPROVE_URL);
        Log.d(TAG, message);

        new LoadPermohonanTask().execute(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_staff_application, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_approve) {

            AlertDialog.Builder dialogConfirmation = new AlertDialog.Builder(PermohonanPengesahanActivity.this);
            dialogConfirmation.setMessage("Adakah anda pasti anda ingin meluluskan permohonan ini?");
            dialogConfirmation.setCancelable(true);
            dialogConfirmation.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            Intent intent = getIntent();
                            String url = intent.getStringExtra(SenaraiPermohonanPengesahanActivity.APPROVE_URL);

                            new lulusPermohonanTask().execute(url);
                        }
                    });
            dialogConfirmation.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog confirm = dialogConfirmation.create();
            confirm.show();

            return true;

        } else if (id == R.id.action_reject) {

            Context context = getApplicationContext();
            CharSequence text = "Fungsi ini sedang dibangunkan.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else if (id == R.id.action_return) {

            Context context = getApplicationContext();
            CharSequence text = "Fungsi ini sedang dibangunkan.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }

        return super.onOptionsItemSelected(item);
    }

    private class lulusPermohonanTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(PermohonanPengesahanActivity.this, "", "Submitting...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(String... params) {
            ECuti co = new ECuti();

            try {

                co.doSahPermohonan(params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(PermohonanPengesahanActivity.this, SenaraiPermohonanPengesahanActivity.class);
            intent.putExtra(FORCE_RELOAD, "true");
            startActivity(intent);

            return null;
        }
    }

    private class LoadPermohonanTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;
        DataApplication dataApp;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(PermohonanPengesahanActivity.this, "", "Loading...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            TextView nama = (TextView) findViewById(R.id.textViewNamaVal);
            nama.setText(dataApp.getNama());

            TextView jenisCuti = (TextView) findViewById(R.id.textViewJenisCutiVal);
            jenisCuti.setText(dataApp.getJenis());

            String tarikhCutiVal = "";

            tarikhCutiVal = dataApp.getTarikhDari() + " - " + dataApp.getTarikhHingga();

            TextView tarikhCuti = (TextView) findViewById(R.id.textViewTarikhCutiVal);
            tarikhCuti.setText(tarikhCutiVal);

            TextView tempohCuti = (TextView) findViewById(R.id.textViewTempohCutiVal);
            tempohCuti.setText(dataApp.getJumlahHari() + " hari");

            TextView sebabCuti = (TextView) findViewById(R.id.textViewSebabCutiVal);
            sebabCuti.setText(dataApp.getSebabCuti());
        }

        @Override
        protected Void doInBackground(String... params) {

            ECuti co = new ECuti();

            try {

                this.dataApp = co.openPermohonananSah(params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
