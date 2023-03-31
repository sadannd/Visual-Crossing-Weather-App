package com.sadanand.weatherapp.weatherapp.ActivityAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sadanand.weatherapp.weatherapp.Activities.WeekActivity;
import com.sadanand.weatherapp.weatherapp.EntityModule.DaysDataBean;
import com.siddhi.weatherapp.R;

import java.util.List;

public class WeekActAdapter extends RecyclerView.Adapter<WeekActAdapter.ViewHolder>{
    WeekActivity weekActivity;
    List<DaysDataBean> daysDataList;

    final String dash="-";
    final String underscore="_";

    final String drawable="drawable";

    View view;

    @NonNull
    @Override
    public WeekActAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weekly_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekActAdapter.ViewHolder holder, int position) {

        holder.weeklyDesc.setText(daysDataList.get(position).getDesc());
        holder.weekPrecip.setText(daysDataList.get(position).getPrecipprob());
        holder.Day.setText(daysDataList.get(position).getDateAndtimeEp());
        holder.minmaxTemperature.setText(daysDataList.get(position).getTemperatureMax());

        holder.noonTemperature.setText(daysDataList.get(position).getAfternoonTime());
        holder.evenTemperature.setText(daysDataList.get(position).getEveningTime());
        holder.uvIndex.setText(daysDataList.get(position).getUvIndexes());
        holder.morningTemp.setText(daysDataList.get(position).getMorningtime());

        String image = daysDataList.get(position).getImage().replace(dash, underscore);
        int iconResId = weekActivity.getResources().getIdentifier(image,
                drawable, weekActivity.getPackageName());
        holder.imageViewer.setImageResource(iconResId);
        holder.nightTemperature.setText(daysDataList.get(position).getNightTime());

    }

    public WeekActAdapter(WeekActivity weekActivity, List<DaysDataBean> daysDataList) {
        this.daysDataList = daysDataList;
        this.weekActivity = weekActivity;
    }

    @Override
    public int getItemCount() {
        return daysDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Day;
        TextView minmaxTemperature;
        TextView weeklyDesc;
        TextView weekPrecip;
        TextView uvIndex;
        TextView morningTemp;
        TextView noonTemperature;
        TextView evenTemperature;
        TextView nightTemperature;
        ImageView imageViewer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            weeklyDesc = itemView.findViewById(R.id.weeklyDescription);
            weekPrecip = itemView.findViewById(R.id.weeklyPrecip);
            Day = itemView.findViewById(R.id.weeklyDay);
            minmaxTemperature = itemView.findViewById(R.id.minmaxTemp);
            noonTemperature = itemView.findViewById(R.id.noonTemp);
            evenTemperature = itemView.findViewById(R.id.evenTemp);
            uvIndex = itemView.findViewById(R.id.uvIndexWeekly);
            morningTemp = itemView.findViewById(R.id.mornTemp);
            nightTemperature = itemView.findViewById(R.id.nightTemp);
            imageViewer = itemView.findViewById(R.id.imageView);
        }
    }
}
