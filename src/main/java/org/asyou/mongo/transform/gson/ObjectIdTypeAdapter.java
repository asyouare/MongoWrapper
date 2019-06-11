package org.asyou.mongo.transform.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * Created by steven on 17/7/17.
 */
public class ObjectIdTypeAdapter extends ABasicTypeAdapter<ObjectId> {
    @Override
    public ObjectId reading(final JsonReader jsonReader) throws IOException {
        String id = null;
        jsonReader.beginObject();
        jsonReader.nextName();
        id = jsonReader.nextString();
        jsonReader.endObject();
        return new ObjectId(id);
    }

    @Override
    public void writing(final JsonWriter jsonWriter, final ObjectId value) throws IOException {
        jsonWriter.beginObject().name("$oid").value(value.toString()).endObject();
    }
}