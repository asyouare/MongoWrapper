package org.asyou.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import org.asyou.mongo.common.Assertions;
import org.asyou.mongo.common.Convert;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.exception.MongoAdapterException;
import org.asyou.mongo.exception.MongoErrorCode;
import org.asyou.mongo.query.IQuery;
import org.asyou.mongo.query.QueryMatcher;
import org.asyou.mongo.query.QueryObject;
import org.asyou.mongo.query.QueryUtil;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * 更新时query和value最好使用相同的类型
 * 更新的集合名称以adapter的collectionName为优先, 其次modelValue的类注解, 再次modelValue的类名称
 * Created by steven on 2016/9/23.
 */
public class Update {
    //region 构造器及内部成员
    public static Update create(MongoAdapter mongoAdapter){
        return new Update(mongoAdapter);
    }

    private MongoAdapter adapter;
    private IQuery queryMatcher;
    private IQuery bsonValue;
    private Class<?> clazz;

    private Update(MongoAdapter mongoAdapter){
        this.adapter = mongoAdapter;
    }

    //endregion

    public Update query(IQuery matcher){
        this.queryMatcher = matcher;
        return this;
    }

    public Update value(IQuery value){
        this.bsonValue = value;
        return this;
    }

    public Update as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    public long updateOne(String jsonQuery, String jsonValue) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        this.bsonValue = QueryObject.create(jsonValue);
        return update(UpdateType.UpdateOne);
    }

    public long updateOne(Bson bsonQuery, Bson bsonValue) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        this.bsonValue = QueryObject.create(bsonValue);
        return update(UpdateType.UpdateOne);
    }

    public <T> long updateOne(T modelQuery, T modelValue) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.bsonValue = QueryObject.create(modelValue);
        this.clazz = modelQuery.getClass();
        return update(UpdateType.UpdateOne);
    }

    public long updateMany(String jsonQuery, String jsonValue) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        this.bsonValue = QueryObject.create(jsonValue);
        return update(UpdateType.UpdateMany);
    }

    public long updateMany(Bson bsonQuery, Bson bsonValue) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        this.bsonValue = QueryObject.create(bsonValue);
        return update(UpdateType.UpdateMany);
    }

    public <T> long updateMany(T modelQuery, T modelValue) throws MongoAdapterException {
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.bsonValue = QueryObject.create(modelValue);
        this.clazz = modelQuery.getClass();
        return update(UpdateType.UpdateMany);
    }

    private long update(Update.UpdateType updateType) throws MongoAdapterException {
        MongoCollection<Document> collection = null;
        MongoCursor<Document> cursor = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null, use as(Class)", adapter.getCollectionName());

        Bson matcherBson = queryMatcher == null ? null : queryMatcher.toBson();
        Bson valueBson = this.bsonValue == null ? null : this.bsonValue.toBson();

        if ((null == valueBson || Convert.toBsonDocument(valueBson).isEmpty()) ||
                (null == matcherBson || Convert.toBsonDocument(matcherBson).isEmpty())) {
            throw MongoAdapterException.create(MongoErrorCode.UPDATE_VALUE_NULL + " update error: value is null");
        }

        if (UpdateType.UpdateOne == updateType){
            try{
                collection = adapter.getHost().getDatabase().getCollection(adapter.getCollectionName());
                UpdateResult result = collection.updateOne(matcherBson, valueBson);
                return result.getModifiedCount();
            }catch (Throwable te){
                throw MongoAdapterException.create(MongoErrorCode.UPDATE_ERROR + " update error: " + te.getMessage());
            }

        }else if (UpdateType.UpdateMany == updateType){
            try{
                collection = adapter.getHost().getDatabase().getCollection(adapter.getCollectionName());
                UpdateResult result = collection.updateMany(matcherBson, valueBson);
                return result.getModifiedCount();
            }catch (Throwable te){
                throw MongoAdapterException.create(MongoErrorCode.UPDATE_ERROR + " update error: " + te.getMessage());
            }
        }

        throw MongoAdapterException.create(MongoErrorCode.UNKNOWN_OPERATION.from(MongoErrorCode.UPDATE_ERROR));
    }

    private enum UpdateType {
        UpdateOne(1), UpdateMany(2);

        private int _value;
        private UpdateType(int value){
            this._value = value;;
        }
    }
}
