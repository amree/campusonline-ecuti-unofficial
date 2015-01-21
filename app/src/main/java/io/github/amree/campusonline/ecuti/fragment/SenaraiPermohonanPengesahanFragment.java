package io.github.amree.campusonline.ecuti.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import io.github.amree.campusonline.ecuti.activity.MainActivity;
import io.github.amree.campusonline.ecuti.adapter.PermohonanPengesahanAdapter;
import io.github.amree.campusonline.ecuti.parcel.StatusPermohonanPengesahanParcel;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SenaraiPermohonanPengesahanFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<StatusPermohonanPengesahanParcel> data;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SenaraiPermohonanPengesahanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();

        data = new ArrayList<StatusPermohonanPengesahanParcel>();

        for (Parcelable parcel : bundle.getParcelableArrayList("data")) {
            data.add((StatusPermohonanPengesahanParcel) parcel);
        }

        PermohonanPengesahanAdapter adapter = new PermohonanPengesahanAdapter(getActivity(), data);

        setListAdapter(adapter);
    }

    private android.support.v7.app.ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(2);

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

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            // mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
            mListener.onSenaraiPermohonanPengesahanFragmentInteraction(data.get(position).getUrl());
        }
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
        public void onSenaraiPermohonanPengesahanFragmentInteraction(String url);
    }

}
