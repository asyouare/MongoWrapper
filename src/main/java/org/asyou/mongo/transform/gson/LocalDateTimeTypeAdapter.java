package org.asyou.mongo.transform.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.asyou.mongo.common.Format;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by steven on 17/10/24.
 */
public class LocalDateTimeTypeAdapter extends ABasicTypeAdapter<LocalDateTime>  {
    @Override
    public LocalDateTime reading(JsonReader jsonReader) throws IOException {
        LocalDateTime dateTime = null;
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString();
        dateTime = LocalDateTime.parse(value, Format.dateTimeFormatterISOS3);
        jsonReader.endObject();
        return dateTime;
    }

    @Override
    public void writing(JsonWriter jsonWriter, LocalDateTime value) throws IOException {
        jsonWriter.beginObject().name("$date").value(value.format(Format.dateTimeFormatterISOS3)).endObject();
    }
}
