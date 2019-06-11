package org.asyou.mongo;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.asyou.mongo.common.Assertions;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.exception.MongoAdapterException;
import org.asyou.mongo.exception.MongoErrorCode;
import org.asyou.mongo.query.QueryAggregate;
import org.asyou.mongo.query.QueryUtil;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

/**
 * Created by steven on 2016/9/23.
 */
public class Aggregate {
    //region 构造器及内部成员
    public static Aggregate create(MongoAdapter mongoAdapter){
        return new Aggregate(mongoAdapter);
    }

    private MongoAdapter adapter;
    private QueryAggregate queryAggregate;
    private Class<?> clazz;

    private Aggregate(MongoAdapter mongoAdapter){
        this.adapter = mongoAdapter;
    }

    //endregion


    public QueryAggregate getQueryAggregate() {
        if (null == this.queryAggregate) {
            this.queryAggregate = QueryAggregate.create();
        }
        return queryAggregate;
    }

    public Aggregate match(QueryAggregate aggregate){
        this.queryAggregate = aggregate;
        return this;
    }

    public Aggregate as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    public List<Document> aggregate(List<Bson> queryList) throws MongoAdapterException {
        MongoCollection<Document> collection = null;
        MongoCursor<Document> cursor = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null, use as(Class)", adapter.getCollectionName());

        List<Document> list = new ArrayList<>();
        try {
            collection = adapter.getHost().getDatabase().getCollection(adapter.getCollectionName());
            AggregateIterable<Document> aggregateIterable = collection.aggregate(queryList);
            cursor = aggregateIterable.iterator();
            while(cursor.hasNext()){
                list.add(cursor.next());
            }
        }catch (Throwable te){
            te.printStackTrace();
            throw MongoAdapterException.create(MongoErrorCode.AGGREGATE__ERROR + " AGGREGATE error: " + te.getMessage());
        }finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return list;
    }

    public Map<String, Number> values(List<Bson> queryList) throws MongoAdapterException {
        Map<String,Number> map = new LinkedHashMap<>();
        List<Document> resultList = aggregate(queryList);
        if (null != resultList){
            int size = resultList.size();
            for(int i=0;i<size;i++){
                if (resultList.get(i).containsKey("_id")) {
                    resultList.get(i).remove("_id");
                }
                Set<String> keys = resultList.get(i).keySet();
                for(String key : keys){
                    if (!("_id".equals(key))) {
                        map.put(key, (Number) resultList.get(i).get(key));
                    }
                }
            }
        }
        return map;
    }

    public Map<String, Number> values() throws MongoAdapterException {
        return values(this.queryAggregate.toBsonList());
    }

    public Number sum(String fieldName) throws MongoAdapterException {
        String displayName = "sum";
        getQueryAggregate().group().sum(fieldName, displayName);
        return values().get(displayName);
    }

    public Number avg(String fieldName) throws MongoAdapterException {
        String displayName = "avg";
        getQueryAggregate().group().avg(fieldName, displayName);
        return values().get(displayName);
    }

    public Number first(String fieldName) throws MongoAdapterException {
        String displayName = "first";
        getQueryAggregate().group().first(fieldName, displayName);
        return values().get(displayName);
    }

    public Number last(String fieldName) throws MongoAdapterException {
        String displayName = "last";
        getQueryAggregate().group().last(fieldName, displayName);
        return values().get(displayName);
    }

    public Number max(String fieldName) throws MongoAdapterException {
        String displayName = "max";
        getQueryAggregate().group().max(fieldName, displayName);
        return values().get(displayName);
    }

    public Number min(String fieldName) throws MongoAdapterException {
        String displayName = "min";
        getQueryAggregate().group().min(fieldName, displayName);
        return values().get(displayName);
    }
    
    public Number count(String fieldName) throws MongoAdapterException {
        String displayName = "count";
        getQueryAggregate().group().min(fieldName, displayName);
        return values().get(displayName);
    }
}
