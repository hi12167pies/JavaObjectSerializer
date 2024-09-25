package cf.pies.serialize;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    public static Class<?> getFieldGeneric(Field field) throws IOException {
        Class<?> listType = null;

        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;

            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            listType = (Class<?>) actualTypeArguments[0];
        }

        if (listType == null) {
            throw new IOException("List type is null.");
        }
        return listType;
    }
}
