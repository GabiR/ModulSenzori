package com.example.modulsenzori.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.modulsenzori.R;
import com.example.modulsenzori.activities.MainActivity;
import com.example.modulsenzori.objects.Sensor;

import java.util.ArrayList;

/**
 * Created by GabiRotaru on 08/07/16.
 */
public class PowerFragment extends Fragment {

    ArrayList<Sensor> sensors;
    ImageView icon_temp, icon_humid, icon_press, icon_light;
    ImageView temp_switch, humid_switch, press_switch, light_switch;
    ImageView switch_all, switch_emergency;
    TextView temp_hour, humid_hour, press_hour, light_hour;
    boolean allSensorsClosed = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.power_fragment, container, false);

        sensors = new ArrayList<>();
        sensors = MainActivity.retrieveSensorsObjects(getActivity());

        switch_all = (ImageView) view.findViewById(R.id.switch_all);
        switch_emergency = (ImageView) view.findViewById(R.id.switch_emergency);

        temp_hour = (TextView) view.findViewById(R.id.temp_hour);
        icon_temp = (ImageView) view.findViewById(R.id.icon_temp);
        temp_switch = (ImageView) view.findViewById(R.id.switch_temp);

        humid_hour = (TextView) view.findViewById(R.id.humid_hour);
        icon_humid = (ImageView) view.findViewById(R.id.icon_humid);
        humid_switch = (ImageView) view.findViewById(R.id.switch_humid);

        light_hour = (TextView) view.findViewById(R.id.light_hour);
        icon_light = (ImageView) view.findViewById(R.id.icon_light);
        light_switch = (ImageView) view.findViewById(R.id.switch_light);

        press_hour = (TextView) view.findViewById(R.id.press_hour);
        icon_press = (ImageView) view.findViewById(R.id.icon_press);
        press_switch = (ImageView) view.findViewById(R.id.switch_press);

        for(int i=0 ; i< sensors.size(); i++) {
            setLayoutByObject(sensors.get(i));
            if(sensors.get(i).getStatus().equals("On"))
                allSensorsClosed = false;
        }

        if(allSensorsClosed)
            setImage(switch_all, "power_button_gray");
        else
            setImage(switch_all, "power_button_green");

        switch_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allSensorsClosed = true;
                for(int i=0 ; i< sensors.size(); i++) {
                    if(sensors.get(i).getStatus().equals("On"))
                        allSensorsClosed = false;
                }

                for(int i=0 ; i< sensors.size(); i++) {
                    if (allSensorsClosed)
                        sensors.get(i).setStatus("On");
                    else
                        sensors.get(i).setStatus("Off");

                    setLayoutByObject(sensors.get(i));
                }

                if(allSensorsClosed) {
                    setImage(switch_all, "power_button_green");
                    allSensorsClosed = false;
                    sendCommand("start_all");
                } else {
                    setImage(switch_all, "power_button_gray");
                    allSensorsClosed = true;
                    sendCommand("stop_all");
                }

            }
        });

        switch_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                        .title("Atentie")
                        .content("Folositi doar in cazuri de urgenta! Necesita reset hardware")
                        .positiveText("Continua")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                sendCommand("full_stop");
                                for(int i=0 ; i< sensors.size(); i++) {
                                    sensors.get(i).setStatus("Off");
                                    setLayoutByObject(sensors.get(i));
                                }
                                allSensorsClosed = true;
                                setImage(switch_all, "power_button_gray");
                                dialog.dismiss();
                            }
                        })
                        .negativeText("Renunta")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });
        
        temp_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSensorCommand(sensors.get(0), "ts");
                setLayoutByObject(sensors.get(0));
            }
        });
        humid_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSensorCommand(sensors.get(1),"h");
                setLayoutByObject(sensors.get(1));
            }
        });
        light_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSensorCommand(sensors.get(2),"l");
                setLayoutByObject(sensors.get(2));
            }
        });
        press_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSensorCommand(sensors.get(3),"p");
                setLayoutByObject(sensors.get(3));
            }
        });

        return view;
    }


    void setLayoutByObject(Sensor s) {
            if(s.getStatus().equals("On")) {
                switch (s.getTitle()) {
                    case ("Temperatura"):
                        setImage(icon_temp, s.getIcon_blue());
                        setImage(temp_switch, "power_button_green");
                        temp_hour.setText(s.getLast_reading());
                        break;
                    case ("Umiditate"):
                        setImage(icon_humid, s.getIcon_blue());
                        setImage(humid_switch, "power_button_green");
                        humid_hour.setText(s.getLast_reading());
                        break;
                    case ("Lumina"):
                        setImage(icon_light, s.getIcon_blue());
                        setImage(light_switch, "power_button_green");
                        light_hour.setText(s.getLast_reading());
                        break;
                    case ("Presiune"):
                        setImage(icon_press, s.getIcon_blue());
                        setImage(press_switch, "power_button_green");
                        press_hour.setText(s.getLast_reading());
                        break;
                }
            } else {
                switch (s.getTitle()) {
                    case ("Temperatura"):
                        setImage(icon_temp, s.getIcon_gray());
                        setImage(temp_switch, "power_button_gray");
                        temp_hour.setText(s.getLast_reading());
                        break;
                    case ("Umiditate"):
                        setImage(icon_humid, s.getIcon_gray());
                        setImage(humid_switch, "power_button_gray");
                        humid_hour.setText(s.getLast_reading());
                        break;
                    case ("Lumina"):
                        setImage(icon_light, s.getIcon_gray());
                        setImage(light_switch, "power_button_gray");
                        light_hour.setText(s.getLast_reading());
                        break;
                    case ("Presiune"):
                        setImage(icon_press, s.getIcon_gray());
                        setImage(press_switch, "power_button_gray");
                        press_hour.setText(s.getLast_reading());
                        break;
                }
            }

    }

    void setImage(ImageView imgView, String drawableName) {
        imgView.setImageResource(getResources().getIdentifier(drawableName, "drawable", getActivity().getPackageName()));
    }


    void sendSensorCommand(Sensor s, String sensor) {
        String command = "";
        if(s.getStatus().equals("Off")) {
            s.setStatus("On");
            command = "start";
        } else {
            s.setStatus("Off");
            command = "stop";
        }


//aici se da comanda ble command + "_" + sensor
        //!!!! TREBUIE SA BAG PRIMA CITIRE in last_reading
        Log.e("comanda", command + "_" + sensor);
        MainActivity.storeSensorsObjects(sensors, getActivity());

    }

    void sendCommand(String command) {
        Log.e("comanda", command);
        MainActivity.storeSensorsObjects(sensors, getActivity());

    }
}
