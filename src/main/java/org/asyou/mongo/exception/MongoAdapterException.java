package org.asyou.mongo.exception;

/**
 * Created by steven on 17/10/26.
 */
public class MongoAdapterException extends MongoCustomException {
    public MongoAdapterException(){
        super();
    }

    public MongoAdapterException(String message) {
        super(message);
    }

    public static MongoAdapterException create(Throwable e){
        return new MongoAdapterException(MongoCustomException.getExceptionString(e));
    }

    public static MongoAdapterException create(String message){
        return new MongoAdapterException(message);
    }
}
