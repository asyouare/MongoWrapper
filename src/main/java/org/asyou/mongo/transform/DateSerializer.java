package org.asyou.mongo.transform;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by steven on 17/7/17.
 */
public class DateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {
    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {

        return null;
    }

    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        return null;
    }
}
