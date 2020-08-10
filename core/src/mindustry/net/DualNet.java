package mindustry.net;
import mindustry.basic.NetBasic;

/* redirect net calls */
public class DualNet<T extends NetBasic> extends Net{
    public boolean bluetoothMode = false;
    T BluetoothNet;

    public DualNet(NetProvider provider) {
        super(provider);
    }

    public DualNet(NetProvider provider, T t){
        super(provider);
        this.BluetoothNet = t;
    }

    public void setBluetoothNet(T t){
        this.BluetoothNet = t;
    }

    public boolean BTactive(){ return BluetoothNet.BTactive();}

    public boolean hasBT(){
        return BluetoothNet != null;
    }

    public boolean test(){
        if(BluetoothNet != null){
            return BluetoothNet.BluetoothOn();
        }else{
            BluetoothNet.hi();
        }
        return false;
    }
}
