//package adapter;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;
//import config.DataConfig;
//import org.apache.commons.lang3.StringUtils;
//import org.asyou.mongo.base.Config;
//import org.asyou.mongo.base.ConfigManager;
//import org.asyou.mongo.dao.MongoAdapter;
//import org.bson.Document;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by steven on 17/5/3.
// */
//public class TestPool {
//    private MongoClient mongoClient;
//
//    @Before
//    public void before_init() throws Exception {
////        Logger log = Logger.getLogger("org.mongodb.driver");
////        log.setLevel(Level.OFF);
//        ConfigManager.putMongoConfig(new DataConfig());
//
//        Config config = new Config();
//        config.setId("tuhao-data");
//        config.setHostName("192.168.15.100");
//        config.setPort(27017);
//        config.setUserName(null);
//        config.setPassword(null);
//        config.setDatabaseName("tuhao-data");
//        config.setPoolSize(100);
//        config.setBlockSize(10);
//        config.setConnectTimeout(5000);
//        config.setMaxWaitTime(5000);
//
//        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
//        build.connectionsPerHost(config.getPoolSize());
//        build.threadsAllowedToBlockForConnectionMultiplier(config.getBlockSize());
//        build.connectTimeout(config.getConnectTimeout());
//        build.maxWaitTime(config.getMaxWaitTime());
//
//        MongoClientOptions options = build.build();
//
//        ServerAddress addres = new ServerAddress(config.getHostName(), config.getPort());
//
//        if (StringUtils.isNotBlank(config.getUserName()) && StringUtils.isNotBlank(config.getPassword())){
//            List<MongoCredential> credentialsList = new ArrayList<>();
//            mongoClient = new MongoClient(addres,credentialsList,options);
//        }else {
//            mongoClient = new MongoClient(addres, options);
//        }
//    }
//
//    @Test
//    public void TestDatabase(){
//        MongoDatabase db = mongoClient.getDatabase("tuhao-data");
//        MongoCollection c1 = db.getCollection("UserModel");
//        MongoCollection c2 = db.getCollection("OrderModel");
//
//        System.out.println("UserModel: " + c1.count());
//        FindIterable findIterable = c1.find();
//        MongoCursor mongoCursor = findIterable.iterator();
//        System.out.println();
//
//        System.out.println("OrderModel: " + c1.count());
//        FindIterable findIterable1 = c1.find();
//        MongoCursor mongoCursor1 = findIterable1.iterator();
//
//        System.out.println();
//        System.out.println(mongoCursor.next().toString());
//        System.out.println(mongoCursor.next().toString());
//        System.out.println(mongoCursor.next().toString());
//
//        System.out.println();
//        System.out.println(mongoCursor1.next().toString());
//        System.out.println(mongoCursor1.next().toString());
//    }
//
//    @Test
//    public void TestFind() throws InterruptedException {
//        for(int i=0;i<2020;i++){
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        countWithPool();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            t.start();
//        }
//
//        System.out.println("********************");
//
//        Thread.sleep(5000);
//
//        System.out.println("********************");
//
//        for(int i=0;i<220;i++){
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        countWithPool();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            t.start();
//        }
//System.err.println(mongoClient.getDatabase("tuhao-data").getCollection("UserModel").count());
//
//        System.err.println("done!");
//        Thread.sleep(50000);
//    }
//
//    public void countWithPool() throws InterruptedException {
//        MongoDatabase data = mongoClient.getDatabase("tuhao-data");
//        MongoCollection<Document> col = data.getCollection("UserModel");
//        col.count(new Document().append("name","name3"));
//        Thread.sleep(3000);
//    }
//
//    public void count() throws Exception {
//        MongoAdapter adapter = new MongoAdapter("tuhao-data");
//        adapter.collection("UserModel").count("{name:'name3'}");
//        Thread.sleep(3000);
//
//    }
//}
