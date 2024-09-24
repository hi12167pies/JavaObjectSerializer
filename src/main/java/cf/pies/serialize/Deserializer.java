package cf.pies.serialize;

import cf.pies.serialize.exception.VersionMismatchException;

import javax.activation.UnsupportedDataTypeException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class Deserializer {
    /**
     * <p>Takes an array of data and a class to deserialize to.
     * The class much match the data types of the byte array.</p>
     *
     * This will create a new object, use if you have the object {@link Deserializer#deserializeToObject(byte[], Object)}
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) throws ReflectiveOperationException, IOException, VersionMismatchException {
        // Create new instance of object
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T object = constructor.newInstance();

        return deserializeToObject(data, object);
    }

    /**
     * Takes an array of data and a class to deserialize to.
     * The class much match the data types of the byte array.
     */
    public static <T> T deserializeToObject(byte[] data, T object) throws ReflectiveOperationException, IOException, VersionMismatchException {
        SerializeClass serializeClass = object.getClass().getAnnotation(SerializeClass.class);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        int versionIn = in.readInt();

        if (versionIn != serializeClass.version()) {
            throw new VersionMismatchException("Serialized version " + versionIn + " does not match expected version " + serializeClass.version());
        }

        List<Field> fields = SerializerUtil.findFields(object.getClass(), SerializeField.class);

        for (Field field : fields) {
            Class<?> type = field.getType();
            field.setAccessible(true);

            if (type.isArray()) {
                int length = in.readInt();
                Object array = Array.newInstance(type.getComponentType(), length);

                for (int i = 0; i < length; i++) {
                    Array.set(array, i, readObject(in, type.getComponentType()));
                }

                field.set(object, array);
            } else {
                field.set(object, readObject(in, type));
            }
        }

        in.close();

        return object;
    }

    private static Object readObject(DataInputStream in, Class<?> type) throws IOException {
        if (type == String.class) {
            return in.readUTF();
        } else if (type == int.class || type == Integer.class) {
            return in.readInt();
        } else if (type == short.class || type == Short.class) {
            return in.readShort();
        } else if (type == long.class || type == Long.class) {
            return in.readShort();
        } else if (type == byte.class || type == Byte.class) {
            return in.readByte();
        } else if (type == double.class || type == Double.class) {
            return in.readByte();
        } else if (type == boolean.class || type == Boolean.class) {
            return in.readBoolean();
        } else if (type == float.class || type == Float.class) {
            return in.readFloat();
        } else if (type == char.class || type == Character.class) {
            return in.readChar();
        } else {
            throw new UnsupportedDataTypeException("Unsupported type: " + type);
        }
    }
}
