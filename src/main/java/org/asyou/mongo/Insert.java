package org.asyou.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.asyou.mongo.common.Assertions;
import org.asyou.mongo.common.Convert;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.exception.MongoAdapterException;
import org.asyou.mongo.exception.MongoErrorCode;
import org.asyou.mongo.query.QueryUtil;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

/**
 * Created by steven on 2016/9/23.
 */
public class Insert {
    //region 构造器及内部成员
    public static Insert create(MongoAdapter mongoAdapter){
        return new Insert(mongoAdapter);
    }

    private MongoAdapter adapter;
    private Class<?> clazz;

    private Insert(MongoAdapter mongoAdapter){
        this.adapter = mongoAdapter;
    }

    //endregion

    public Insert as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    public <T> int insertOne(T modelValue) throws MongoAdapterException {
        this.clazz = modelValue.getClass();
        return insert((Document) Convert.toBson(modelValue),null,InsertType.InsertOne);
    }

    public int insertOne(String jsonValue) throws MongoAdapterException {
        return insert((Document) Convert.toBson(jsonValue),null,InsertType.InsertOne);
    }

    public int insertOne(Bson bsonValue) throws MongoAdapterException {
        return insert(Document.parse(Convert.toBsonDocument(bsonValue).toJson()),null,InsertType.InsertOne);
    }

    public <T> int insertMany(List<T> modelValues) throws MongoAdapterException {
        if (null == modelValues) {
            throw MongoAdapterException.create(MongoErrorCode.INSERT_VALUE_NULL.toString());
        }
        if (modelValues.size() < 1) {
            return 0;
        }
        this.clazz = modelValues.get(0).getClass();
        return insert(null, Convert.transformList(modelValues),InsertType.InsertMany);
    }

    private int insert(Document value, List<Document> values, InsertType insertType) throws MongoAdapterException {
        MongoCollection<Document> collection = null;
        MongoCursor<Document> cursor = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null, use as(Class)", adapter.getCollectionName());

        if (InsertType.InsertOne == insertType){
            if (null == value) {
                throw MongoAdapterException.create(MongoErrorCode.INSERT_VALUE_NULL + " insert error: value is null");
            }
            try {
                collection = adapter.getHost().getDatabase().getCollection(adapter.getCollectionName());
                collection.insertOne(value);
            }catch (Throwable te){
                throw MongoAdapterException.create(MongoErrorCode.INSERT_ERROR + " insert error: " + te.getMessage());
            }
            return 1;
        }else if (InsertType.InsertMany == insertType){
            if (null == values) {
                throw MongoAdapterException.create(MongoErrorCode.INSERT_VALUE_NULL + " insert error: values is null");
            }
            if (values.size() < 1) {
                return 0;
            }
            try{
                collection = adapter.getHost().getDatabase().getCollection(adapter.getCollectionName());
                collection.insertMany(values);
            }catch (Throwable te){
                throw MongoAdapterException.create(MongoErrorCode.INSERT_ERROR + " insert error: " + te.getMessage());
            }
            return values.size();
        }
        throw MongoAdapterException.create(MongoErrorCode.UNKNOWN_OPERATION.from(MongoErrorCode.INSERT_ERROR));
    }

    private enum InsertType {
        InsertOne(1), InsertMany(2);

        private int _value;
        private InsertType(int value){
            this._value = value;;
        }
    }
}
