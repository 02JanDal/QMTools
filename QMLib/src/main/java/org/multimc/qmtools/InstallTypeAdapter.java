package org.multimc.qmtools;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class InstallTypeAdapter implements JsonSerializer<QuickModVersion.InstallType>, JsonDeserializer<QuickModVersion.InstallType> {
    @Override
    public JsonElement serialize(QuickModVersion.InstallType installType, Type type, JsonSerializationContext jsc) throws InvalidInstallTypeException {
        switch (installType) {
            case forgeMod:
                return new JsonPrimitive("forgeMod");
            case forgeCoreMod:
                return new JsonPrimitive("forgeCoreMod");
            case liteloaderMod:
                return new JsonPrimitive("liteloaderMod");
            case extract:
                return new JsonPrimitive("extract");
            case configPack:
                return new JsonPrimitive("configPack");
            case group:
                return new JsonPrimitive("group");
        }
        throw new InvalidInstallTypeException("Invalid installType: " + installType.toString());
    }

    @Override
    public QuickModVersion.InstallType deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException, InvalidInstallTypeException {
        return QuickModVersion.installTypeFromString(je.getAsString());
    }
    
}
