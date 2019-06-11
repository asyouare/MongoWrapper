package org.asyou.mongo.base;

import org.apache.commons.lang3.StringUtils;
import org.asyou.mongo.dao.MongoHostManager;
import org.asyou.mongo.exception.MongoAdapterException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steven on 2017/5/2.
 */
public class ConfigManager implements Serializable {
    private static Map<String, Config> map = new HashMap<>();

    public static int getSize(){
        return map != null ? map.size() : 0;
    }

    public static boolean addConfig(Config... configs) throws MongoAdapterException { return addConfig(true, configs); }

    public static boolean addConfig(boolean toCreateHost, Config... configs) throws MongoAdapterException {
        if (configs.length > 0) {
            Map<String, Config> cmap = new HashMap();
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
                cmap.put(config.getAdapterId(), config);
            }

            if (configs.length == cmap.size()){
                map.putAll(cmap);
                if (toCreateHost) {
                    MongoHostManager.addHost(configs);
                }
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
        StringBuilder strb = new StringBuilder();
        for(Map.Entry<String,Config> entry : map.entrySet()){
            strb.append(entry.getValue().toString());
            strb.append("\r\n");
        }
        return strb.toString();
    }
}
