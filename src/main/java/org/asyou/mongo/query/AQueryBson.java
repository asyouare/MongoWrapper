package org.asyou.mongo.query;

import org.asyou.mongo.common.Convert;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Created by steven on 17/10/10.
 */
public abstract class AQueryBson {

    static {
        Convert.check_and_init();
    }

    protected Bson bsonObject;

    public Bson getBson() {
        return bsonObject;
    }

    public void setBson(Document bsonObject) {
        this.bsonObject = bsonObject;
    }

    protected AQueryBson(){
        this.bsonObject = new Document();
    }

    protected AQueryBson(Bson bsonObject){
        this.bsonObject = bsonObject;
    }

    protected AQueryBson(String jsonObject){
        this.bsonObject = Convert.toBson(jsonObject, Document.class);
    }

    protected <T> AQueryBson(T modelObject){
        this.bsonObject = Convert.toBson(Convert.toJson(modelObject), Document.class);
    }

    public <TValue> void put(String key, TValue value){
        Document document = new Document(key, value);
        BsonDocument bsonDocument = Convert.toBsonDocument(document);
        BsonDocument bsonObj = Convert.toBsonDocument(this.bsonObject);
        bsonObj.put(key, bsonDocument.get(key));
        this.bsonObject = bsonObj;
    }

    public void putAll(Bson value){
        BsonDocument bsonObj = Convert.toBsonDocument(this.bsonObject);
        bsonObj.putAll(Convert.toBsonDocument(value));
        this.bsonObject = bsonObj;
    }

    public Bson toBson() {
        return this.bsonObject;
//        return null == this.bsonObject ? null : createBson();
    }

}
