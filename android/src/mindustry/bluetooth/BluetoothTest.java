package mindustry.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import mindustry.android.AndroidLauncher;
import mindustry.android.debugtest;
import mindustry.basic.NetBasic;

public class BluetoothTest extends NetBasic {
    private BluetoothAdapter bluetoothAdapter;
    private AndroidLauncher app;
    public debugtest debug;

    public BluetoothTest(AndroidLauncher app){
        this.app = app;
        this.debug = new debugtest();

        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            debug.alert("no bluetooth");
            return;
        }

    }

    @Override
    public boolean BluetoothOn(){
        debug.alert(" *** Bluetooth button ***");
        if(bluetoothAdapter == null){
            debug.alert("no bluetooth");
            return false;
        }

        if(!bluetoothAdapter.isEnabled()){
            Intent enablebt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            app.addResultListener(i -> app.startActivityForResult(enablebt, Constants.REQUEST_ENABLE_BT), (code, in) -> {
                debug.alert("bluetooth window");
                System.out.println(code);
                if(code == Constants.RESULT_OK){
                    bluetoothAdapter.enable();
                    debug.alert("Bluetooth ON");}
            });
        }
        return false;
    }

    @Override
    public void hi(){
        debug.alert("BLUETOOTH is ON");
    }
}
