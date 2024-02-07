package me.duquee.createutilities.utils;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class BufferUtils {

    public static void writeGameProfile(FriendlyByteBuf buffer, GameProfile profile) {
        buffer.writeUUID(profile.getId());
        buffer.writeUtf(profile.getName());
        buffer.writeCollection(profile.getProperties().values(), BufferUtils::writeProperty);
    }

    public static void writeProperty(FriendlyByteBuf buffer, Property property) {
        buffer.writeUtf(property.getName());
        buffer.writeUtf(property.getValue());
        if (property.hasSignature()) {
            buffer.writeBoolean(true);
            buffer.writeUtf(property.getSignature());
        } else {
            buffer.writeBoolean(false);
        }
    }

    public static GameProfile readGameProfile(FriendlyByteBuf buffer) {
        UUID uuid = buffer.readUUID();
        String string = buffer.readUtf(16);
        GameProfile gameProfile = new GameProfile(uuid, string);
        gameProfile.getProperties().putAll(readGameProfileProperties(buffer));
        return gameProfile;
    }

    public static PropertyMap readGameProfileProperties(FriendlyByteBuf buffer) {
        PropertyMap propertyMap = new PropertyMap();
        buffer.readWithCount((buf) -> {
            Property property = readProperty(buffer);
            propertyMap.put(property.getName(), property);
        });
        return propertyMap;
    }

    public static Property readProperty(FriendlyByteBuf buffer) {
        String string = buffer.readUtf();
        String string2 = buffer.readUtf();
        if (buffer.readBoolean()) {
            String string3 = buffer.readUtf();
            return new Property(string, string2, string3);
        } else {
            return new Property(string, string2);
        }
    }

}
