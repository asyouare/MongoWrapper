package org.asyou.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.asyou.mongo.common.Assertions;
import org.asyou.mongo.common.Convert;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.exception.MongoAdapterException;
import org.asyou.mongo.exception.MongoErrorCode;
import org.asyou.mongo.query.IQuery;
import org.asyou.mongo.query.QueryMatcher;
import org.asyou.mongo.query.QueryUtil;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Created by steven on 2016/12/15.
 */
public class Delete {
    //region 构造器及内部成员
    public static Delete create(MongoAdapter mongoAdapter){
        return new Delete(mongoAdapter);
    }

    private MongoAdapter adapter;
    private IQuery queryMatcher;
    private Class<?> clazz;

    private Delete(MongoAdapter mongoAdapter){
        this.adapter = mongoAdapter;
    }

    //endregion

    public Delete query(IQuery matcher){
        this.queryMatcher = matcher;
        return this;
    }

    public Delete as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    public long deleteOne(String jsonQuery) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        return delete(DeleteType.DeleteOne);
    }

    public long deleteOne(Bson bsonQuery) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        return delete(DeleteType.DeleteOne);
    }

    public <T> long deleteOne(T modelQuery) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.clazz = modelQuery.getClass();
        return delete(DeleteType.DeleteOne);
    }

    public long deleteMany(String jsonQuery) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        return delete(DeleteType.DeleteMany);
    }

    public long deleteMany(Bson bsonQuery) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        return delete(DeleteType.DeleteMany);
    }

    public <T> long deleteMany(T modelQuery) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.clazz = modelQuery.getClass();
        return delete(DeleteType.DeleteMany);
    }

    private long delete(DeleteType updateType) throws MongoAdapterException {
        MongoCollection<Document> collection = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null, use as(Class)", adapter.getCollectionName());

        Bson matcherBson = queryMatcher == null ? null : queryMatcher.toBson();

        if (null == matcherBson || Convert.toBsonDocument(matcherBson).isEmpty()) {
            throw MongoAdapterException.create(MongoErrorCode.DELETE_VALUE_NULL.toString());
        }

        if (DeleteType.DeleteOne == updateType){
            try{
                collection = adapter.getHost().getDatabase().getCollection(adapter.getCollectionName());
                DeleteResult result = collection.deleteOne(matcherBson);
                return result.getDeletedCount();
            }catch (Throwable te){
                throw MongoAdapterException.create(MongoErrorCode.DELETE_ERROR + " delete error: " + te.getMessage());
            }

        }else if (DeleteType.DeleteMany == updateType){
            try{
                collection = adapter.getHost().getDatabase().getCollection(adapter.getCollectionName());
                DeleteResult result = collection.deleteMany(matcherBson);
                return result.getDeletedCount();
            }catch (Throwable te){
                throw MongoAdapterException.create(MongoErrorCode.DELETE_ERROR + " delete error: " + te.getMessage());
            }
        }

        throw MongoAdapterException.create(MongoErrorCode.UNKNOWN_OPERATION.from(MongoErrorCode.DELETE_ERROR));
    }

    private enum DeleteType {
        DeleteOne(1), DeleteMany(2);

        private int _value;
        private DeleteType(int value){
            this._value = value;;
        }
    }
}
