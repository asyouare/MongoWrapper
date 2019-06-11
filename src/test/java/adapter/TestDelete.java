package adapter;

import model.UserModel;
import org.asyou.mongo.dao.MongoAdapter;
import org.junit.Test;

/**
 * Created by steven on 17/5/3.
 */
public class TestDelete extends BaseInit {
    @Test
    public void TestDeleteOne() throws Exception {
        MongoAdapter adapter = AdapterFactory.getAdapter();
        UserModel userModel = new UserModel();
//        userModel.set_id(new ObjectId());
//        userModel.setName("name2");
        adapter.delete().deleteOne(userModel);
    }

    @Test
    public void TestDeleteMany() throws Exception {
        MongoAdapter adapter = AdapterFactory.getAdapter();
        UserModel userModel = new UserModel();
//        userModel.set_id(new ObjectId());
//        userModel.setName("name2");
        adapter.delete().deleteMany(userModel);
    }
}
