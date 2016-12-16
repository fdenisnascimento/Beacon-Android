package com.example.denis.beacon;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private Button buttonBeacon;
    private BeaconTransmitter beaconTransmitter;
    private Beacon beacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBeacon = (Button) findViewById(R.id.button_beacon);
        buttonBeacon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                emulatorBeacon();
            };
        });

         beacon = new Beacon.Builder()
                .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6") // UUID for beacon
                .setId2("0001")
                .setId3("5")
                .setManufacturer(0x004C)
                .setTxPower(-56)
                .setDataFields(Arrays.asList(new Long[]{0l}))
                .build();
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24");

        beaconTransmitter  = new BeaconTransmitter(getApplicationContext(), beaconParser);
    }


    public void emulatorBeacon() {
        if (beaconTransmitter.isStarted()){
            stopBeacon();
        }else{
            startBeacon();
        }
    }

    private void startBeacon(){

        beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {

            @Override
            public void onStartFailure(int errorCode) {
                Log.e("M4U", "Advertisement start failed with code: " + errorCode);
                setMessage("Advertisement start failed with code: " + errorCode);
            }

            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                Log.i("M4U", "Advertisement start succeeded.");
                setMessage("Promoção iniciada com sucesso");
            }
        });
    }

    private void  stopBeacon(){
        beaconTransmitter.stopAdvertising();
        setMessage("Promoção finalizada");
    }

    private void setMessage(String text){
        Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
    }

}
