package org.asyou.mongo.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;
import org.asyou.mongo.base.Config;
import org.asyou.mongo.common.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Mongo服务器管理器，用于管理全局所有的Mongo服务器
 * Created by steven on 2016/9/27.
 */
public class MongoHost {
//    private final static Logger log = Logger.getLogger(MongoHost.class.getName());

    private Config config;
    private MongoClient mongoClient;

    public MongoHost(Config config) {
        this.config = config;
        init();
    }

    private void init(){
        MongoClientOptions options = config.getMongoClientOptions();
        ServerAddress addres = new ServerAddress(config.getHostName(), config.getPort());
        if (StringUtils.isNotBlank(this.config.getUserName())
                && StringUtils.isNotBlank(this.config.getPassword())
                && StringUtils.isNotBlank(this.config.getDatabaseName())){
            MongoCredential credential = MongoCredential.createCredential(
                    this.config.getUserName(),
                    this.config.getDatabaseName(),
                    this.config.getPassword().toCharArray()
            );
            this.mongoClient = new MongoClient(addres, Arrays.asList(credential),options);
        }else {
            this.mongoClient = new MongoClient(addres, options);
        }
    }

    public String getAdapterId(){ return config.getAdapterId(); }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public String getDatabaseName(){
        return this.config.getDatabaseName();
    }

    public MongoDatabase getDatabase(){
        return mongoClient.getDatabase(config.getDatabaseName());
    }

    public MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName);
    }

    public void setDatabaseName(String dbName){
        config.setDatabaseName(dbName);
    }

    public List<String> getDatabaseNames(){
        List<String> list = new ArrayList<>();
        MongoCursor<String> iterable = getMongoClient().listDatabaseNames().iterator();
        while(iterable.hasNext()) {
            list.add(iterable.next());
        }
        return list;
    }

    public List<String> getCollectionNames(){
        return getCollectionNames(getDatabaseName());
    }

    public List<String> getCollectionNames(String databaseName){
        List<String> list = new ArrayList<>();
        MongoCursor<String> iterable = getDatabase(databaseName).listCollectionNames().iterator();
        while(iterable.hasNext()) {
            list.add(iterable.next());
        }
        return list;
    }

    public synchronized void close(){
        close(null);
    }

    public synchronized void close(MongoClient client){
        if (client != null){
            if (client instanceof MongoClient){
                client.close();
                client = null;
            }
        }else {
            if (mongoClient != null) {
                mongoClient.close();
                mongoClient = null;
            }
        }
    }

    public Config getConfig(){
        return this.config;
    }

    @Override
    public String toString(){
        return this.config.toString();
    }


}
