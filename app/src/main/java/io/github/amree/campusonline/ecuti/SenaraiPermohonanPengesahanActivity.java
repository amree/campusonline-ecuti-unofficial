package io.github.amree.campusonline.ecuti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;


public class SenaraiPermohonanPengesahanActivity extends ActionBarActivity {

    private static final String TAG = "SenaraiPermohonanPengesahanActivity";
    public static final String APPROVE_URL= "APPROVE_URL";

    private ArrayList<DataApplication> data;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senarai_permohonan_pengesahan);

        setTitle("Pengesahan Cuti");

        // For reload if have to
        Intent intent = getIntent();
        String message = intent.getStringExtra(PermohonanPengesahanActivity.FORCE_RELOAD);

        if ((message != null) && (message.equals("true"))) {

            new CampusOnlineTask().execute();

        } else {
            loadSenaraiPermohonan();
        }
    }

    private void loadSenaraiPermohonan() {

        this.data = new ArrayList<DataApplication>();

        if (ECuti.applications.length == 0) {

            setContentView(R.layout.fragment_no_data);
            
        } else {

            for (int i = 0; i < ECuti.applications.length; i++) {

                DataApplication dataApplication = new DataApplication();

                dataApplication.setStatus(ECuti.applications[i][0]);
                dataApplication.setUrl(ECuti.applications[i][1]);
                dataApplication.setNama(ECuti.applications[i][2]);
                dataApplication.setJenis(ECuti.applications[i][3]);
                dataApplication.setMasaMinta(ECuti.applications[i][4]);

                this.data.add(dataApplication);
            }

            adapter = new CustomAdapter(this, data);
            final ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(SenaraiPermohonanPengesahanActivity.this, PermohonanPengesahanActivity.class);
                    intent.putExtra(APPROVE_URL, data.get(position).url);
                    startActivity(intent);
                }
            });
        }
    }

    private class CampusOnlineTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SenaraiPermohonanPengesahanActivity.this, "", "Loading...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            loadSenaraiPermohonan();
        }

        @Override
        protected Void doInBackground(String... params) {

            try {

                ECuti co = new ECuti();
                co.openSenaraiPermohonan();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }
    }
}
