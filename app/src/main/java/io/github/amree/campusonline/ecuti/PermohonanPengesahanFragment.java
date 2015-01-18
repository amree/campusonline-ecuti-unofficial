package io.github.amree.campusonline.ecuti;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PermohonanPengesahanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PermohonanPengesahanFragment extends Fragment {
    private String sahURL;

    private OnFragmentInteractionListener mListener;

    public PermohonanPengesahanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_permohonan_pengesahan, container, false);

        ((TextView) v.findViewById(R.id.nameValue2)).setText(getArguments().getString("nama"));
        ((TextView) v.findViewById(R.id.jenisValue2)).setText(getArguments().getString("jenisCuti"));
        ((TextView) v.findViewById(R.id.fromToValue2)).setText(getArguments().getString("tarikhCuti"));
        ((TextView) v.findViewById(R.id.durationValue2)).setText(getArguments().getString("tempohCuti"));
        ((TextView) v.findViewById(R.id.reasonValue2)).setText(getArguments().getString("sebabcuti"));

        if ((getArguments().getString("sebabCuti") == null) || getArguments().getString("sebabCuti").isEmpty()) {
            ((TextView) v.findViewById(R.id.reasonValue2)).setText("-");
        } else {
            ((TextView) v.findViewById(R.id.reasonValue2)).setText(getArguments().getString("sebabcuti"));
        }

        this.sahURL = getArguments().getString("sahURL");

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_approve) {

            AlertDialog.Builder dialogConfirmation = new AlertDialog.Builder(getActivity());
            dialogConfirmation.setMessage("Adakah anda pasti anda ingin meluluskan permohonan ini?");
            dialogConfirmation.setCancelable(true);
            dialogConfirmation.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            new lulusPermohonanTask().execute(sahURL);
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

            CharSequence text = "Fungsi ini sedang dibangunkan.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getActivity(), text, duration);
            toast.show();

        } else if (id == R.id.action_return) {

            CharSequence text = "Fungsi ini sedang dibangunkan.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getActivity(), text, duration);
            toast.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_staff_application, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPermohonanPengesahanFragmentInteraction();
        }
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

    private class lulusPermohonanTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(), "", "Submitting...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                // mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
                mListener.onPermohonanPengesahanFragmentInteraction();
            }
        }

        @Override
        protected Void doInBackground(String... params) {
//            Cuti co = new Cuti();
//
//            try {
//
//                co.doSahPermohonan(params[0]);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            System.out.println("Params: " + params[0]);

            // TODO:
            // Need to mark everything is OK before returning
            // Will effect onPostExecute

            return null;
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
        public void onPermohonanPengesahanFragmentInteraction();
    }
}
