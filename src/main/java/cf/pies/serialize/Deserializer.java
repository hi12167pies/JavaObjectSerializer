package cf.pies.serialize;

import cf.pies.serialize.exception.VersionMismatchException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
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

            if (type == String.class) {
                field.set(object, in.readUTF());
            }

            if (type == int.class || type == Integer.class) {
                field.set(object, in.readInt());
            }
        }

        in.close();

        return object;
    }

}
