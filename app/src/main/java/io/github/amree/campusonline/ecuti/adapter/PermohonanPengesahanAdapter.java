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
import io.github.amree.campusonline.ecuti.parcel.StatusPermohonanPengesahanParcel;

/**
 * Created by amree on 1/21/15.
 */
public class PermohonanPengesahanAdapter extends BaseAdapter {
    Context context;
    ArrayList<StatusPermohonanPengesahanParcel> data;
    private static LayoutInflater inflater = null;

    public PermohonanPengesahanAdapter(Context context, ArrayList<StatusPermohonanPengesahanParcel> data) {
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
            view = inflater.inflate(R.layout.list_item_four_lines, null);
        }
        // Extract the desired views
        TextView headerText = (TextView) view.findViewById(R.id.headerValue);
        TextView subHeaderText = (TextView) view.findViewById(R.id.subHeaderValue);
        TextView mainBodyText = (TextView) view.findViewById(R.id.mainBodyValue);
        TextView bodyText = (TextView) view.findViewById(R.id.bodyValue);

        // Get the data item
        StatusPermohonanPengesahanParcel item = data.get(position);

        // Display the data item's properties
        headerText.setText(item.getNama());
        subHeaderText.setText(item.getJenis());
        mainBodyText.setText(item.getStatus());
        bodyText.setText(item.getMasa());

        return view;
    }
}
