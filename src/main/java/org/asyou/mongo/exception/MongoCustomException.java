package org.asyou.mongo.exception;

/**
 * Created by steven on 2017/4/23.
 */
public abstract class MongoCustomException extends Exception{
    public MongoCustomException(){
        super();
    }

    public MongoCustomException(String message) {
        super(message);
    }

    public String getExceptionString(){
        StackTraceElement[] stes = this.getStackTrace();
        StackTraceElement ste = (stes != null && stes.length > 0) ? stes[0] : null;
        if (ste != null) {
            return String.format("%s - [%s(%s)]", this.getMessage(), ste.getFileName(), ste.getLineNumber());
        } else {
            return this.getMessage();
        }
    }

    public static String getExceptionString(Throwable e){
        StackTraceElement[] stes = e.getStackTrace();
        StackTraceElement ste = (stes != null && stes.length > 0) ? stes[0] : null;
        if (ste != null)
//            return String.format("%s - [%s] [%s.%s] [%s]", e.getMessage(), ste.getFileName(), ste.getClassName(), ste.getMethodName(), ste.getLineNumber());
        {
            return String.format("%s - [%s(%s)]", e.getMessage(), ste.getFileName(), ste.getLineNumber());
        } else {
            return e.getMessage();
        }
    }
}
