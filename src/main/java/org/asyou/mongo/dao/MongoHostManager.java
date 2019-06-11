package org.asyou.mongo.dao;

import org.apache.commons.lang3.StringUtils;
import org.asyou.mongo.base.Config;
import org.asyou.mongo.base.ConfigManager;
import org.asyou.mongo.exception.MongoAdapterException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by steven on 17/10/26.
 */
public class MongoHostManager {
    private static Map<String,MongoHost> map = new HashMap<>();

    public static int getSize(){
        return map != null ? map.size() : 0;
    }

    public static MongoHost getSequoiaHost(String adapterId){
        if (map.containsKey(adapterId)) {
            return map.get(adapterId);
        }
        return null;
    }

    public static boolean addHost(Config... configs) throws MongoAdapterException {
        if (configs.length > 0) {
            Map<String, MongoHost> cmap = new HashMap();
            for (Config config : configs) {
                if (config == null) {
                    throw MongoAdapterException.create(String.format("SequoiaConfig is null"));
                }
                if (StringUtils.isBlank(config.getAdapterId())) {
                    throw MongoAdapterException.create(String.format("SequoiaConfig adapter ID is null"));
                }
                if (map.containsKey(config.getAdapterId())) {
                    throw MongoAdapterException.create(String.format("SequoiaConfig adapter ID'%s' is already used", config.getAdapterId()));
                }
                MongoHost host = new MongoHost(config);
                cmap.put(config.getAdapterId(), host);
            }

            if (configs.length == cmap.size()){
                map.putAll(cmap);
                return true;
            }else{
                throw MongoAdapterException.create(String.format("SequoiaConfig setting error"));
            }
        }
        return false;
    }

    public static boolean containsId(String adapterId){
        return map.containsKey(adapterId);
    }

    public static String getMapString(){
        return ConfigManager.getMapString();
    }
}
