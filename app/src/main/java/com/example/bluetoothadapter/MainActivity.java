package com.example.bluetoothadapter;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter BA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        BA = BluetoothAdapter.getDefaultAdapter();
        if (BA == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth is not supported", Toast.LENGTH_LONG).show();
        } else if(BA.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth is on", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(i);
            if (BA.isEnabled()) {
                Toast.makeText(getApplicationContext(), "Now Bluetooth is on", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onFindDevices(View view) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(intent);
    }

    public void onViewPairedDevices(View view) {
        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        ListView pairedDevicesListView = findViewById(R.id.listView);
        ArrayList pairedDevicesArrayList = new ArrayList();
        for (BluetoothDevice bluetoothDevice: pairedDevices) {
            pairedDevicesArrayList.add(bluetoothDevice.getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, pairedDevicesArrayList);
        pairedDevicesListView.setAdapter(arrayAdapter);
    }

    public void onTurnBluetoothOff(View view) {
        BA.disable();
        if (BA.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth couldn't switch off", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is switched off", Toast.LENGTH_LONG).show();
        }
    }
}
