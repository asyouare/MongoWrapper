//package org.asyou.mongo.model;
//
//import org.bson.BasicBSONObject;
//import org.bson.BsonDocument;
//import org.bson.Document;
//import org.bson.codecs.Encoder;
//import org.bson.conversions.Bson;
//
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * Created by steven on 17/10/26.
// */
//public class Commons {
//    public static <T> Bson insideOpt(String fieldName, String operatorName, T value){
//        return new BasicBSONObject(fieldName,new BasicBSONObject(operatorName,value));
//    }
//
//    public static <T> Bson outsideOpt(String operatorName, String fieldName, T value){
//        return new BasicBSONObject(operatorName,new BasicBSONObject(fieldName,value));
//    }
//
//    public static <T> Bson createBSONObject(){
//        return new BasicBSONObject();
//    }
//
//    public static <T> Bson createBSONObject(String fieldName, T value){
//        return new BasicBSONObject(fieldName, value);
//    }
//
//    public static Bson combine(Bson... bsonObjects){
//        return combine(Arrays.asList(bsonObjects));
//    }
//
//    public static Bson combine(List<Bson> bsonObjects){
//        Bson bson = new BsonDocument();
//
//        for (Bson item : bsonObjects) {
//            for (String key : item.keySet()) {
//                bson.put(key, item.get(key));
//            }
//        }
//        return bson;
//    }
//
//    public static Bson combine(Object value, String... fieldNames){
//        return combine(Arrays.asList(fieldNames));
//    }
//
//    public static Bson combine(Object value, List<String> fieldNames){
//        Bson bson = new BasicBSONObject();
//        Iterator names = fieldNames.iterator();
//        while(names.hasNext()) {
//            String fieldName = (String)names.next();
//            bson.put(fieldName,value);
//        }
//        return bson;
//    }
//}
