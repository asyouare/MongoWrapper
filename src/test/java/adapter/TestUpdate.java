package adapter;

import model.UserModel;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.exception.MongoAdapterException;
import org.junit.Test;

public class TestUpdate {
    @Test
    public void Test_UpdateOne() throws MongoAdapterException {
        MongoAdapter adapter = AdapterFactory.getAdapter();
        UserModel queryModel = new UserModel();
        UserModel userModel = new UserModel();
//        userModel.set_id(new ObjectId());
//        userModel.setName("name2");
        adapter.update().updateOne(queryModel, userModel);
    }

    @Test
    public void Test_UpdateMany() throws MongoAdapterException {
        MongoAdapter adapter = AdapterFactory.getAdapter();
        UserModel queryModel = new UserModel();
        UserModel userModel = new UserModel();
//        userModel.set_id(new ObjectId());
//        userModel.setName("name2");
        adapter.update().updateMany(queryModel, userModel);
    }
}
