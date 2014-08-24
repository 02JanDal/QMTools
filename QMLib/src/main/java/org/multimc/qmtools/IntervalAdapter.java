package org.multimc.qmtools;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class IntervalAdapter implements JsonSerializer<Interval>, JsonDeserializer<Interval> {
    @Override
    public JsonElement serialize(Interval interval, Type type, JsonSerializationContext jsc) {
        return new JsonPrimitive(interval.toString());
    }

    @Override
    public Interval deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException, IntervalParseException {
        return Interval.fromString(je.getAsString());
    }
    
}
