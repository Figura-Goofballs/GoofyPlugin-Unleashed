package com.thekillerbunny.goofyplugin.lua;

import com.google.gson.Gson;
import com.mojang.serialization.JsonOps;
import com.neovisionaries.ws.client.WebSocket;
import net.minecraft.util.ExtraCodecs;
import org.figuramc.figura.backend2.HttpAPI;
import org.figuramc.figura.backend2.NetworkStuff;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.data.FiguraBuffer;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.luaj.vm2.LuaError;

import java.lang.reflect.Field;
import java.util.Objects;

import net.minecraft.network.chat.Component;

@LuaWhitelist
public class BackendAPI {
    public BackendAPI() {}

    @LuaMethodDoc("goofy.backend.connected")
    @LuaWhitelist
    public boolean connected() {
        return NetworkStuff.isConnected();
    }
    @LuaMethodDoc("goofy.backend.can_upload")
    @LuaWhitelist
    public boolean canUpload() {
        return NetworkStuff.canUpload();
    }
    @LuaMethodDoc("goofy.backend.get_avatar_max_size")
    @LuaWhitelist
    public int getAvatarMaxSize() {
        return NetworkStuff.getSizeLimit();
    }
    @LuaMethodDoc("goofy.backend.send_ping")
    @LuaWhitelist
    public void sendPing(int id, boolean sync, byte[] data) {
        NetworkStuff.sendPing(id, sync, data);
    }
    @LuaMethodDoc("goofy.backend.disconnect")
    @LuaWhitelist
    public void disconnect(String message) {
        NetworkStuff.disconnect(message);
    }

    private String getToken() {
        try {
            Field apiField = NetworkStuff.class.getDeclaredField("api");
            apiField.setAccessible(true);
            HttpAPI api = (HttpAPI) apiField.get(null);
            Field tokenField = HttpAPI.class.getDeclaredField("token");
            tokenField.setAccessible(true);
            return (String) tokenField.get(api);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Not possible", e);
        }
    }

    @LuaMethodDoc("goofy.backend.connect_with_token")
    @LuaWhitelist
    public void connect() {
        NetworkStuff.connect(getToken());
    }
    @LuaWhitelist
    public String motd() {
        throw new LuaError("backend:motd() is unavailable on 1.21");
    }
    @LuaMethodDoc("goofy.backend.write")
    @LuaWhitelist
    public void write(FiguraBuffer data) {
        try {
            Field socketField = NetworkStuff.class.getDeclaredField("ws");
            socketField.setAccessible(true);
            Field bufField = FiguraBuffer.class.getDeclaredField("buf");
            bufField.setAccessible(true);
            ((WebSocket) socketField.get(null)).sendBinary((byte[]) bufField.get(data));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
    }

    @Override
    public String toString() {
      return "BackendAPI";
    }
}
