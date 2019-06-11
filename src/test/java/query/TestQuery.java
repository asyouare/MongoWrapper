package query;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import org.asyou.mongo.Find;
import org.asyou.mongo.common.Convert;
import org.asyou.mongo.query.QueryMatcher;
import org.asyou.mongo.type.DateTimeFromTo;
import org.bson.BsonDocument;
import org.junit.Test;

public class TestQuery {
    @Test
    public void Test_Query(){
        DateTimeFromTo fromTo = new DateTimeFromTo("dateTime");
        QueryMatcher matcher = QueryMatcher.create("{a:1,b:2,c:'string'}");
        matcher.contain().not().dateTimeFromTo(fromTo).not().not().or();
        System.out.println(matcher.toBson().toBsonDocument(null, Convert.getCodecRegistry()));
        System.out.println(fields(eq("a",1)).toBsonDocument(null, Convert.getCodecRegistry()));
    }

    @Test
    public void Test_Switch(){
        int i = 3;
        switch (i){
            default:
                System.out.println("default");
            case 1:
                System.out.println("1");
                break;
            case 2:
                System.out.println("2");
                break;
        }
    }
}
