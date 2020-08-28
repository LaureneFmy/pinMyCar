package com.example.pinmycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class PinsAdapter extends RecyclerView.Adapter<PinsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pinLabel;
        public TextView pinDate;
        public TextView pinLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            pinLabel = itemView.findViewById(R.id.pinLabel);
            pinDate = itemView.findViewById(R.id.pinDate);
            pinLocation = itemView.findViewById(R.id.pinLocation);
        }
    };

    private List<Pin> Pins;

    public PinsAdapter(List<Pin> pins) {
        Pins = pins;
    };

    @Override
    public PinsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View pinView = inflater.inflate(R.layout.pin_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(pinView);
        return viewHolder;
    };

    @Override
    public void onBindViewHolder(PinsAdapter.ViewHolder holder, int position) {
        Pin pin = Pins.get(position);

        TextView pinLabel = holder.pinLabel;
        pinLabel.setText((CharSequence) pin.getLabel());

        TextView pinDate = holder.pinDate;
        pinDate.setText((CharSequence) pin.getDate());

        TextView pinLocation = holder.pinLocation;
        pinLocation.setText(String.format(Locale.FRANCE, "%s , %s", pin.getLatitude(), pin.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return Pins.size();
    }
}
