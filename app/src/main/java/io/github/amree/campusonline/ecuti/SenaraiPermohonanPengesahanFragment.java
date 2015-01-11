package io.github.amree.campusonline.ecuti;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SenaraiPermohonanPengesahanFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<DataApplication> data;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SenaraiPermohonanPengesahanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new ArrayList<DataApplication>();

        for (int i = 0; i < ECuti.applications.length; i++) {

            DataApplication dataApplication = new DataApplication();

            dataApplication.setStatus(ECuti.applications[i][0]);
            dataApplication.setUrl(ECuti.applications[i][1]);
            dataApplication.setNama(ECuti.applications[i][2]);
            dataApplication.setJenis(ECuti.applications[i][3]);


            try {

                String input = ECuti.applications[i][4];
                Date date = new SimpleDateFormat("d/M/yyyy h:m:s a").parse(input);
                long milliseconds = date.getTime();

                CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(milliseconds,
                                                    System.currentTimeMillis(),
                                                    DateUtils.MINUTE_IN_MILLIS,
                                                    DateUtils.FORMAT_NO_NOON);

                dataApplication.setMasaMinta(timeAgo.toString());

            } catch (ParseException e) {
                dataApplication.setMasaMinta(ECuti.applications[i][4]);
            }

            data.add(dataApplication);
        }

        CustomAdapter adapter = new CustomAdapter(getActivity(), data);

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

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            // mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
            mListener.onSenaraiPermohonanPengesahanFragmentInteraction(data.get(position).url);
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
