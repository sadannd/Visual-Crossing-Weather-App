package com.sadanand.weatherapp.weatherapp.Services;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sadanand.weatherapp.weatherapp.EntityModule.DataBean;
import com.sadanand.weatherapp.weatherapp.EntityModule.DaysDataBean;
import com.sadanand.weatherapp.weatherapp.EntityModule.HourlyDataBean;
import com.sadanand.weatherapp.weatherapp.MainActivity;
import com.sadanand.weatherapp.weatherapp.EntityModule.CurrConditionBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataService {
    private static MainActivity mainActivity;
    private static RequestQueue queue;
    private static String unit;
    private static final String UNITF = "°F";
    private static final String UNITC = "°C";

    public static void fetchData(MainActivity activity, String location, String units) {
        mainActivity = activity;
        queue = Volley.newRequestQueue(mainActivity);
        unit = units;
        Uri.Builder builder = Uri.parse("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/").buildUpon();
        builder.appendPath(location);
        builder.appendQueryParameter("unitGroup",units);
        builder.appendQueryParameter("lang","en");
        builder.appendQueryParameter("key", "QAQ4936N7STWCZJJFKRKAV3K8");
        String url = builder.build().toString();
        Response.Listener<JSONObject> jsonObjectListener = response -> parseJSON(response.toString());
        Response.ErrorListener errorListener = error -> {
            mainActivity.updateData(null);
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,jsonObjectListener,errorListener);
        queue.add(jsonObjectRequest);
    }

    private static void parseJSON(String toString) {
        try {
            JSONObject jsonObject = new JSONObject(toString);
            DataBean dataBean;
            String address = jsonObject.getString("address");
            String timezone = jsonObject.getString("timezone");
            String tzOffset = jsonObject.getString("tzoffset");
            ArrayList<DaysDataBean> daysArrayList = new ArrayList<>();
            JSONArray daysArray = jsonObject.getJSONArray("days");
            for(int j = 0 ; j < daysArray.length(); j++) {
                ArrayList<HourlyDataBean> hourlyArrayList = new ArrayList<>();
                JSONObject days = (JSONObject) daysArray.get(j);
                JSONArray hours = days.getJSONArray("hours");
                for(int i = 0; i < hours.length(); i++) {
                    JSONObject jHourly = (JSONObject) hours.get(i);
                    HourlyDataBean hourlyDataBean = new HourlyDataBean(
                            jHourly.getString("datetimeEpoch"),
                            (int)Math.ceil(jHourly.getDouble("temp"))+(unit.equals("us") ? UNITF : UNITC),
                            jHourly.getString("conditions"),
                            jHourly.getString("icon")
                    );
                    hourlyArrayList.add(hourlyDataBean);
                }
                DaysDataBean daysDataBean = new DaysDataBean(
                        days.getString("datetimeEpoch"),
                        (int)Math.ceil(days.getDouble("tempmax"))+(unit.equals("us") ? UNITF : UNITC),
                        (int)Math.ceil(days.getDouble("tempmin"))+(unit.equals("us") ? UNITF : UNITC),
                        days.getString("precipprob"),
                        days.getString("uvindex"),
                        days.getString("description"),
                        days.getString("icon"),
                        hourlyArrayList
                );
                daysArrayList.add(daysDataBean);
            }
            JSONObject current = jsonObject.getJSONObject("currentConditions");
            String windgust;
            if(current.getString("windgust")!=null && !current.getString("windgust").equals("null")) {
                windgust = (int) Math.ceil(current.getDouble("windgust")) + "";
            }
            else {
                windgust = 0 + "";
            }
            CurrConditionBean currConditionBean = new CurrConditionBean(
                    current.getString("datetimeEpoch"),
                    (int)Math.ceil(current.getDouble("temp"))+(unit.equals("us") ? UNITF : UNITC),
                    (int)Math.ceil(current.getDouble("feelslike"))+(unit.equals("us") ? UNITF : UNITC),
                    (int)Math.ceil(current.getDouble("humidity"))+"",
                    windgust,
                    (int)Math.ceil(current.getDouble("windspeed"))+"",
                    current.getString("winddir"),
                    current.getString("visibility"),
                    (int)Math.ceil(current.getDouble("cloudcover"))+"",
                    (int)Math.ceil(current.getDouble("uvindex"))+"",
                    current.getString("conditions"),
                    current.getString("icon"),
                    current.getString("sunriseEpoch"),
                    current.getString("sunsetEpoch")
            );
            dataBean = new DataBean(
                    jsonObject.getString("address"),
                    jsonObject.getString("timezone"),
                    jsonObject.getString("tzoffset"),
                    daysArrayList,
                    currConditionBean
            );
            mainActivity.updateData(dataBean);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
