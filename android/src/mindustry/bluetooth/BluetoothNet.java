package mindustry.bluetooth;

import android.app.Activity;
import android.bluetooth.*;

import mindustry.basic.NetBasic;

public class BluetoothNet extends NetBasic {
    BluetoothAdapter bluetoothAdapter;

    public BluetoothNet() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean BTactive(){
        return bluetoothAdapter.isEnabled();
    }

}
