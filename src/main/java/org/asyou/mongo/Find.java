package org.asyou.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.asyou.mongo.common.Assertions;
import org.asyou.mongo.common.Convert;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.exception.MongoAdapterException;
import org.asyou.mongo.exception.MongoErrorCode;
import org.asyou.mongo.query.IQuery;
import org.asyou.mongo.query.QueryMatcher;
import org.asyou.mongo.query.QueryObject;
import org.asyou.mongo.query.QueryUtil;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.LinkedList;
import java.util.List;

public class Find {
    //region 构造器及内部成员
    public static Find create(MongoAdapter mongoAdapter){
        return new Find(mongoAdapter);
    }

    public static Find create(MongoAdapter mongoAdapter, String jsonQuery){
        return new Find(mongoAdapter, jsonQuery);
    }

    public static Find create(MongoAdapter mongoAdapter, Bson bsonQuery){
        return new Find(mongoAdapter, bsonQuery);
    }

    public static <T> Find create(MongoAdapter mongoAdapter, T modelQuery){
        return new Find(mongoAdapter, modelQuery);
    }

    private MongoAdapter adapter;
    private IQuery queryMatcher;
    private IQuery projectionFields;
    private IQuery sortFields;
    private int skipCount = 0;
    private int limitCount = 0;
    private long totalCount = 0;
    private Class<?> clazz;

    private Find(MongoAdapter mongoAdapter){
        this.adapter = mongoAdapter;
    }

    private Find(MongoAdapter mongoAdapter, String jsonQuery){
        this.adapter = mongoAdapter;
        this.queryMatcher = QueryMatcher.create(jsonQuery);
    }

    private Find(MongoAdapter mongoAdapter, Bson bsonQuery){
        this.adapter = mongoAdapter;
        this.queryMatcher = QueryMatcher.create(bsonQuery);
    }

    private <T> Find(MongoAdapter mongoAdapter, T modelQuery){
        this.adapter = mongoAdapter;
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.clazz = modelQuery.getClass();
    }
    //endregion

    public Find match(IQuery matcher){
        this.queryMatcher = matcher;
        return this;
    }

    public Find projection(IQuery projection){
        this.projectionFields = projection;
        return this;
    }

    public Find projection(String... fieldNames){
        this.projectionFields = QueryObject.create(Projections.include(fieldNames));
        return this;
    }

    public Find sort(IQuery sort){
        this.sortFields = sort;
        return this;
    }

    public Find desc(String... fieldNames){
        this.sortFields = QueryObject.create(Sorts.descending(fieldNames));
        return this;
    }

    public Find asc(String... fieldNames){
        this.sortFields = QueryObject.create(Sorts.ascending(fieldNames));
        return this;
    }

    public Find skip(int skipCount){
        this.skipCount = skipCount;
        return this;
    }

    public Find limit(int limitCount){
        this.limitCount = limitCount;
        return this;
    }

    public Find as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    public <T> T findOne() throws MongoAdapterException {
        this.skipCount = 0;
        this.limitCount = 1;
        List<T> list = getList(FindType.FindOne);
        if (list.size() < 1) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public <T> List<T> findMany() throws MongoAdapterException {
        return getList(FindType.FindMany);
    }

    public <T> Page<T> page(final int pageIndex, final int perPageCount) throws MongoAdapterException {
        this.skipCount = pageIndex * perPageCount;
        this.limitCount = perPageCount;
        List<T> list = getList(FindType.FindPage);
        Page<T> page = new Page(pageIndex, perPageCount, totalCount, list);
        return page;
    }

    private <T> List<T> getList(FindType findType) throws MongoAdapterException {
        List<T> list = new LinkedList<T>();
        MongoCollection<Document> collection = null;
        MongoCursor<Document> cursor = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null, use as(Class)", adapter.getCollectionName());

        Bson matcherBson = queryMatcher == null ? new BsonDocument() : queryMatcher.toBson();
        Bson projectionBson = projectionFields == null ? null : projectionFields.toBson();
        Bson sortBson = sortFields == null ? null : sortFields.toBson();

        try {
            collection = adapter.getHost().getDatabase().getCollection(adapter.getCollectionName());
            FindIterable<Document> finder = collection.find(matcherBson);
            if (null != projectionBson) {
                finder.projection(projectionBson);
            }
            if (null != sortBson) {
                finder.sort(sortBson);
            }
            if (skipCount > 0) {
                finder.skip(skipCount);
            }
            if (limitCount > 0) {
                finder.limit(limitCount);
            }
            cursor = finder.iterator();
            if (null == this.clazz || String.class == this.clazz) {
                while (cursor.hasNext()) {
                    list.add((T) Convert.toJson(cursor.next()));
                }
            } else {
                while (cursor.hasNext()) {
                    list.add(Convert.toModel(cursor.next(), (Class<T>) this.clazz));
                }
            }
        }catch (Throwable te){
            throw MongoAdapterException.create(MongoErrorCode.FIND__ERROR + " find error: " + te.getMessage());
        }
        finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        if (findType == FindType.FindPage) {
            totalCount = collection.count(matcherBson);
        }

        return list;
    }

    private enum FindType {
        FindOne(1), FindMany(2), FindPage(4);

        private int _value;
        private FindType(int value){
            this._value = value;;
        }
    }
}
