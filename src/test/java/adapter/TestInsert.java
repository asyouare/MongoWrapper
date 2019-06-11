package adapter;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.UserModel;
import org.asyou.mongo.common.Convert;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.dao.MongoHostInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 17/5/3.
 */
public class TestInsert extends BaseInit {
    @Test
    public void TestInsertOne() throws Exception {
        MongoAdapter adapter = AdapterFactory.getAdapter();
        for(int i=0;i<100;i++) {
            UserModel userModel = getUserModel();
            userModel.setId(i+1);
            userModel.setName("name"+(i+1));
            adapter.insert().insertOne(userModel);
        }
    }

    @Test
    public void TestInserMany() throws Exception {
        List<UserModel> list = new ArrayList<>();
        for(int i=0;i<100;i++) {
            UserModel userModel = getUserModel();
            userModel.setId(i+1);
            userModel.setName("name"+(i+1));
            list.add(userModel);
        }
        MongoAdapter adapter = AdapterFactory.getAdapter();
        adapter.insert().insertMany(list);
        System.out.println(adapter.find().as(UserModel.class).findMany().size());
//        Document bsonObject = Document.parse(Filters.eq("a",1).toBsonDocument(Document.class, Convert.getCodecRegistry()));
    }

    /*


    adapter.find().as(AccountModel.class).findOne(query);
    adapter.find().as(AccountModel.class).findMany(query);
    adapter.find().as(AccountModel.class).page(query, 1, 20);
    adapter.delete().updateOne(query, value);
    adapter.delete().updateMany(query, values);
    adapter.insert().insertOne(value);
    adapter.insert().insertMany(values);
    adapter.delete().deleteOne(query);
    adapter.delete().deleteMany(query);



    adapter.find().query(query).as(AccountModel.class).findOne();
    adapter.find().query(query).as(AccountModel.class).findMany();
    adapter.find().query(query).as(AccountModel.class).page(1, 20);
    adapter.update().query(query).value(value).updateOne();
    adapter.update().query(query).value(values).updateMany();
    adapter.insert().value(value).insertOne();
    adapter.insert().value(values).insertMany();
    adapter.delete().query(query).deleteOne();
    adapter.delete().query(query).deleteMany();



    adapter.find(query).as(AccountModel.class).findOne();
    adapter.find(query).as(AccountModel.class).findMany();
    adapter.find(query).as(AccountModel.class).page(1, 20);
    adapter.delete(query, value).updateOne();
    adapter.delete(query, values).updateMany();
    adapter.insert(value).insertOne();
    adapter.insert(values).insertMany();
    adapter.delete(query).deleteOne();
    adapter.delete(query).deleteMany();


     */

}
