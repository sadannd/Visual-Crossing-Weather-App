package com.sadanand.weatherapp.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sadanand.weatherapp.weatherapp.ActivityAdapter.WeekActAdapter;
import com.sadanand.weatherapp.weatherapp.EntityModule.DataBean;
import com.sadanand.weatherapp.weatherapp.EntityModule.DaysDataBean;
import com.sadanand.weatherapp.weatherapp.EntityModule.HourlyDataBean;
import com.siddhi.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeekActivity extends AppCompatActivity {
    RecyclerView recycler;
    DataBean bean;
    WeekActAdapter weeklyActivity;
    List<DaysDataBean> daysBeansList = new ArrayList<>();
    final String data="data";

    final String fifteendays=" 15 Day";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        Date dateAndTime;

        Bundle bundle =getIntent().getBundleExtra(data);
        bean = (DataBean) bundle.getSerializable(data);
        ArrayList<DaysDataBean> daysDataList = (ArrayList<DaysDataBean>) bean.getDaysList();

        setTitle(bean.getAddr()+ fifteendays);
        String format="EEEE, MM/dd";

        SimpleDateFormat dayDateF;
        dayDateF=new SimpleDateFormat(format, Locale.getDefault());

        for(int i = 0; i < daysDataList.size(); i++){
            ArrayList<HourlyDataBean> hourlyArrayBeanList = (ArrayList<HourlyDataBean>) daysDataList.get(i).getHourlyList();

            dateAndTime = new Date(Long.parseLong(daysDataList.get(i).getDateAndtimeEp()) * 1000);
            String temp8=hourlyArrayBeanList.get(8).getTemperature();
            String temp13=hourlyArrayBeanList.get(13).getTemperature();
            String temp17=hourlyArrayBeanList.get(17).getTemperature();
            String temp23=hourlyArrayBeanList.get(22).getTemperature();
            String icon= daysDataList.get(i).getImage();
            String tempMax=daysDataList.get(i).getTemperatureMax()+"/"+daysDataList.get(i).getTemperatureMin();
            String prob="("+daysDataList.get(i).getPrecipprob()+"% precip.)";

            DaysDataBean daysBean = new DaysDataBean(
                    dayDateF.format(dateAndTime),
                    tempMax,
                    daysDataList.get(i).getDesc(),
                    prob,
                    "UV Index: "+ daysDataList.get(i).getUvIndexes(),
                    icon, temp8, temp13, temp17, temp23
            );
            daysBeansList.add(daysBean);
            if(String.valueOf(daysBeansList.size()).matches("15"))
                break;
        }

        weeklyActivity = new WeekActAdapter(this, daysBeansList);
        recycler = findViewById(R.id.recyclerWeek);

        recycler.setAdapter(weeklyActivity);
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
}