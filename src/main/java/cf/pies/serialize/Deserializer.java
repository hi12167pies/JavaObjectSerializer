package cf.pies.serialize;

import cf.pies.serialize.annotation.SerializeClass;
import cf.pies.serialize.annotation.SerializeField;
import cf.pies.serialize.encoder.SerializeReader;
import cf.pies.serialize.encoder.impl.SerializeObjectReader;
import cf.pies.serialize.exception.VersionMismatchException;
import sun.security.krb5.internal.crypto.Des;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class Deserializer {
    private SerializeReader reader;

    public Deserializer() {
        this(new SerializeObjectReader());
    }

    public Deserializer(SerializeReader reader) {
        this.reader = reader;
    }

    /**
     * <p>Takes an array of data and a class to deserialize to.
     * The class much match the data types of the byte array.</p>
     *
     * This will create a new object, use if you have the object {@link Deserializer#deserializeToObject(byte[], Object)}
     */
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
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
    public <T> T deserializeToObject(byte[] data, T object) throws Exception {
        SerializeClass serializeClass = object.getClass().getAnnotation(SerializeClass.class);

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
        int versionIn = in.readInt();

        if (versionIn != serializeClass.version()) {
            throw new VersionMismatchException("Serialized version " + versionIn + " does not match expected version " + serializeClass.version());
        }

        List<Field> fields = SerializerUtil.findFields(object.getClass(), SerializeField.class);

        for (Field field : fields) {
            field.setAccessible(true);
            field.set(object, reader.readObject(this, in, field.getType(), field));
        }

        in.close();

        return object;
    }
}
