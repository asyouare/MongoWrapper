package adapter;

import model.UserModel;
import org.asyou.mongo.Page;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.exception.MongoAdapterException;
import org.asyou.mongo.query.QueryMatcher;
import org.asyou.mongo.query.QueryObject;
import org.asyou.mongo.type.DateTimeFromTo;
import org.bson.Document;
import org.junit.Test;
import sun.nio.ch.ThreadPool;

import java.util.List;


public class TestFind {
    @Test
    public void Test_FindOne() throws MongoAdapterException {

        QueryMatcher matcher = QueryMatcher.create(new UserModel());
        matcher.dateTimeFromTo(DateTimeFromTo.fromField("date"));


        UserModel queryModel = new UserModel();
        UserModel valueModel = new UserModel();

        MongoAdapter adapter = AdapterFactory.getAdapter();

        Page<UserModel> page = adapter.find()
                .match(QueryMatcher.create(queryModel)
                        .contain()
                        .dateTimeFromTo(DateTimeFromTo.fromField("dateTime"))
                )
                .as(UserModel.class)
                .page(0,20);

        List list = page.getList();

        System.out.println(list.size());

        adapter.insert().insertOne(valueModel);

        adapter.update().updateMany(queryModel, valueModel);

        adapter.delete().deleteOne(valueModel);


    }

    @Test
    public void Test_Count() throws MongoAdapterException {
//        AdapterFactory.getAdapter().count();
        System.out.println(AdapterFactory.getAdapter().count().count(new UserModel()));
    }
}
