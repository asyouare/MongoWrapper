package org.asyou.mongo.query;

import com.mongodb.client.model.*;
import org.asyou.mongo.common.Assertions;
import org.asyou.mongo.common.Convert;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 17/10/17.
 */
public class QueryAggregate {

    public static QueryAggregate create(){
        return new QueryAggregate();
    }

    public static QueryAggregate create(Bson match){
        return new QueryAggregate(match);
    }

    public static QueryAggregate create(String match){
        return new QueryAggregate(match);
    }

    public static <T> QueryAggregate create(T match){
        return new QueryAggregate(match);
    }

    private Bson match;
    private Group group;

    public QueryAggregate(){ }

    public QueryAggregate(Bson match){
        this.match(match);
    }

    public QueryAggregate(String match){
        this.match(match);
    }

    public <T> QueryAggregate(T match){
        this.match(match);
    }

    public List<Bson> toBsonList(){
        Assertions.notNull("QueryAggregate group expression", this.group);
        if (null == this.match) {
            this.match();
        }
        List<Bson> list = new ArrayList<>();
        list.add(this.match);
        list.add(this.group.toBson());
        return list;
    }

    public QueryAggregate match(){
        match = Aggregates.match(new BsonDocument());
        return this;
    }

    public QueryAggregate match(Bson bsonMatch){
        match = Aggregates.match(bsonMatch);
        return this;
    }

    public QueryAggregate match(String jsonMatch){
        match = Aggregates.match(Convert.toBson(jsonMatch));
        return this;
    }

    public <T> QueryAggregate match(T modelMatch){
        match = Aggregates.match(Convert.toBson(modelMatch));
        return this;
    }

    public Group group(){
        return group(null);
    }

    public <TExpression> Group group(TExpression expression){
        if (null == this.group) {
            this.group = new Group(this, expression);
        }
        return this.group;
    }

    public static class Group extends AQueryBson implements IQuery{
        private QueryAggregate parentObject;
        private Object groupExpression;
        private List<BsonField> listBsonField;
        private char dollar;

        private Group(QueryAggregate parentObject){ this(parentObject, null); }

        private Group(QueryAggregate parentObject, Object expression){
            this.parentObject = parentObject;
            this.groupExpression = expression; 
            this.listBsonField = new ArrayList<>();
            this.dollar = QuerySymbol.DOLLAR;
        }

        public Group sum(String targetFieldName){
            return sum(targetFieldName, "sum");
        }

        public Group sum(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.sum(displayName, dollar + targetFieldName));
            return this;
        }

        public Group avg(String targetFieldName){
            return avg(targetFieldName, "avg");
        }

        public Group avg(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.avg(displayName, dollar + targetFieldName));
            return this;
        }

        public Group first(String targetFieldName){
            return first(targetFieldName, "first");
        }

        public Group first(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.first(displayName, dollar + targetFieldName));
            return this;
        }

        public Group last(String targetFieldName){
            return last(targetFieldName, "last");
        }

        public Group last(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.last(displayName, dollar + targetFieldName));
            return this;
        }

        public Group max(String targetFieldName){
            return max(targetFieldName, "max");
        }

        public Group max(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.max(displayName, dollar + targetFieldName));
            return this;
        }

        public Group min(String targetFieldName){
            return min(targetFieldName, "min");
        }

        public Group min(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.min(displayName, dollar + targetFieldName));
            return this;
        }

        public Group count(){
            return count("count");
        }

        public Group count(String displayName){
            listBsonField.add(new BsonField(displayName,new Document("$sum", 1)));
            return this;
        }

        public QueryAggregate parent(){
            return this.parentObject;
        }

        @Override
        public Bson toBson(){
            return Aggregates.group(groupExpression, listBsonField);
        }

    }
}
