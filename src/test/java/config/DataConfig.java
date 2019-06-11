package config;

import org.asyou.mongo.base.Config;
import org.asyou.mongo.common.Convert;

/**
 * Created by steven on 2017/5/2.
 */
public class DataConfig extends Config {
    public DataConfig(String hostname){
        super("tuhao-data",
                hostname,
                27017,
                "tuhao",
                "Tuhao@2314",
                "tuhao-data");
    }
}
