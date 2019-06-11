package org.asyou.mongo.query;

import org.bson.conversions.Bson;

public class QueryObject extends AQueryBson implements IQuery {
    public static QueryObject create(){
        return new QueryObject();
    }

    public static QueryObject create(String jsonQuery){
        return new QueryObject(jsonQuery);
    }

    public static <T> QueryObject create(T modelQuery){
        return new QueryObject(modelQuery);
    }

    public static QueryObject create(Bson bsonQuery){
        return new QueryObject(bsonQuery);
    }

    public QueryObject(){ super(); }

    public QueryObject(Bson bsonQuery){
        super(bsonQuery);
    }

    public QueryObject(String jsonQuery){
        super(jsonQuery);
    }

    public <T> QueryObject(T modelQuery){
        super(modelQuery);
    }
}
