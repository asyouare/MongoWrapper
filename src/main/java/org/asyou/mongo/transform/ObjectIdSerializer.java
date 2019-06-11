package org.asyou.mongo.transform;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

/**
 * Created by steven on 17/7/18.
 */
public class ObjectIdSerializer implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId> {
    @Override
    public JsonElement serialize(final ObjectId objectId,
                                 final Type type,
                                 final JsonSerializationContext context)
    {
        JsonObject element = null;
        JsonElement value = new JsonPrimitive(objectId.toString());
        element = new JsonObject();
        element.add("$oid",value);
        return element;
    }

    @Override
    public ObjectId deserialize(final JsonElement jsonElement,
                                final Type type,
                                final JsonDeserializationContext context)
            throws JsonParseException
    {
        //todo 如果字段名相同，类型不同如何处理？
        ObjectId oid = null;
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (ObjectId.class.getTypeName().equals(type.getTypeName())) {
            JsonPrimitive value = jsonObject.getAsJsonPrimitive("$oid");
            oid = new ObjectId(value.getAsString());
        }
        return oid;

    }
}