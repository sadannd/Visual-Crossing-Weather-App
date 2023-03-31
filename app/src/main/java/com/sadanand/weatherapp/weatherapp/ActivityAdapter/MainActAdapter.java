package com.sadanand.weatherapp.weatherapp.ActivityAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sadanand.weatherapp.weatherapp.EntityModule.HourlyDataBean;
import com.sadanand.weatherapp.weatherapp.MainActivity;
import com.siddhi.weatherapp.R;

import java.util.ArrayList;

public class MainActAdapter extends RecyclerView.Adapter<MainActAdapter.ViewHolder> {
    View view;
    ArrayList<HourlyDataBean> hourlyRecycler;
    MainActivity mainActivity;
    final String dash="-";
    final String underscore="_";

    final String drawable="drawable";

    public MainActAdapter(MainActivity mainActivity, ArrayList<HourlyDataBean> hourlyRecycler) {
        this.hourlyRecycler = hourlyRecycler;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MainActAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActAdapter.ViewHolder holder, int position) {
        String image = hourlyRecycler.get(position).getImage().replace(dash, underscore);
        holder.temperature.setText(hourlyRecycler.get(position).getTemperature());
        holder.day.setText(hourlyRecycler.get(position).getDateAndtime());
        holder.time.setText(hourlyRecycler.get(position).getTime());
        holder.description.setText(hourlyRecycler.get(position).getCond());
        int iconResId = mainActivity.getResources().getIdentifier(image,
                drawable, mainActivity.getPackageName());
        holder.image.setImageResource(iconResId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        TextView time;

        TextView temperature;

        TextView description;

        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            temperature = itemView.findViewById(R.id.temp);
            description = itemView.findViewById(R.id.desc);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.icon);
        }
    }
    @Override
    public int getItemCount() {
        return hourlyRecycler.size();
    }
}
