package org.asyou.mongo.transform.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.asyou.mongo.common.Format;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by steven on 17/7/18.
 */
public class DateTypeAdapter extends ABasicTypeAdapter<Date> {
    @Override
    public Date reading(final JsonReader jsonReader) throws IOException {
        Date date = null;
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString();
        //时间格式
        try {
            date = Format.simpleDateTimeFormatISOS3().parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //时间戳方式 long
//        date = new Date(Long.valueOf(value));
        jsonReader.endObject();
        return date;
    }

    @Override
    public void writing(final JsonWriter jsonWriter, final Date value) throws IOException {
        //时间格式
        String dateStr = Format.simpleDateTimeFormatISOS3().format(value);
        jsonWriter.beginObject().name("$date").value(dateStr).endObject();
        ////时间戳方式 long
//        jsonWriter.beginObject().name("$date").value(value.getTime()).endObject();
    }
}
