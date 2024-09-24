package cf.pies.serialize;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class Serializer {
    /**
     * <p>Takes an object and returns a byte array representation.</p>
     *
     * Use {@link SerializeField} on any fields you wish to be serialized in the byte array.
     */
    public static byte[] serialize(Object object) throws ReflectiveOperationException, IOException {
        if (!object.getClass().isAnnotationPresent(SerializeClass.class)) {
            throw new IllegalArgumentException("Class is not annotated with SerializeClass");
        }

        SerializeClass serializeClass = object.getClass().getAnnotation(SerializeClass.class);
        int version = serializeClass.version();

        List<Field> fields = SerializerUtil.findFields(object.getClass(), SerializeField.class);

        ByteOutputStream byteOutputStream = new ByteOutputStream();
        DataOutputStream out = new DataOutputStream(byteOutputStream);

        out.writeInt(version);

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(object);

            if (value instanceof String) {
                String valueStr = (String) value;
                out.writeUTF(valueStr);
            }

            if (value instanceof Integer) {
                int valueInt = (Integer) value;
                out.writeInt(valueInt);
            }
        }

        out.close();

        return byteOutputStream.getBytes();
    }
}
