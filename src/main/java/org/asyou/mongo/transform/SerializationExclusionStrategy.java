package org.asyou.mongo.transform;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by steven on 17/7/18.
 */
public class SerializationExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(final FieldAttributes f) {
            // 这里作判断，决定要不要排除该字段,return true为排除
//                      if ("finalField".equals(f.getFieldName())) return true; //按字段名排除
//            NoExpose noexpose = f.getAnnotation(NoExpose.class);
//            if (noexpose != null)
//                return true; //按注解排除
//                if (org.bson.types.ObjectId.class.getFieldName().equals(f.getDeclaredClass().getFieldName()))
//                    return true;
//                        System.out.println("f: " + f.getFieldName() + " : " + f.getDeclaredClass());
//                        count++;
            return false;
        }

        @Override
        public boolean shouldSkipClass(final Class<?> clazz) {
            // 直接排除某个类 ，return true为排除
//                      return (clazz == int.class || clazz == Integer.class);
//                        System.out.println(this.getClass().getFieldName() + " : " + clazz.getFieldName());
//                        count++;
            return false;
        }
}
