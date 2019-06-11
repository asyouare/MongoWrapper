package mongo;

import adapter.AdapterFactory;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ConnectionPoolSettings;
import org.asyou.mongo.common.Convert;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.dao.MongoHostInfo;
import org.asyou.mongo.exception.MongoAdapterException;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestMongoClient {
    MongoClient client;

    @Before
    public void Test_Init(){
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.addConnectionPoolListener(new CustomConnectionPoolListener());
        //线程池最大连接数
        build.connectionsPerHost(200);
        //创建连接超时时间，仅在创建时使用一次
        build.connectTimeout(10 * 1000);
        //等待可用连队列数，connectionsPerHost的倍数
        build.threadsAllowedToBlockForConnectionMultiplier(5);
        //最大等可用连接时间
        build.maxWaitTime(60 * 1000);
        //连接池中连接的最大生存时间，超过将关闭，必要时创建新连接
//        build.maxConnectionLifeTime(10 * 60 * 1000);
        //连接最大空闲时间
        build.maxConnectionIdleTime(60 * 1000);
        MongoClientOptions options = build.build();
        System.out.println(options.getMaxConnectionLifeTime());
        System.out.println(options.getMaxConnectionIdleTime());
        client = new MongoClient(new ServerAddress(), build.build());
    }

    @Test
    public void Test_MongoClient() throws MongoAdapterException, InterruptedException {
        Logger log = Logger.getLogger("org.mongodb.driver");
        log.setLevel(Level.OFF);
        System.out.println(client.getDatabase("local").runCommand(new Document("serverStatus","")).get("connections"));
        for(int i =0;i<1000;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=0;j<10;j++) {
                        MongoConnect();
                    }
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(client.getDatabase("local").runCommand(new Document("serverStatus","")).get("connections"));
        Thread.sleep(3*1000);
        System.out.println(client.getDatabase("local").runCommand(new Document("serverStatus","")).get("connections"));
    }

    public void MongoConnect(){
        MongoDatabase mdb = null;
        MongoCollection collection = null;
        mdb = client.getDatabase("tuhao-data");
        collection = mdb.getCollection("demo");
        collection.insertOne(Convert.toBson("{a:1}"));
    }

    @Test
    public void Test_Options(){

    }
}
