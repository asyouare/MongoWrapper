package org.asyou.mongo.query;

import org.bson.conversions.Bson;

/**
 * Created by steven on 17/7/14.
 */
public interface IQuery extends Cloneable {
    Bson toBson();
}
