package org.asyou.mongo.transform.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by steven on 17/7/19.
 */
public class BigDecimalTypeAdapter extends ABasicTypeAdapter<BigDecimal> {
    @Override
    public BigDecimal reading(JsonReader jsonReader) throws IOException {
        BigDecimal decimal = null;
        jsonReader.beginObject();
        jsonReader.nextName();
        decimal = new BigDecimal(jsonReader.nextString());
        jsonReader.endObject();
        return decimal;
    }

    @Override
    public void writing(JsonWriter jsonWriter, BigDecimal value) throws IOException {
        jsonWriter.beginObject().name("$decimal").value(value.toString()).endObject();
    }
}
