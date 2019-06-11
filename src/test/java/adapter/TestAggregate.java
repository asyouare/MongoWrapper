package adapter;

import com.mongodb.client.model.Aggregates;
import org.asyou.mongo.common.Convert;
import org.asyou.mongo.exception.MongoAdapterException;
import org.asyou.mongo.query.QueryAggregate;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestAggregate {
    @Test
    public void Test_GroupSum() throws MongoAdapterException {
//        System.out.println(Convert.toBsonDocument(Aggregates.newGroup(null)));
//        System.out.println(Convert.toBsonDocument(Aggregates.newGroup(null, Accumulators.sum("a","m"), Accumulators.avg("b","t"))));

        QueryAggregate aggregate = QueryAggregate.create();
        aggregate.group().sum("id").avg("id").count();
//        QueryAggregate.create()
        for(Bson bson : aggregate.toBsonList())
            System.out.println(Convert.toBsonDocument(bson));
        Map<String,Number> map = AdapterFactory.getAdapter().collection("users").aggregate().match(aggregate).values();
        System.out.println(map);

        Document match = new Document();
        Document group = new Document();

        Arrays.asList(new Document[]{match,group});
    }

    @Test
    public void Test_Aggregate(){
        System.out.println(new Document("list", QueryAggregate.create().group().sum("abc").parent().toBsonList()));
    }
}
