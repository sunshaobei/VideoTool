package com.ccee.videotool.arouter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ARouterPathFilter {
    public static List<String> needLogingPaths() {
        List<String> needLoginPaths = new ArrayList<>();
        Field[] fields = RoutePath.class.getFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof LoginIntercept) {
                    try {
                        needLoginPaths.add((String) field.get(null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return needLoginPaths;
    }
}
