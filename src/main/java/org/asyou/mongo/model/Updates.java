//package org.asyou.mongo.model;
//
//import org.bson.Bson;
//
//public class Updates {
//    public static <T>Bson inc(String fieldName, T value){
//        return Commons.outsideOpt("$inc",fieldName, value);
//    }
//
//    public static <T>Bson inc(Bson value){
//        return Commons.createBson("$inc",value);
//    }
//
//    public static <T>Bson set(String fieldName, T value){
//        return Commons.outsideOpt("$set",fieldName, value);
//    }
//
//    public static <T>Bson set(Bson value){
//        return Commons.createBson("$set",value);
//    }
//
//    public static <T>Bson unset(String fieldName, T value){
//        return Commons.outsideOpt("$unset",fieldName, value);
//    }
//
//    public static <T>Bson unset(Bson value){
//        return Commons.createBson("$unset",value);
//    }
//
//    public static <T>Bson addtoset(String fieldName, T... values){
//        return Commons.outsideOpt("$addtoset",fieldName, values);
//    }
//
//    public static Bson addtoset(Bson value){
//        return Commons.createBson("$addtoset",value);
//    }
//
//    public static <T>Bson pop(String fieldName, T value){
//        return Commons.outsideOpt("$pop",fieldName, value);
//    }
//
//    public static Bson pop(Bson value){
//        return Commons.createBson("$pop",value);
//    }
//
//    public static <T>Bson pull(String fieldName, T value){
//        return Commons.outsideOpt("$pull",fieldName, value);
//    }
//
//    public static Bson pull(Bson value){
//        return Commons.createBson("$pull",value);
//    }
//
//    public static <T>Bson pull_all(String fieldName, T... value){
//        return Commons.outsideOpt("$pull_all",fieldName, value);
//    }
//
//    public static Bson pull_all(Bson value){
//        return Commons.createBson("$pull_all",value);
//    }
//
//    public static <T>Bson pull_by(String fieldName, T value){
//        return Commons.outsideOpt("$pull_by",fieldName, value);
//    }
//
//    public static Bson pull_by(Bson value){
//        return Commons.createBson("$pull_by",value);
//    }
//
//    public static <T>Bson pull_all_by(String fieldName, T... value){
//        return Commons.outsideOpt("$pull_all_by",fieldName, value);
//    }
//
//    public static Bson pull_all_by(Bson value){
//        return Commons.createBson("$pull_all_by",value);
//    }
//
//    public static <T>Bson push(String fieldName, T value){
//        return Commons.outsideOpt("$push",fieldName, value);
//    }
//
//    public static Bson push(Bson value){
//        return Commons.createBson("$push",value);
//    }
//
//    public static <T>Bson push_all(String fieldName, T... value){
//        return Commons.outsideOpt("$push_all",fieldName, value);
//    }
//
//    public static Bson push_all(Bson value){
//        return Commons.createBson("$push_all",value);
//    }
//
//    public static <T>Bson replace(String fieldName, T value){
//        return Commons.outsideOpt("$replace",fieldName, value);
//    }
//
//    public static Bson replace(Bson value){
//        return Commons.createBson("$replace",value);
//    }
//}
