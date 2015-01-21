package io.github.amree.campusonline.ecuti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.amree.campusonline.ecuti.R;
import io.github.amree.campusonline.ecuti.parcel.StatusPermohonanParcel;

/**
 * Created by amree on 1/19/15.
 */
public class PermohonanCutiAdapter extends BaseAdapter {

    Context context;
    ArrayList<StatusPermohonanParcel> data;
    private static LayoutInflater inflater = null;

    public PermohonanCutiAdapter(Context context, ArrayList<StatusPermohonanParcel> data) {
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
        StatusPermohonanParcel item = data.get(position);

        // Display the data item's properties
        headerText.setText(item.getJenis());
        subHeaderText.setText(item.getStatus());
        bodyText.setText(item.getTarikh());

        return view;
    }
}
