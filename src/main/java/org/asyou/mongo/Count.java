package org.asyou.mongo;

import com.mongodb.client.MongoCollection;
import org.asyou.mongo.common.Assertions;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.exception.MongoAdapterException;
import org.asyou.mongo.exception.MongoErrorCode;
import org.asyou.mongo.query.IQuery;
import org.asyou.mongo.query.QueryMatcher;
import org.asyou.mongo.query.QueryUtil;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Created by steven on 17/5/3.
 */
public class Count {
    //region 构造器及内部成员
    public static Count create(MongoAdapter mongoAdapter){
        return new Count(mongoAdapter);
    }

    private MongoAdapter adapter;
    private IQuery queryMatcher;
    private Class<?> clazz;

    private Count(MongoAdapter mongoAdapter){
        this.adapter = mongoAdapter;
    }
    //endregion

    public Count as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    public long count(String jsonQuery) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        return this.count();
    }

    public long count(Bson bsonQuery) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        return this.count();
    }

    public <T> long count(T modelQuery) throws MongoAdapterException {
        this.clazz = modelQuery.getClass();
        this.queryMatcher = QueryMatcher.create(modelQuery);
        return this.count();
    }

    public long count() throws MongoAdapterException {
        MongoCollection<Document> collection = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null, use as(Class)", adapter.getCollectionName());

        Bson matcherBson = queryMatcher == null ? new BsonDocument() : queryMatcher.toBson();

        long count = 0;
        try {
            collection = adapter.getHost().getDatabase().getCollection(adapter.getCollectionName());
            count = collection.count(matcherBson);
        }catch (Throwable te){
            throw MongoAdapterException.create(MongoErrorCode.COUNT__ERROR + " count error: " + te.getMessage());
        }
        return count;
    }
}
