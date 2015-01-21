package io.github.amree.campusonline.ecuti.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

import io.github.amree.campusonline.ecuti.activity.MainActivity;
import io.github.amree.campusonline.ecuti.adapter.AwardWangTunaiAdapter;
import io.github.amree.campusonline.ecuti.parcel.AwardWangTunaiParcel;

/**
 * A fragment representing a list of Items.
 */
public class AwardWangTunaiFragment extends ListFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AwardWangTunaiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();

        ArrayList<AwardWangTunaiParcel> data = new ArrayList<AwardWangTunaiParcel>();

        for (Parcelable parcel : bundle.getParcelableArrayList("data")) {
            data.add((AwardWangTunaiParcel) parcel);
        }

        AwardWangTunaiAdapter adapter = new AwardWangTunaiAdapter(getActivity(), data);

        setListAdapter(adapter);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(4);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
