package io.github.amree.campusonline.ecuti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.amree.campusonline.ecuti.R;
import io.github.amree.campusonline.ecuti.parcel.AwardWangTunaiParcel;

/**
 * Created by amree on 1/19/15.
 */
public class AwardWangTunaiAdapter extends BaseAdapter {

    Context context;
    ArrayList<AwardWangTunaiParcel> data;
    private static LayoutInflater inflater = null;

    public AwardWangTunaiAdapter(Context context, ArrayList<AwardWangTunaiParcel> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // See if the view needs to be inflated
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_three_lines, null);
        }
        // Extract the desired views
        TextView headerText = (TextView) view.findViewById(R.id.headerValue);
        TextView subHeaderText = (TextView) view.findViewById(R.id.subHeaderValue);
        TextView bodyText = (TextView) view.findViewById(R.id.bodyValue);

        // Get the data item
        AwardWangTunaiParcel item = data.get(position);

        // Display the data item's properties
        headerText.setText(item.getTahun());
        bodyText.setText(item.getBilangan());

        subHeaderText.setVisibility(View.GONE);

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
