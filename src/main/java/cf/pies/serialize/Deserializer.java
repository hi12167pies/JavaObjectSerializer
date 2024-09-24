package cf.pies.serialize;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Deserializer {
    /**
     * <p>Takes an array of data and a class to deserialize to.
     * The class much match the data types of the byte array.</p>
     *
     * This will create a new object, use if you have the object {@link Deserializer#deserializeToObject(byte[], Object)}
     */
    public static <T> T deserialize(byte[] data, Class<T> clazz) throws ReflectiveOperationException, IOException {
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
    public static <T> T deserializeToObject(byte[] data, T object) throws ReflectiveOperationException, IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));

        List<Field> fields = SerializerUtil.findFields(object.getClass(), SerializeField.class);

        for (Field field : fields) {
            Class<?> type = field.getType();
            field.setAccessible(true);

            if (type == String.class) {
                int length = in.readInt();

                byte[] strBytes = new byte[length];
                in.read(strBytes, 0, length);

                field.set(object, new String(strBytes, StandardCharsets.UTF_8));
            }

            if (type == int.class || type == Integer.class) {
                field.set(object, in.readInt());
            }
        }

        in.close();

        return object;
    }

}
