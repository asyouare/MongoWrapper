package config;

import org.asyou.mongo.base.Config;

/**
 * Created by steven on 2017/5/2.
 */
public class LogConfig extends Config {
    public LogConfig(String hostname){
        super("tuhao-log",
                hostname,
                27017,
                "tuhao",
                "Tuhao@2314",
                "tuhao-log");
    }
}
