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


public class ListStaffApplicationActivity extends ActionBarActivity {

    private static final String TAG = "ListStaffApplicationActivity";
    public static final String APPROVE_URL= "APPROVE_URL";

    private ArrayList<DataApplication> data;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_staff_application);

        setTitle("Pengesahan Cuti");

        // For reload if have to
        Intent intent = getIntent();
        String message = intent.getStringExtra(StaffApplicationActivity.FORCE_RELOAD);

        if ((message != null) && (message.equals("true"))) {

            new CampusOnlineTask().execute();

        } else {
            loadSenaraiPermohonan();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
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

    private void loadSenaraiPermohonan() {

        this.data = new ArrayList<DataApplication>();

        for (int i = 0; i < CampusOnline.applications.length; i++) {

            DataApplication dataApplication = new DataApplication();

            dataApplication.setStatus(CampusOnline.applications[i][0]);
            dataApplication.setUrl(CampusOnline.applications[i][1]);
            dataApplication.setNama(CampusOnline.applications[i][2]);
            dataApplication.setJenis(CampusOnline.applications[i][3]);
            dataApplication.setMasaMinta(CampusOnline.applications[i][4]);

            this.data.add(dataApplication);
        }

        adapter = new CustomAdapter(this, data);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListStaffApplicationActivity.this, StaffApplicationActivity.class);
                intent.putExtra(APPROVE_URL, data.get(position).url);
                startActivity(intent);
            }
        });
    }

    private class CampusOnlineTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ListStaffApplicationActivity.this, "", "Loading...");
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

                CampusOnline co = new CampusOnline();
                co.openSenaraiPermohonan();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }
    }
}
