package io.github.amree.campusonline.ecuti.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import io.github.amree.campusonline.ecuti.R;
import io.github.amree.campusonline.ecuti.library.Cuti;
import io.github.amree.campusonline.ecuti.library.PermohonanCutiException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PermohonanCutiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PermohonanCutiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PermohonanCutiFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PermohonanCutiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PermohonanCutiFragment newInstance() {
        PermohonanCutiFragment fragment = new PermohonanCutiFragment();
        return fragment;
    }

    public PermohonanCutiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_permohonan_cuti, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.jenisCutiSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.jenis_cuti_array, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        EditText editTextFromDate = (EditText) v.findViewById(R.id.editTextDari);
        EditText editTextToDate = (EditText) v.findViewById(R.id.editTextHingga);

        editTextFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerFragment((EditText) v).show(getFragmentManager(), "datePicker");
            }
        });

        editTextToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerFragment((EditText) v).show(getFragmentManager(), "datePicker");
            }
        });

        Button btnHantarPermohonan = (Button) v.findViewById(R.id.btnHantarPermohonan);

        btnHantarPermohonan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new HantarPermohonanCutiTask().execute();
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPermohonanCutiFragmentInteraction();
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

    private class HantarPermohonanCutiTask extends AsyncTask<String, Void, Void> {

        ProgressDialog progressDialog;

        String errorMsg = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(), "", "Sedang dihantar...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();

            if (this.errorMsg != null) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Ralat")
                        .setMessage(this.errorMsg)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {

                Toast toast = Toast.makeText(getActivity(),
                        "Permohonan cuti telah dihantar.",
                        Toast.LENGTH_SHORT);

                toast.show();

                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    // mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
                    mListener.onPermohonanCutiFragmentInteraction();
                }
            }
        }

        @Override
        protected Void doInBackground(String... params) {

            Cuti cuti = new Cuti();

            try {

                cuti.doHantarPermohonanCuti();

            } catch (PermohonanCutiException e) {

                this.errorMsg = e.getMessage();

            } catch (IOException e) {

                e.printStackTrace();
            }

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
        public void onPermohonanCutiFragmentInteraction();
    }
}
