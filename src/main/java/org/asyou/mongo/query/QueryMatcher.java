package org.asyou.mongo.query;

import com.mongodb.client.model.Filters;
import org.asyou.mongo.common.Convert;
import org.asyou.mongo.type.DateTimeFactory;
import org.asyou.mongo.type.DateTimeFromTo;
import org.bson.*;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.mongodb.client.model.Filters.*;


/**
 * Created by steven on 17/9/20.
 */
public final class QueryMatcher extends AQueryBson implements IQuery{

    public static QueryMatcher create(){
        return new QueryMatcher();
    }

    public static QueryMatcher create(String jsonQuery){
        return new QueryMatcher(jsonQuery);
    }

    public static <T> QueryMatcher create(T modelQuery){
        return new QueryMatcher(modelQuery);
    }

    public static QueryMatcher create(Bson bsonQuery){
        return new QueryMatcher(bsonQuery);
    }

    public QueryMatcher(){ super(); }

    public QueryMatcher(Bson bsonQuery){
        super(bsonQuery);
    }

    public QueryMatcher(String jsonQuery){
        super(jsonQuery);
    }

    public <T> QueryMatcher(T modelQuery){
        super(modelQuery);
    }

    public QueryMatcher dateTimeFromTo(DateTimeFromTo fromTo){
        BsonDocument bsonDocument = Convert.toBsonDocument(bsonObject);
        BsonDocument bsonFromTo = new BsonDocument();
        bsonFromTo.put("$gte", new BsonDateTime(DateTimeFactory.getTime(fromTo.getFrom())));
        bsonFromTo.put("$lte", new BsonDateTime(DateTimeFactory.getTime(fromTo.getTo())));
        BsonDocument dateTimeFromToBson = Convert.toBsonDocument(eq(fromTo.getFieldName(), bsonFromTo));
        for(String dateKey : dateTimeFromToBson.keySet()){
            bsonDocument.append(dateKey, dateTimeFromToBson.get(dateKey));
        }
        this.bsonObject = bsonDocument;
        return this;
    }

    public QueryMatcher and(){
        BsonDocument bsonDocument = Convert.toBsonDocument(bsonObject);
        Set<String> keySet = bsonDocument.keySet();
        List<Bson> andBsons = new ArrayList<>();
        for(String key : keySet){
            andBsons.add(new BsonDocument(key, bsonDocument.get(key)));
        }
        bsonDocument = Convert.toBsonDocument(Filters.and(andBsons));
        this.bsonObject = bsonDocument;
        return this;
    }

    public QueryMatcher or(){
        BsonDocument bsonDocument = Convert.toBsonDocument(bsonObject);
        Set<String> keySet = bsonDocument.keySet();
        List<Bson> andBsons = new ArrayList<>();
        for(String key : keySet){
            andBsons.add(new BsonDocument(key, bsonDocument.get(key)));
        }
        bsonDocument = Convert.toBsonDocument(Filters.or(andBsons));
        this.bsonObject = bsonDocument;
        return this;
    }

    public QueryMatcher not(){
        BsonDocument bsonDocument = Convert.toBsonDocument(bsonObject);
        bsonDocument = Convert.toBsonDocument(Filters.not(bsonDocument));
        this.bsonObject = bsonDocument;
        return this;
    }

    public QueryMatcher contain(){
        BsonDocument bsonDocument = Convert.toBsonDocument(bsonObject);
        Set<String> keySet = bsonDocument.keySet();
        for(String key : keySet){
            if (bsonDocument.get(key).isString()){
                BsonDocument rbson = new BsonDocument(key, new BsonRegularExpression(bsonDocument.get(key).asString().getValue()));
                for(String rkey : rbson.keySet()){
                    bsonDocument.put(rkey, rbson.get(rkey));
                }
            }
        }
        this.bsonObject = bsonDocument;
        return this;
    }

}
