package mindustry.net;

import mindustry.Vars;
import mindustry.basic.AdministrationBasic;

/* redirect administration calls */
public class DualAdministration<T extends AdministrationBasic> extends Administration {

    public DualAdministration(){
        super();
    }

    /*
    private boolean viaBluetooth(){
        return Vars.net.bluetoothMode;
    }


    @Override
    public boolean isSubnetBanned(String ip){
        if(!viaBluetooth()){
            return super.isSubnetBanned(ip);
        }
        return false;
    }

    @Override
    public boolean banPlayer(String uuid){
        if(!viaBluetooth()){
            return super.banPlayer(uuid);
        }
        return banPlayerID(uuid);
    }
    */




}
