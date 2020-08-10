package mindustry.basic;


//mimic all androidbluetoothmethods to avoid compile errors on desktop
public class NetBasic {
    public boolean BluetoothOn(){return false;}
    public boolean BTactive(){return false;}
    //public boolean test(){return BluetoothOn();}
    public void hi(){};
    public void server(){};
    public void client(){};

    static enum netModes {
        ip,
        bluetooth
    }

}

