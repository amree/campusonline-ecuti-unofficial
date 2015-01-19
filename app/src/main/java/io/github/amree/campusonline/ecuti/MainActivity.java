package io.github.amree.campusonline.ecuti;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import java.io.IOException;
import java.util.ArrayList;

import io.github.amree.campusonline.ecuti.parcel.AwardWangTunaiParcel;
import io.github.amree.campusonline.ecuti.parcel.CutiDiambilParcel;
import io.github.amree.campusonline.ecuti.parcel.PermohonanCutiParcel;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
                   SenaraiPermohonanPengesahanFragment.OnFragmentInteractionListener,
                   PermohonanPengesahanFragment.OnFragmentInteractionListener,
                   CutiDiambilFragment.OnFragmentInteractionListener,
                   NoDataFragment.OnFragmentInteractionListener,
                   PermohonanCutiFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                new LoadPermohonanCutiTask().execute();
                break;
            case 1:
                fragment = new SenaraiPermohonanPengesahanFragment();
                break;
            case 2:
                new LoadCutiDiambilTask().execute();
                break;
            case 3:
                new LoadAwardWangTunaiTask().execute();
                break;
            case 4:
                break;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onCutiDiambilFragmentInteraction() {

    }

    @Override
    public void onNoDataFragmentInteraction() {

    }

    @Override
    public void onPermohonanCutiFragmentInteraction(String url) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void onSenaraiPermohonanPengesahanFragmentInteraction(String url) {
        new LoadPermohonanTask().execute(url);
    }

    @Override
    public void onPermohonanPengesahanFragmentInteraction() {
        Fragment fragment = null;
        fragment = new SenaraiPermohonanPengesahanFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("onPermohonanPengesahanFragment")
                .commit();
    }

    private class LoadPermohonanTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;
        DataApplication dataApp;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            Bundle args = new Bundle();
            args.putString("nama", dataApp.getNama());
            args.putString("jenisCuti", dataApp.getJenis());
            args.putString("tarikhCuti", dataApp.getTarikhDari() + " - " + dataApp.getTarikhHingga());
            args.putString("tempohCuti", dataApp.getJumlahHari());
            args.putString("sebabcuti", dataApp.getSebabCuti());
            args.putString("sahURL", dataApp.getUrl());

            Fragment fragment = null;
            fragment = new PermohonanPengesahanFragment();
            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("onPermohonanPengesahanFragment")
                    .commit();

        }

        @Override
        protected Void doInBackground(String... params) {

            Cuti co = new Cuti();

            try {

                this.dataApp = co.openPermohonananSah(params[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class LoadCutiDiambilTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;
        CutiDiambilParcel[] dataCutiDiambil;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            Bundle args = new Bundle();

            Fragment fragment = null;

            if (this.dataCutiDiambil.length == 0) {

                fragment = new NoDataFragment();

            } else {

                ArrayList<CutiDiambilParcel> dataList = new ArrayList<CutiDiambilParcel>();

                for (CutiDiambilParcel cutiDiambil : this.dataCutiDiambil) {
                    dataList.add(cutiDiambil);
                }

                args.putParcelableArrayList("data", dataList);

                fragment = new CutiDiambilFragment();
                fragment.setArguments(args);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("onCutiDiambilFragment")
                    .commit();

        }

        @Override
        protected Void doInBackground(String... params) {

            Cuti cuti = new Cuti();

            try {

                cuti.gotoCutiDiambil();
                this.dataCutiDiambil = cuti.getCutiDiambil();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class LoadAwardWangTunaiTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;
        AwardWangTunaiParcel[] dataAwardWangTunai;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            Bundle args = new Bundle();

            Fragment fragment = null;

            if (this.dataAwardWangTunai.length == 0) {

                fragment = new NoDataFragment();

            } else {

                ArrayList<AwardWangTunaiParcel> dataList = new ArrayList<AwardWangTunaiParcel>();

                for (AwardWangTunaiParcel awardWangTunaiParcel : this.dataAwardWangTunai) {
                    dataList.add(awardWangTunaiParcel);
                }

                args.putParcelableArrayList("data", dataList);

                fragment = new AwardWangTunaiFragment();
                fragment.setArguments(args);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("onCutiDiambilFragment")
                    .commit();

        }

        @Override
        protected Void doInBackground(String... params) {

            Cuti cuti = new Cuti();

            try {

                cuti.gotoAwardWangTunai();
                this.dataAwardWangTunai = cuti.getAwardWangTunai();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class LoadPermohonanCutiTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;
        PermohonanCutiParcel[] permohonanCutiParcel;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            Bundle args = new Bundle();

            Fragment fragment = null;

            if (this.permohonanCutiParcel.length == 0) {

                fragment = new NoDataFragment();

            } else {

                ArrayList<PermohonanCutiParcel> dataList = new ArrayList<PermohonanCutiParcel>();

                for (PermohonanCutiParcel permohonanCutiParcel : this.permohonanCutiParcel) {
                    dataList.add(permohonanCutiParcel);
                }

                args.putParcelableArrayList("data", dataList);

                fragment = new PermohonanCutiFragment();
                fragment.setArguments(args);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("onPermohonanCutiFragment")
                    .commit();

        }

        @Override
        protected Void doInBackground(String... params) {

            Cuti cuti = new Cuti();

            try {

                cuti.gotoPermohonanCuti();
                this.permohonanCutiParcel = cuti.getPermohonanCuti();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
