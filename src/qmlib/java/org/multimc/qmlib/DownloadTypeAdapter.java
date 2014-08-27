package org.multimc.qmlib;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class DownloadTypeAdapter implements JsonSerializer<QuickModDownload.DownloadType>, JsonDeserializer<QuickModDownload.DownloadType> {
    @Override
    public JsonElement serialize(QuickModDownload.DownloadType installType, Type type, JsonSerializationContext jsc) throws InvalidInstallTypeException {
        switch (installType) {
            case direct:
                return new JsonPrimitive("direct");
            case parallel:
                return new JsonPrimitive("parallel");
            case sequential:
                return new JsonPrimitive("sequential");
            case encoded:
                return new JsonPrimitive("encoded");
            case maven:
                return new JsonPrimitive("maven");
        }
        throw new InvalidDownloadTypeException("Invalid downloadType: " + installType.toString());
    }

    @Override
    public QuickModDownload.DownloadType deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException, InvalidDownloadTypeException {
        return QuickModDownload.downloadTypeFromString(je.getAsString());
    }
    
}
