package org.asyou.mongo.annotation;

import java.lang.annotation.*;

/**
 * Created by steven on 2016/12/13.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CollectionName {
    String name() default "";
}
