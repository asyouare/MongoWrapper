package org.asyou.mongo.transform.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.asyou.mongo.type.DecimalObject;

import java.io.IOException;

/**
 * Created by steven on 17/7/19.
 */
public class DecimalObjectTypeAdapter extends ABasicTypeAdapter<DecimalObject> {
    @Override
    public DecimalObject reading(JsonReader jsonReader) throws IOException {
        DecimalObject decimal = null;
        jsonReader.beginObject();
        jsonReader.nextName();
        decimal = new DecimalObject(jsonReader.nextString());
        jsonReader.endObject();
        return decimal;
    }

    @Override
    public void writing(JsonWriter jsonWriter, DecimalObject value) throws IOException {
        jsonWriter.beginObject().name("$decimal").value(value.toString()).endObject();
    }
}
