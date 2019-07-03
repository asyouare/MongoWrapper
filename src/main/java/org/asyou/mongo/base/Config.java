package org.asyou.mongo.base;

import com.mongodb.MongoClientOptions;
import org.asyou.mongo.common.Convert;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Administrator on 2016/4/5.
 */
public class Config implements Cloneable,Serializable {

    public static Config build(){
        return new Config();
    }

    private String adapterId;
    private String hostName;
    private Integer port;
    private String userName;
    private String password;
    private String databaseName;

    private MongoClientOptions mongoClientOptions;

    public Config(){
        this.mongoClientOptions = this.default_mongoClientOptions();
    }

    public Config(String adapterId, String hostName, Integer port, String databaseName){
        default_init(adapterId, hostName, port, null, null, databaseName, default_mongoClientOptions());
    }

    public Config(String adapterId, String hostName, Integer port, String userName, String password, String databaseName){
        default_init(adapterId, hostName, port, userName, password, databaseName, default_mongoClientOptions());
    }

    public Config(String adapterId, String hostName, Integer port, String userName, String password, String databaseName, MongoClientOptions options) {
        default_init(adapterId,hostName,port,userName,password,databaseName,options);
    }

    private void default_init(String adapterId,
                  String hostName, Integer port,
                  String userName, String password,
                  String databaseName,
                  MongoClientOptions options) {
        this.adapterId = adapterId;
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.databaseName = databaseName;
        this.mongoClientOptions = options;
    }

    private MongoClientOptions default_mongoClientOptions(){
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        //线程池最大连接数
        build.connectionsPerHost(100);
        //创建连接超时时间，仅在创建时使用一次
        build.connectTimeout(10 * 1000);
        //等待可用连队列数，connectionsPerHost的倍数
        build.threadsAllowedToBlockForConnectionMultiplier(5);
        //最大等可用连接时间
        build.maxWaitTime(60 * 1000);
        //连接池中连接的最大生存时间，超过将关闭，必要时创建新连接
//        build.maxConnectionLifeTime(10 * 60 * 1000);
        //连接最大空闲时间
        build.maxConnectionIdleTime(30 * 1000);

        return build.build();
    }

    public String getAdapterId(){
        return adapterId;
    }

    public Config setAdapterId(String adapterId){
        this.adapterId = adapterId;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public Config setHostName(String hostname) {
        this.hostName = hostname;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public Config setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Config setUserName(String username) {
        this.userName = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Config setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public Config setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public MongoClientOptions getMongoClientOptions() {
        return mongoClientOptions;
    }

    public Config setMongoClientOptions(MongoClientOptions mongoClientOptions) {
        this.mongoClientOptions = mongoClientOptions;
        return this;
    }

    @Override
    public String toString() {
        return Convert.toJson(this);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object obj = super.clone();
        return obj;
    }
}
