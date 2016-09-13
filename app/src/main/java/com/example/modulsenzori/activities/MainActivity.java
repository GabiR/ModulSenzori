package com.example.modulsenzori.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.modulsenzori.R;
import com.example.modulsenzori.fragments.GraphsFragment;
import com.example.modulsenzori.fragments.HistoryFragment;
import com.example.modulsenzori.fragments.PowerFragment;
import com.example.modulsenzori.fragments.ReadingsFragment;
import com.example.modulsenzori.objects.Sensor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout readingsBtn, graphsBtn, historyBtn, ordersBtn;
    LinearLayout prevLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readingsBtn = (LinearLayout) findViewById(R.id.readingsBtn);
        readingsBtn.setTag("read");
        graphsBtn = (LinearLayout) findViewById(R.id.graphsBtn);
        graphsBtn.setTag("graph");
        historyBtn = (LinearLayout) findViewById(R.id.historyBtn);
        historyBtn.setTag("history");
        ordersBtn = (LinearLayout) findViewById(R.id.ordersBtn);
        ordersBtn.setTag("order");

        prevLayout = readingsBtn;

        readingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolvePrevBtn(readingsBtn);
                ((ImageView)readingsBtn.getChildAt(0)).setImageDrawable(getResources().getDrawable(R.drawable.read_icon_blue));
                ((TextView)readingsBtn.getChildAt(1)).setTextColor(getResources().getColor(R.color.colorPrimary));
                goToFragment(new ReadingsFragment());
            }
        });

        graphsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolvePrevBtn(graphsBtn);
                ((ImageView)graphsBtn.getChildAt(0)).setImageDrawable(getResources().getDrawable(R.drawable.graph_icon_blue));
                ((TextView)graphsBtn.getChildAt(1)).setTextColor(getResources().getColor(R.color.colorPrimary));
                goToFragment(new GraphsFragment());
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolvePrevBtn(historyBtn);
                ((ImageView)historyBtn.getChildAt(0)).setImageDrawable(getResources().getDrawable(R.drawable.history_icon_blue));
                ((TextView)historyBtn.getChildAt(1)).setTextColor(getResources().getColor(R.color.colorPrimary));
                goToFragment(new HistoryFragment());
            }
        });

        ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolvePrevBtn(ordersBtn);
                ((ImageView)ordersBtn.getChildAt(0)).setImageDrawable(getResources().getDrawable(R.drawable.power_icon_blue));
                ((TextView)ordersBtn.getChildAt(1)).setTextColor(getResources().getColor(R.color.colorPrimary));
                goToFragment(new PowerFragment());
            }
        });

        prevLayout.callOnClick();

        ArrayList<Sensor> sensors = retrieveSensorsObjects(MainActivity.this);
        if(sensors == null)
            sensors = new ArrayList<>();
        if(sensors.size() == 0) {
            // Temperatura, Umiditate, Lumina, Presiune, PresiuneRel, TemperaturaAmbient
            Sensor s1 = new Sensor("Temperatura", "Off", "temperature_blue","temperature_gray","-");
            Sensor s2 = new Sensor("Umiditate", "Off", "humidity_blue","humidity_gray","-");
            Sensor s3 = new Sensor("Lumina", "Off", "light_blue","light_gray","-");
            Sensor s4 = new Sensor("Presiune", "Off", "pressure_blue","pressure_gray","-");
            Sensor s5 = new Sensor("PresiuneRel", "Off", "pressure_blue","pressure_gray","-");
            Sensor s6 = new Sensor("TemperaturaAmbient", "Off", "pressure_blue","pressure_gray","-");
            sensors.add(s1); sensors.add(s2); sensors.add(s3);
            sensors.add(s4); sensors.add(s5); sensors.add(s6);
            storeSensorsObjects(sensors, MainActivity.this);
        }


    }

    public void resolvePrevBtn(LinearLayout linearLayout) {
        switch (prevLayout.getTag().toString()) {
            case "read":
                ((ImageView)prevLayout.getChildAt(0)).setImageDrawable(getResources().getDrawable(R.drawable.read_icon_gray));
                break;

            case "graph":
                ((ImageView)prevLayout.getChildAt(0)).setImageDrawable(getResources().getDrawable(R.drawable.graph_icon_gray));
                break;

            case "history":
                ((ImageView)prevLayout.getChildAt(0)).setImageDrawable(getResources().getDrawable(R.drawable.history_icon_gray));
                break;

            case "order":
                ((ImageView)prevLayout.getChildAt(0)).setImageDrawable(getResources().getDrawable(R.drawable.power_icon_gray));
                break;

        }

        ((TextView)prevLayout.getChildAt(1)).setTextColor(getResources().getColor(R.color.darkGray));
        prevLayout = linearLayout;
    }

    void goToFragment (Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    }

    public static void storeSensorsObjects(ArrayList<Sensor> sensors, Context context){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        if(sensors == null)
            sensors = new ArrayList<>();
        String json = gson.toJson(sensors);

        editor.putString("sensorsArray", json);
        editor.commit();
    }

    public static ArrayList<Sensor> retrieveSensorsObjects(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("sensorsArray", null);
        Type type = new TypeToken<ArrayList<Sensor>>() {}.getType();
        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors = gson.fromJson(json, type);
        return sensors;
    }




}
