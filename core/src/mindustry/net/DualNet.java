package mindustry.net;

import mindustry.basic.BluetoothBasic;

/* redirect net calls */
public class DualNet<T extends BluetoothBasic> extends Net{
    public Boolean bluetoothMode = false;
    T BluetoothNet;

    public DualNet(NetProvider provider) {
        super(provider);
    }
    public DualNet(NetProvider provider, T t){
        super(provider);
        this.BluetoothNet = t;
    }

    public boolean hasBT(){
        return BluetoothNet != null;
    }

    public void test(){
        BluetoothNet.hi();
    }
}
