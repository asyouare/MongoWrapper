package adapter;

import model.Label;
import model.UserModel;
import org.bson.types.ObjectId;
import org.junit.Before;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by steven on 17/5/3.
 */
public class BaseInit {

    @Before
    public void before_init() throws Exception {
        Logger log = Logger.getLogger("org.mongodb.driver");
        log.setLevel(Level.OFF);
    }

    public UserModel getUserModel(){
        UserModel userModel = new UserModel();
        userModel.set_id(new ObjectId());
        userModel.setId(1);
        userModel.setName("steven");
        userModel.setDate(new Date());
        userModel.setLocalDateTime(LocalDateTime.now());

        Label l1 = new Label();
        l1.setName("l1");
        List<Label> labelList = new ArrayList<>();
        labelList.add(l1);

        Label m1 = new Label();
        m1.setName("m1");
        Map<String,Label> labelMap = new HashMap<>();
        labelMap.put("1", m1);

        userModel.setLabelList(labelList);
        userModel.setLabelMap(labelMap);

        return userModel;
    }

    public UserModel getQuery(){
        UserModel query = new UserModel();

        return query;
    }
}
