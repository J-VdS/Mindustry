package mindustry.ui.dialogs;

import arc.Core;
import arc.math.Mathf;
import arc.scene.ui.*;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.net.Host;
import mindustry.ui.Styles;

import java.util.UUID;

import static mindustry.Vars.*;
import static mindustry.Vars.player;

public class BluetoothDialog extends BaseDialog{
    Table previous = new Table();
    Seq<BTServer> servers = new Seq<>(); //only the last one?
    BTServer renaming;
    Dialog add;
    Button joinbtn;

    public BluetoothDialog(){
        super("@bluetooth.joingame");

        //load servers
        loadServers();

        buttons.add().width(60f);
        buttons.add().growX().width(-1);

        addCloseButton();

        buttons.add().growX().width(-1);
        buttons.button("?", () -> ui.showInfo("@bluetooth.clientinfo")).size(60f, 64f).width(-1);

        add = new BaseDialog("@joingame.title");

        add.cont.add();
        add.cont.add(Core.bundle.format("bluetooth.uuidexample", Core.settings.getString("uuid")));
        add.cont.row();
        add.cont.add("@bluetooth.uuid").padRight(5f).left();
            TextField field = add.cont.field(Core.settings.getString("serverUUID"), text -> {
                Core.settings.put("serverUUID", text);
            }).size(320f, 54f).maxTextLength(100).addInputDialog().get();

        //setup?
        add.cont.row();
        add.buttons.defaults().size(140f, 60f).pad(4f);
        add.buttons.button("@cancel", add::hide);
        add.buttons.button("@ok", () -> {
            if(renaming == null){
                BluetoothDialog.BTServer server = new BluetoothDialog.BTServer();
                //TODO
                if(server.setUUID(Core.settings.getString("serverUUID"))) {
                    servers.clear();
                    servers.add(server);
                }
            }else{
                renaming.setUUID(Core.settings.getString("serverUUID"));
            }
            saveServers();
            setupPrevious();
            add.hide();
        }).disabled(b -> Core.settings.getString("serverUUID") == null || field.getText().isEmpty());


        add.shown(() -> {
            add.title.setText("@server.add");
            if(renaming != null){
                System.out.println("renaming != null");
                field.setText(renaming.displayUUID());
            }

        });

        shown(() -> {
            setup();

            //Core.app.post(() -> Core.settings.getBoolOnce("ip", () -> ui.showInfo("@bluetooth.clientinfo")));
        });

    }

    void setupPrevious(){
        ((TextButton)this.joinbtn).setText((servers.size>0)?servers.get(0).displayUUID():"");
    }

    void setup(){
        float w = targetWidth();

        previous.clear();

        cont.clear();
        //name + color selector
        cont.table(t -> {
            t.add("@name").padRight(10);

            t.field(Core.settings.getString("name"), text -> {
                player.name(text);
                Core.settings.put("name", text);
            }).grow().pad(8).addInputDialog(maxNameLength);

            ImageButton button = t.button(Tex.whiteui, Styles.clearFulli, 40, () -> {
                new PaletteDialog().show(color -> {
                    player.color().set(color);
                    Core.settings.put("color-0", color.rgba8888());
                });
            }).size(54f).get();
            button.update(() -> button.getStyle().imageUpColor = player.color());
        }).width(w).height(70f).pad(4);
        cont.row();
        //cont.add(previous).width(w + 38).pad(0);

        //add server button
        cont.row();
        cont.buttonCenter("@server.add", Icon.add, () -> {
            renaming = null;
            add.show();
        }).marginLeft(10).width(w).height(80f);


        cont.row();
        this.joinbtn = cont.buttonCenter("", Icon.play, () -> {
        }).marginLeft(10).width(w).height(80f).disabled(b -> servers.size < 1).get();

        setupPrevious();
    }

    public void connect(java.util.UUID uuid) {
        if (player.name.trim().isEmpty()) {
            ui.showInfo("@noname");
            return;
        }

        ui.loadfrag.show("@connecting");

        ui.loadfrag.setButton(() -> {
            ui.loadfrag.hide();
            //netClient.disconnectQuietly();
        });

        /*
        Time.runTask(2f, () -> {
            logic.reset();
            net.reset();
            Vars.netClient.beginConnecting();
            net.connect(ip, port, () -> {
                hide();
                add.hide();
            });
        });
         */
    }

    /* from JoinDialog.java */
    float targetWidth(){
        return Math.min(Core.graphics.getWidth() / Scl.scl() * 0.9f, 500f);
    }

    private void saveServers(){
        Core.settings.putJson("btservers", BTServer.class, servers);
    }

    private void loadServers(){
        this.servers = Core.settings.getJson("btservers", Seq.class, BTServer.class, Seq::new);
    }

    public static class BTServer{
        public java.util.UUID UUID;
        String UUID_raw;

        transient Host lastHost;

        boolean setUUID(String UUID_raw){
            try{
                this.UUID = java.util.UUID.nameUUIDFromBytes(UUID_raw.getBytes());
                this.UUID_raw = UUID_raw;
                return true;
            }catch(Exception e){
                ui.showInfo("@bluetooth.invalidUUID");
                return false;
            }
        }

        String displayUUID(){
            return UUID_raw;
        }

        public BTServer(){
        }
    }
}
