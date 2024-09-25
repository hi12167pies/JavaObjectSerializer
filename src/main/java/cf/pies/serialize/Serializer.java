package cf.pies.serialize;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import javax.activation.UnsupportedDataTypeException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

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

            writeObject(out, value);
        }

        out.close();

        return byteOutputStream.getBytes();
    }

    private static void writeObject(DataOutputStream out, Object value) throws ReflectiveOperationException, IOException  {
        if (value instanceof String) {
            out.writeUTF((String) value);
        } else if (value instanceof Integer) {
            out.writeInt((int) value);
        } else if (value instanceof Short) {
            out.writeShort((short) value);
        } else if (value instanceof Long) {
            out.writeLong((long) value);
        } else if (value instanceof Byte) {
            out.writeByte((byte) value);
        } else if (value instanceof Double) {
            out.writeDouble((double) value);
        } else if (value instanceof Boolean) {
            out.writeBoolean((boolean) value);
        } else if (value instanceof Float) {
            out.writeFloat((Float) value);
        } else if (value instanceof Character) {
            out.writeFloat((char) value);
        }
        // End basic types - Start advanced types.
        else if (value instanceof List || value instanceof Set || value.getClass().isArray()) {
            // Handle any list, set or array
            int length;

            // Get length
            if (value instanceof List) {
                length = ((List<?>) value).size();
            } else if (value instanceof Set) {
                length = ((Set<?>) value).size();
            } else length = Array.getLength(value);

            // Write length
            out.writeInt(length);

            // Write object depending on how the data is formated
            if (value instanceof List) {
                for (Object item : (List<?>) value) {
                    writeObject(out, item);
                }
            } else if (value instanceof Set) {
                for (Object item : (Set<?>) value) {
                    writeObject(out, item);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    writeObject(out, Array.get(value, i));
                }
            }
        } else if (value.getClass().isAnnotationPresent(SerializeClass.class)) {
            byte[] data = Serializer.serialize(value);
            out.writeInt(data.length);
            out.write(data);
        } else {
            throw new UnsupportedDataTypeException("Unsupported type: " + value.getClass());
        }
    }
}
