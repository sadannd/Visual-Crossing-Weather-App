package com.sadanand.weatherapp.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sadanand.weatherapp.weatherapp.Activities.WeekActivity;
import com.sadanand.weatherapp.weatherapp.ActivityAdapter.MainActAdapter;
import com.sadanand.weatherapp.weatherapp.EntityModule.CurrConditionBean;
import com.sadanand.weatherapp.weatherapp.EntityModule.DataBean;
import com.sadanand.weatherapp.weatherapp.EntityModule.DaysDataBean;
import com.sadanand.weatherapp.weatherapp.EntityModule.HourlyDataBean;
import com.siddhi.weatherapp.R;
import com.sadanand.weatherapp.weatherapp.Services.DataService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    TextView desc;
    TextView dirWind;

    TextView currHumidity;
    TextView currUVIndex;

    TextView dateInformation;

    TextView currentTemperature;

    TextView currentFeelslike;

    TextView currVision;


    TextView tempEvening;

    TextView tempeNight;
    RecyclerView recyclerView;

    ImageView currImage;
    TextView tempMorning;
    TextView tempAfternoon;
    TextView  thisSunset;
    TextView thisSunrise;

    DataBean data;
    String location = "Chicago, Illinois";
    String units = "us";
    MainActAdapter mainActAdapter;
    SwipeRefreshLayout swipeRefresh;
    Date date;

    ArrayList<DaysDataBean> daysBeanList ;
    ArrayList<HourlyDataBean> hourlyBeanList ;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTemperature = findViewById(R.id.currentTemp);
        currentFeelslike = findViewById(R.id.currentFeels);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        dateInformation = findViewById(R.id.dateInfo);

        currHumidity = findViewById(R.id.currentHumidity);
        currUVIndex = findViewById(R.id.currentUVIndex);
        desc = findViewById(R.id.description);
        dirWind = findViewById(R.id.directionWind);

        tempAfternoon = findViewById(R.id.temperatureAtAfternoon);
        tempEvening = findViewById(R.id.temperatureAtEvening);
        currVision = findViewById(R.id.currentVisibility);
        tempMorning = findViewById(R.id.temperatureAtMorning);

        thisSunset = findViewById(R.id.todaysSunset);
        currImage = findViewById(R.id.currentIcon);
        tempeNight = findViewById(R.id.temperatureAtNight);
        thisSunrise = findViewById(R.id.todaysSunrise);
        recyclerView = findViewById(R.id.recyclerWeek);

        if(ConnectionCheck()){
            setContentView(R.layout.activity_main);

            currentFeelslike = findViewById(R.id.currentFeels);
            desc = findViewById(R.id.description);
            dateInformation = findViewById(R.id.dateInfo);
            currentTemperature = findViewById(R.id.currentTemp);

            currUVIndex = findViewById(R.id.currentUVIndex);
            currVision = findViewById(R.id.currentVisibility);
            dirWind = findViewById(R.id.directionWind);
            currHumidity = findViewById(R.id.currentHumidity);

            tempEvening = findViewById(R.id.temperatureAtEvening);
            tempeNight = findViewById(R.id.temperatureAtNight);
            tempMorning = findViewById(R.id.temperatureAtMorning);
            tempAfternoon = findViewById(R.id.temperatureAtAfternoon);

            currImage = findViewById(R.id.currentIcon);
            recyclerView = findViewById(R.id.recyclerWeek);
            thisSunrise = findViewById(R.id.todaysSunrise);
            thisSunset = findViewById(R.id.todaysSunset);
            String SaveData="SaveData";
            SharedPreferences sharedPref = getSharedPreferences(SaveData, MODE_PRIVATE);
            location = sharedPref.getString("location", location);
            units = sharedPref.getString("unit", units);
            DataService.fetchData(this, location, units);
        }else {
            setContentView(R.layout.internet_check_view);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean ConnectionCheck(){
        NetworkInfo networkInfo = getSystemService(ConnectivityManager.class).getActiveNetworkInfo();
        Log.d("MainActivity", "Checking Connection");
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());

    }
    public int getImage(String icon){
        String dash="-";
        String underscore="_";
        int iconID =
                this.getResources().getIdentifier(icon.replace(dash, underscore), "drawable", this.getPackageName());
        if (String.valueOf(iconID).matches( "0")) {
            Log.d("MainActivity", "getImage: CANNOT FIND Image " + icon);
        }
        return iconID;
    }
    private String getWindDirection(double degrees) {
        Log.d("MainActivity", "getDirection: Checking Directions");

        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X"; // We'll use 'X' as the default if we get a bad value
    }

    public String CheckTiming(String str, SimpleDateFormat date){
        Date dateTime = new Date(Long.parseLong(str) * 1000);
        return date.format(dateTime);
    }

    public void updateData(DataBean dataBean) {
        data = dataBean;
        CurrConditionBean currConditionBean = dataBean.getCurrentConditionDataBean();
        Log.d("MainActivity", "updateData: updateData");
        String condition = currConditionBean.getDatetime();
        date = new Date(Long.parseLong(condition) * 1000);
        setTitle(dataBean.getAddr());
        String dateF="EEE MMM dd h:mm a, yyyy";
        SimpleDateFormat CompleteDate = new SimpleDateFormat(dateF, Locale.getDefault());
        dateInformation.setText(CompleteDate.format(date));

        setData(currConditionBean);

        daysBeanList = (ArrayList<DaysDataBean>) dataBean.getDaysList();
        hourlyBeanList = (ArrayList<HourlyDataBean>) daysBeanList.get(0).getHourlyList();
        setTemperatures(hourlyBeanList);
        String timeOnlyF="h:mm a";
        SimpleDateFormat timeOnly = new SimpleDateFormat(timeOnlyF, Locale.getDefault());
        String dayF="EEEE";
        SimpleDateFormat dayFormat = new SimpleDateFormat(dayF, Locale.getDefault());
        String hourOnlyF="h a";
        SimpleDateFormat hourOnly = new SimpleDateFormat(hourOnlyF, Locale.getDefault());
        thisSunrise.setText("Sunrise: "+ CheckTiming(currConditionBean.getSunrise(), timeOnly));
        int counter=0;
        thisSunset.setText("Sunset: "+ CheckTiming(currConditionBean.getSunset(), timeOnly));

        ArrayList<HourlyDataBean> hourlyRecycler = new ArrayList<>();
        boolean flag_check = false;
        for(int i = 0; i < daysBeanList.size(); i++){
            ArrayList<HourlyDataBean>  dayList = (ArrayList<HourlyDataBean>) daysBeanList.get(i).getHourlyList();
            for(int j = 0; j < dayList.size(); j++) {
                String day;
                if(flag_check) {
                    if (i == 0) {
                        day = "Today";
                    } else {
                        day = CheckTiming(dayList.get(j).getDateAndtime(), dayFormat);
                    }
                    String time=CheckTiming(dayList.get(j).getDateAndtime(), timeOnly);
                    String temp=dayList.get(j).getTemperature();
                    String conditions=dayList.get(j).getCond();

                    HourlyDataBean hourlyDataBean = new HourlyDataBean(
                            day,time,
                            dayList.get(j).getImage(),temp,
                            conditions
                    );
                    hourlyRecycler.add(hourlyDataBean);
                }
                if(CheckTiming(currConditionBean.getDatetime(), hourOnly).equals(CheckTiming(dayList.get(j).getDateAndtime(), hourOnly))){
                    flag_check = true;
                }
                if (String.valueOf(hourlyRecycler.size()).matches("48"))
                    break;
            }
            if (String.valueOf(hourlyRecycler.size()).matches("48"))
                break;
        }
        recyclerView.setAdapter(new MainActAdapter(this, hourlyRecycler));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setTemperatures(ArrayList<HourlyDataBean> hourlyBeanList) {
        tempAfternoon.setText(hourlyBeanList.get(13).getTemperature());
        tempMorning.setText(hourlyBeanList.get(8).getTemperature());
        tempeNight.setText(hourlyBeanList.get(23).getTemperature());
        tempEvening.setText(hourlyBeanList.get(17).getTemperature());
    }

    private void setData(CurrConditionBean currConditionBean) {

        currentFeelslike.setText("Feels Like "+ currConditionBean.getFeelsLike());
        currImage.setImageResource(getImage(currConditionBean.getImages()));
        currentTemperature.setText(currConditionBean.getTemp());
        String desc = currConditionBean.getCond() + " ( "+ currConditionBean.getCloudCoverage()+ "% clouds)";
        this.desc.setText(desc.substring(0, 1).toUpperCase() + desc.substring(1));
        dirWind.setText("Winds: "+ getWindDirection(Double.parseDouble(currConditionBean.getWindDirection()))+" at "+ currConditionBean.getwSpeed()+
                " mph gusting to "+ currConditionBean.getWindG()+" mph");
        currVision.setText("Visibility: "+ currConditionBean.getVisibility()+" mi");
        currHumidity.setText("Humidity: "+ currConditionBean.getHumid()+"%");
        currUVIndex.setText("UV Index: "+ currConditionBean.getUvIndexes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.unit)
        {
            String country="us";
            if(ConnectionCheck()){
                if(units.matches(country)){
                    units = "metric";
                    item.setIcon(R.drawable.units_c);
                }else{
                    units = country;
                    item.setIcon(R.drawable.units_f);
                }

                DataService.fetchData(this, location, units);
                return true;
            }else{
                Toast.makeText(this,"No Internet Connectivity",Toast.LENGTH_SHORT).show();
                return true;
            }
        } else if (item.getItemId()==R.id.calender) {
            if(ConnectionCheck()){
                Bundle bundle = new Bundle();
                Intent intent = new Intent(this, WeekActivity.class);
                String data="data";
                bundle.putSerializable(data, (Serializable) data);
                intent.putExtra(data, bundle);
                startActivity(intent);
                return true;
            }else{
                Toast.makeText(this,"No Internet Connectivity",Toast.LENGTH_SHORT).show();
                return true;
            }

        }else if (item.getItemId()==R.id.location) {
            if(ConnectionCheck()){
                final EditText txtUrl = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("Enter a Location")
                        .setMessage("For US locations, enter as 'City',or 'City,State'.\n"+"For international locations enter as 'City,Country'\n")
                        .setView(txtUrl)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(!txtUrl.getText().toString().isEmpty())
                                {
                                    location = txtUrl.getText().toString();
                                }
                                getinformations();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getinformations();
                            }
                        })
                        .show();
                return true;
            }
            else{
                Toast.makeText(this,"No Internet Connectivity",Toast.LENGTH_SHORT).show();
                return true;
            }

        }else {
            Toast.makeText(this,"Invalid menu",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getinformations(){
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("SaveData", MODE_APPEND);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("unit", units);
        myEdit.putString("location", location);

        myEdit.apply();
        DataService.fetchData(this, location, units);
    }
}