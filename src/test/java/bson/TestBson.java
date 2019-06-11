package bson;


import adapter.BaseInit;
import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import model.Label;
import model.UserModel;
import org.asyou.mongo.common.Convert;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

public class TestBson extends BaseInit{

    @Test
    public void Test_Codec(){
        Gson gson = Convert.getGson();
        String json = gson.toJson(getUserModel());
        System.out.println("json: " + json);

        Document document = (Document) Convert.toBson(json);//new Document("date", LocalDateTime.now());
        System.out.println("doc : " + gson.toJson(document));

        BsonDocument bsonDocument = document.toBsonDocument(null, Convert.getCodecRegistry());
        System.out.println("bson: " + bsonDocument.toJson());

        Pattern pat = Pattern.compile("abc");
        Document document1 = new Document("a",pat);
        BsonDocument bsonDocument1 = new BsonDocument("a",new BsonRegularExpression("abc"));
        System.out.println(bsonDocument1);
    }

    @Test
    public void Test_ToMap(){
        Gson gson = Convert.getGson();
        String json = gson.toJson(getUserModel());
        System.out.println(json);

        Document document = gson.fromJson(json, Document.class);
        System.out.println(document);

        BsonDocument bsonDocument = document.toBsonDocument(Document.class, Convert.getCodecRegistry());
        System.out.println(bsonDocument);

        for (String key : bsonDocument.keySet())
            System.out.println(key + " : " + bsonDocument.get(key).getClass().getTypeName());
    }

    @Test
    public void Test_Convert(){
        System.out.println(new Document().isEmpty());
    }

    @Test
    public void Test_Bson(){
        Document doc = new Document();
        BsonDocument bsonDocument = new BsonDocument();
        Bson bson = new Document();
        System.out.println(Document.class == bson.getClass());

        System.out.println(Projections.include("a","b").getClass().getName());
    }
}
