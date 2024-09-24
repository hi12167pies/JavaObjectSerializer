package cf.pies.serialize;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SerializerUtil {
    public static List<Field> findFields(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Field> fields = new ArrayList<>();

        // used for getting super class, then next super, etc... until null
        Class<?> currentClazz = clazz;
        while (currentClazz != null) {
            for (Field field : currentClazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(annotation)) {
                    fields.add(field);
                }
            }
            currentClazz = currentClazz.getSuperclass();
        }

        return fields;
    }
}
