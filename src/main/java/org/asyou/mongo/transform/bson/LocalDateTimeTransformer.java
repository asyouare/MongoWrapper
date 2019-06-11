package org.asyou.mongo.transform.bson;

import org.asyou.mongo.common.Format;
import org.bson.Transformer;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by steven on 17/10/24.
 */
public class LocalDateTimeTransformer implements Transformer {
    @Override
    public Object transform(Object o) {
        if (o instanceof Date) {
            Date date = (Date) o;
            String dateStr = LocalDateTime.ofInstant(date.toInstant(), Format.DEFAULT_ZONE_ID)
                    .format(Format.dateTimeFormatterS6);
            return LocalDateTime.parse(dateStr, Format.dateTimeFormatterS6);
        }else if (o instanceof LocalDateTime){
            String str = ((LocalDateTime)o).format(Format.dateTimeFormatterS6);
            String dateStr = str.substring(0, str.lastIndexOf('.'));
//            String incStr = str.substring(str.lastIndexOf('.') + 1);
            Date date = null;
            try {
                date = Format.simpleDateTimeFormatS6().parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            int seconds = (int)(date.getTime()/1000);
//            int inc = Integer.parseInt(incStr);
            return date;
        }
        return null;
    }
}
