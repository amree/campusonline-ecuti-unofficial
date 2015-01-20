package io.github.amree.campusonline.ecuti.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import io.github.amree.campusonline.ecuti.R;
import io.github.amree.campusonline.ecuti.adapter.PermohonanCutiAdapter;
import io.github.amree.campusonline.ecuti.parcel.StatusPermohonanParcel;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SenaraiStatusPermohonanFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static SenaraiStatusPermohonanFragment newInstance() {
        SenaraiStatusPermohonanFragment fragment = new SenaraiStatusPermohonanFragment();

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SenaraiStatusPermohonanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();

        ArrayList<StatusPermohonanParcel> data = new ArrayList<StatusPermohonanParcel>();

        for (Parcelable parcel : bundle.getParcelableArrayList("data")) {
            data.add((StatusPermohonanParcel) parcel);
        }

        PermohonanCutiAdapter adapter = new PermohonanCutiAdapter(getActivity(), data);

        setListAdapter(adapter);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast toast = Toast.makeText(getActivity(),
                "Fungsi ini sedang dibangunkan.",
                Toast.LENGTH_SHORT);

        toast.show();

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onPermohonanCutiFragmentInteraction("");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_senarai_permohonan_cuti, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_permohonan_baru) {

            Fragment fragment = null;
            fragment = new PermohonanCutiFragment();

            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("onSenaraiPermohonanCutiFragment")
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onPermohonanCutiFragmentInteraction(String url);
    }

}
