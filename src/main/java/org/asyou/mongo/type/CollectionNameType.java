package org.asyou.mongo.type;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by steven on 17/9/20.
 */
public class CollectionNameType {
    private String spaceName;
    private String collectionName;

    public CollectionNameType(String spaceName, String collectionName){
        this.spaceName = spaceName;
        this.collectionName = collectionName;
    }

    public boolean isNotBlank(){
        return StringUtils.isNotBlank(this.spaceName) && StringUtils.isNotBlank(this.collectionName);
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String collectionSpaceName) {
        this.spaceName = collectionSpaceName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
