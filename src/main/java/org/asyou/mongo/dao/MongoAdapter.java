package org.asyou.mongo.dao;

import com.mongodb.client.MongoCursor;
import org.asyou.mongo.*;
import org.asyou.mongo.exception.MongoAdapterException;
import org.asyou.mongo.query.QueryUtil;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Mongo适配器，用于快速进行CRUD操作
 * Created by steven on 2016/9/27.
 */
public class MongoAdapter {
//    private final static Logger log = Logger.getLogger(MongoAdapter.class.getName());

    public static MongoAdapter create(String adapterId) throws MongoAdapterException {
        return new MongoAdapter(adapterId);
    }

    private ThreadLocal<String> collectionName;
    private MongoHost mongoHost;

    public MongoAdapter(String adapterId) throws MongoAdapterException {
        if (!MongoHostManager.containsId(adapterId)) {
            throw new MongoAdapterException(String.format("MongoAdapter id'%s' is not found",adapterId));
        }
        this.mongoHost = MongoHostManager.getSequoiaHost(adapterId);
        this.collectionName = new ThreadLocal<>();
    }

    //region getter

    public String getAdapterId(){ return this.mongoHost.getAdapterId(); }

    public MongoHost getHost() {
        return this.mongoHost;
    }

    public List<String> getDatabaseNames(){
        return getHost().getDatabaseNames();
    }

    public List<String> getCollectionNames(){
        return getHost().getCollectionNames();
    }

    public String getDatabaseName() {
        return this.getHost().getDatabaseName();
    }

    
    public String getCollectionName() {
        return collectionName.get();
    }

    
    public MongoAdapter collection(String collectionName){
        this.collectionName.set(collectionName);
        return this;
    }

    public MongoAdapter collectionNameFromClass(Class<?> clazz){
        this.collectionName.set(QueryUtil.getCollectionName(clazz,this.collectionName.get()));
        return this;
    }

    public MongoAdapter clearDatabaseName(){
        this.collectionName.remove();
        return this;
    }

    public MongoAdapter clearCollectionName(){
        this.collectionName.remove();
        return this;
    }
    //endregion

    //region Count

    public Count count() { return Count.create(this); }

    //endregion

    //region Find

    public Find find(){
        return Find.create(this);
    }


    public <T> Find find(T modelQuery) {
        return Find.create(this,modelQuery);
    }


    public Find find(String jsonQuery) {
        return Find.create(this,jsonQuery);
    }


    public Find find(Bson bsonQuery) {
        return Find.create(this,bsonQuery);
    }

    //endregion

    //region Insert

    public Insert insert(){
        return Insert.create(this);
    }

    //endregion

    //region Updates 更新的集合名称以adapter的collectionName为优先, 其次modelValue的类注解, 再次modelValue的类名称

    public Update update(){
        return Update.create(this);
    }
    
    //endregion

    //region Delete

    public Delete delete() {
        return Delete.create(this);
    }

    //endregion

    //region Aggregate

    public Aggregate aggregate() {
        return Aggregate.create(this);
    }

    //endregion

}
