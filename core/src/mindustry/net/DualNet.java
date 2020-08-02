package mindustry.net;

public class DualNet<T extends BluetoothBasic> extends Net{
    public Boolean bluetoothMode = false;
    T testerje;

    public DualNet(NetProvider provider) {
        super(provider);
    }
    public DualNet(NetProvider provider, T t){
        super(provider);
        this.testerje = t;
    }

    public boolean hasBT(){
        return testerje != null;
    }

    public void test(){
        testerje.hi();
    }
}
