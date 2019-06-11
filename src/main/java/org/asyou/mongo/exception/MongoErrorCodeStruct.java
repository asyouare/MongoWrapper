package org.asyou.mongo.exception;

public class MongoErrorCodeStruct {
    private int code;
    private String message;

    public MongoErrorCodeStruct(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString(){
        return this.code + " " + this.message;
    }

    public String from(MongoErrorCodeStruct exceptionStruct){
        return this.toString() + " from: " + exceptionStruct.toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
