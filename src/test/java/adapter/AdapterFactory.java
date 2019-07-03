package adapter;

import org.asyou.mongo.base.Config;
import org.asyou.mongo.dao.MongoAdapter;
import org.asyou.mongo.dao.MongoHostManager;
import org.asyou.mongo.exception.MongoAdapterException;

public class AdapterFactory {
    private static String hostname = "47.93.160.185";
    private static MongoAdapter adapter;

    public static MongoAdapter getAdapter() throws MongoAdapterException {
        if (null == adapter)
            adapter = new MongoAdapter("tuhao-data");
        return adapter;
    }

    static{
        try {
            initConfig();
        } catch (MongoAdapterException e) {
            e.printStackTrace();
        }
    }

    public static void initConfig() throws MongoAdapterException {
        Config dataConfig = Config.build().setAdapterId(hostname).setPort(27017);
        MongoHostManager.addHost(dataConfig);
    }
}
