package org.asyou.mongo.dao;

import org.bson.Document;

public class MongoHostInfo {
    public static String getConnections(MongoAdapter adapter){
        Document status = (Document) adapter.getHost().getDatabase("local").runCommand(new Document("serverStatus","")).get("connections");
        return status.toJson();
    }
}
