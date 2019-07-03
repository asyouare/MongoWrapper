package org.asyou.mongo.dao;

import org.asyou.mongo.base.Config;
import org.asyou.mongo.common.Assertions;
import org.asyou.mongo.exception.MongoAdapterException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by steven on 17/10/26.
 */
public class MongoHostManager {
    private static Map<String,MongoHost> mapHost = new HashMap<>();

    public static int getSize(){
        return mapHost.size();
    }

    public static MongoHost getMongoHost(String adapterId){
        if (mapHost.containsKey(adapterId)) {
            return mapHost.get(adapterId);
        }
        return null;
    }

    public static void addHost(Config... configs) throws MongoAdapterException {
        if (configs.length > 0) {
            Map<String, MongoHost> cmap = new HashMap();
            for (Config config : configs) {
                Assertions.notNull("MongoConfig", config);
                Assertions.notBlank("MongoConfig adapter ID", config.getAdapterId());
                if (mapHost.containsKey(config.getAdapterId())) {
                    throw MongoAdapterException.create(String.format("MongoConfig adapter ID'%s' is already used", config.getAdapterId()));
                }
                MongoHost host = new MongoHost(config);
                cmap.put(config.getAdapterId(), host);
            }

            mapHost.putAll(cmap);
        }
    }

    public static boolean containsId(String adapterId){
        return mapHost.containsKey(adapterId);
    }

}
