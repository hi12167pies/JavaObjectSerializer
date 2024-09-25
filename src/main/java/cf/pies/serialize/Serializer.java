package cf.pies.serialize;

import cf.pies.serialize.annotation.SerializeClass;
import cf.pies.serialize.annotation.SerializeField;
import cf.pies.serialize.encoder.SerializeWriter;
import cf.pies.serialize.encoder.impl.SerializeObjectWriter;
import cf.pies.serialize.encoder.stream.SerializeOutputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.lang.reflect.Field;
import java.util.List;

public class Serializer {
    private final SerializeWriter writer;

    public Serializer() {
        this(new SerializeObjectWriter());
    }

    public Serializer(SerializeWriter writer) {
        this.writer = writer;
    }

    /**
     * <p>Takes an object and returns a byte array representation.</p>
     *
     * Use {@link SerializeField} on any fields you wish to be serialized in the byte array.
     */
    @SuppressWarnings("deprecation")
    public byte[] serialize(Object object) throws Exception {
        if (!object.getClass().isAnnotationPresent(SerializeClass.class)) {
            throw new IllegalArgumentException("Class is not annotated with SerializeClass");
        }

        SerializeClass serializeClass = object.getClass().getAnnotation(SerializeClass.class);
        int version = serializeClass.version();

        List<Field> fields = SerializerUtil.findFieldsWithAnnotation(object.getClass(), SerializeField.class);

        ByteOutputStream byteOutputStream = new ByteOutputStream();
        SerializeOutputStream out = new SerializeOutputStream(byteOutputStream);

        out.writeInt(version);

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(object);

            writer.writeObject(this, out, value, field);
        }

        out.close();

        return byteOutputStream.toByteArray();
    }
}
