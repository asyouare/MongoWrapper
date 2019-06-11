//package adapter;
//
//import com.google.gson.Gson;
//import com.mongodb.client.MongoCollection;
//import config.DataConfig;
//import config.LogConfig;
//import model.UserModel;
//import org.asyou.mongo.FindMany;
//import org.asyou.mongo.Page;
//import org.asyou.mongo.base.ConfigManager;
//import org.asyou.mongo.dao.MongoAdapter;
//import org.asyou.mongo.dao.MongoHost;
////import org.asyou.mongo.query.Convert;
//import org.bson.BsonDocument;
//import org.bson.Document;
//import org.bson.types.ObjectId;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by steven on 17/4/29.
// */
//public class AdapterTest {
//
//    public AdapterTest() throws Exception {
//    }
//
//    @Before
//    public void Test_Before() throws Exception {
//        boolean bl = ConfigManager.addConfig(
//                new DataConfig(),
//                new LogConfig()
//        );
//    }
//
//    public MongoAdapter adapter() throws Exception {
//        return new MongoAdapter("tuhao-data");
//    }
//
//    @Test
//    public void bson.TestBson() throws Exception {
//
//        UserModel userModel = new UserModel();
//        userModel.set_id(new ObjectId());
//        userModel.setName("name1");
//        userModel.setDate(new Date());
//
//        Gson gson = new Gson();
//
//        BsonDocument bd = BsonDocument.parse(gson.toJson(userModel));
//
//        System.out.println(String.format("json: %s",gson.toJson(userModel)));
//        System.out.println(String.format("bson: %s",bd.toJson()));
//
//        System.out.println(Convert.MapToModel(Convert.ModelToMap(userModel),UserModel.class));
//
//        System.err.println(ConfigManager.getMapString());
//
//
//        UserModel query = new UserModel();
//        query.setName(userModel.getName());
//        MongoAdapter adapter = new MongoAdapter("tuhao-data");
//        UserModel value = adapter.findOne(query);
//        System.out.println(value);
//
//    }
//
//    @Test
//    public void TestFindMany() throws Exception {
//        FindMany many = new MongoAdapter("tuhao-data").findMany(new UserModel());
//        Page<UserModel> page = many.page(1, 1);
//        List<UserModel> list = page.getList();
//        System.err.println(list.toString());
//    }
//
//    public static <T> List<T> findList(T t) throws Exception {
//        return new MongoAdapter("tuhao-data").findMany(t).getList();
//    }
//
//    @Test
//    public void TestCount() throws Exception {
//        UserModel query1 = new UserModel();
//        query1.setName("a");
//        UserModel query2 = new UserModel();
//        query2.setName("name1");
//        query2.set_id(new ObjectId());
//        System.err.println(new MongoAdapter("tuhao-data").findMany(query2).AND().getList().size());
//    }
//
//    @Test
//    public void TestInsertMany() throws Exception {
////        List<model.UserModel> list = new ArrayList<>();
////        for(int i=0;i<100;i++) {
////            model.UserModel userModel = new model.UserModel();
////            userModel.setName("name"+i);
////            list.add(userModel);
////        }
////
////        System.out.println(adapter().collection("model.UserModel").count("{}").count());
////
//////        for(int i=0;i<1;i++)
////            adapter().insertMany(list);
////
////        model.UserModel query = new model.UserModel();
////        System.err.println(adapter().count(query).count());
//
//
//        MongoAdapter adapter = adapter();
//        host = new MongoHost(adapter, ConfigManager.getMongoConfig(adapter.getId()));
//
//        for (int i = 0; i < 100; i++) {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        createMongoConn();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thread.start();
//            thread.join();
//        }
//
//        Thread.sleep(10000);
//    }
//
//    MongoHost host = null;
//
//    public void createMongoConn() throws Exception {
//        MongoCollection<Document> col = host.getDatabase().getCollection("model.UserModel");
//        col.count();
//        host.release();
//    }
//}
