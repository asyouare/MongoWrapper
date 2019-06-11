package model;

import org.asyou.mongo.annotation.CollectionName;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by steven on 17/5/2.
 */
@CollectionName(name="users")
public class UserModel {

//    public String toString(){
//        return Convert.ModelToMap(this).toString();
//    }

    private ObjectId _id;
    private Integer id;
    private String name;
    private Boolean state;
    private Date date;
    private LocalDateTime localDateTime;
    private List<Label> labelList;
    private Map<String,Label> labelMap;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public List<Label> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<Label> labelList) {
        this.labelList = labelList;
    }

    public Map<String, Label> getLabelMap() {
        return labelMap;
    }

    public void setLabelMap(Map<String, Label> labelMap) {
        this.labelMap = labelMap;
    }
}
