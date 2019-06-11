package org.asyou.mongo.type;

import org.asyou.mongo.common.Format;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by steven on 17/10/24.
 */
public final class DateTimeFactory {
    public static LocalDateTime todayMin(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0,0));
    }

    public static LocalDateTime todayMax(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59,999999999));
    }

    public static String format(Date date, String pattern){
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), Format.DEFAULT_ZONE_ID);
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static long getTime(LocalDateTime localDateTime){
        return Timestamp.valueOf(localDateTime).getTime();
    }
}
