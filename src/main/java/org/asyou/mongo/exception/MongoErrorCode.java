package org.asyou.mongo.exception;

public class MongoErrorCode {
    public static final MongoErrorCodeStruct ADAPTER_ID_EXISTED        = new MongoErrorCodeStruct(-001, "adapter id existed");
    public static final MongoErrorCodeStruct ADAPTER_ID_NOT_EXIST      = new MongoErrorCodeStruct(-002, "adapter id not exist");
    public static final MongoErrorCodeStruct DATABASE_NULL             = new MongoErrorCodeStruct(-003, "database null");
    public static final MongoErrorCodeStruct COLLECTION_NULL           = new MongoErrorCodeStruct(-005, "collection null");

    public static final MongoErrorCodeStruct FIND__ERROR               = new MongoErrorCodeStruct(-101, "find error");
    public static final MongoErrorCodeStruct COUNT__ERROR              = new MongoErrorCodeStruct(-102, "count error");
    public static final MongoErrorCodeStruct AGGREGATE__ERROR          = new MongoErrorCodeStruct(-103, "aggregate error");

    public static final MongoErrorCodeStruct INSERT_VALUE_NULL         = new MongoErrorCodeStruct(-111, "insert value null");
    public static final MongoErrorCodeStruct INSERT_ERROR              = new MongoErrorCodeStruct(-112, "insert error");

    public static final MongoErrorCodeStruct UPDATE_VALUE_NULL         = new MongoErrorCodeStruct(-121, "delete value null");
    public static final MongoErrorCodeStruct UPDATE_ERROR              = new MongoErrorCodeStruct(-122, "delete error");

    public static final MongoErrorCodeStruct DELETE_VALUE_NULL         = new MongoErrorCodeStruct(-131, "delete value null");
    public static final MongoErrorCodeStruct DELETE_ERROR              = new MongoErrorCodeStruct(-132, "delete error");

    public static final MongoErrorCodeStruct UNKNOWN_OPERATION         = new MongoErrorCodeStruct(-999, "unknown operation");     //未知操作
}
