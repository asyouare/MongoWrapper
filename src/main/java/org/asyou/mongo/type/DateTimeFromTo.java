package org.asyou.mongo.type;

import org.asyou.mongo.common.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Administrator on 2016/5/17.
 */
public class DateTimeFromTo {

    private String fieldName;
    private LocalDateTime from;
    private LocalDateTime to;

    public DateTimeFromTo(String fieldName){
        Assertions.notNull("DateTimeFromTo fieldName ", fieldName);
        this.fieldName = fieldName;
        from = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0,0));
        to = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59,999999));
    }

    public DateTimeFromTo(String fieldName, LocalDateTime fromDate,LocalDateTime toDate){
        this.fieldName = fieldName;
        from = fromDate;
        to = toDate;
    }

    public String toJson(){
        if (!fieldName.isEmpty()){
            return String.format("{%s:{$gt:%s,$lt:%s}}", fieldName, from, to);
        }else{
            return "";
        }
    }

    public DateTimeFromTo field(String name){
        this.fieldName = name;
        return this;
    }

    public DateTimeFromTo from(LocalDateTime from){
        this.from = from;
        return this;
    }

    public DateTimeFromTo to(LocalDateTime to){
        this.to = to;
        return this;
    }

    public static DateTimeFromTo fromField(String name){
        return new DateTimeFromTo(name);
    }

    public String getFieldName(){
        return fieldName;
    }

    public void setFieldName(String value){
        this.fieldName = value;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }
}
