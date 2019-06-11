package org.asyou.mongo.common;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by steven on 17/9/22.
 */
public class Format {
    public static final String TEMPLATE_ISO_EN = "EEE MMM dd yyyy HH:mm:ss z";
    public static final String TEMPLATE_ISO_S3 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TEMPLATE_DATE = "yyyy-MM-dd";
    public static final String TEMPLATE_DATE_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String TEMPLATE_DATETIME_S3 = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String TEMPLATE_DATETIME_S6 = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(TEMPLATE_DATE);
    public static final DateTimeFormatter dateTimeFormatterISO = DateTimeFormatter.ofPattern(TEMPLATE_ISO_EN, Locale.ENGLISH);
    public static final DateTimeFormatter dateTimeFormatterISOS3 = DateTimeFormatter.ofPattern(TEMPLATE_ISO_S3);
    public static final DateTimeFormatter dateTimeFormatterSecond = DateTimeFormatter.ofPattern(TEMPLATE_DATE_SECOND);
    public static final DateTimeFormatter dateTimeFormatterS3 = DateTimeFormatter.ofPattern(TEMPLATE_DATETIME_S3);
    public static final DateTimeFormatter dateTimeFormatterS6 = DateTimeFormatter.ofPattern(TEMPLATE_DATETIME_S6);

    public static final SimpleDateFormat simpleDateFormat() { return new SimpleDateFormat(TEMPLATE_DATE);}
    public static final SimpleDateFormat simpleDateTimeFormatISO() { return new SimpleDateFormat(TEMPLATE_ISO_EN, Locale.ENGLISH);}
    public static final SimpleDateFormat simpleDateTimeFormatISOS3() { return new SimpleDateFormat(TEMPLATE_ISO_S3);}
    public static final SimpleDateFormat simpleDateTimeFormatSecond() { return new SimpleDateFormat(TEMPLATE_DATE_SECOND);}
    public static final SimpleDateFormat simpleDateTimeFormatS3() { return new SimpleDateFormat(TEMPLATE_DATETIME_S3);}
    public static final SimpleDateFormat simpleDateTimeFormatS6() { return new SimpleDateFormat(TEMPLATE_DATETIME_S6);}

}
