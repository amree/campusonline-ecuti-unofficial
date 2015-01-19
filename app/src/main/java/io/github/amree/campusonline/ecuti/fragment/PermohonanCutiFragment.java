package io.github.amree.campusonline.ecuti.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import io.github.amree.campusonline.ecuti.adapter.PermohonanCutiAdapter;
import io.github.amree.campusonline.ecuti.parcel.PermohonanCutiParcel;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PermohonanCutiFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static PermohonanCutiFragment newInstance() {
        PermohonanCutiFragment fragment = new PermohonanCutiFragment();

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PermohonanCutiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();

        ArrayList<PermohonanCutiParcel> data = new ArrayList<PermohonanCutiParcel>();

        for (Parcelable parcel : bundle.getParcelableArrayList("data")) {
            data.add((PermohonanCutiParcel) parcel);
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
