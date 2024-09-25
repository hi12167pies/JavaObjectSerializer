package cf.pies.serialize.encoder.impl;

import cf.pies.serialize.Serializer;
import cf.pies.serialize.annotation.SerializeClass;
import cf.pies.serialize.encoder.SerializeWriter;

import javax.activation.UnsupportedDataTypeException;
import java.io.DataOutputStream;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

public class SerializeObjectWriter implements SerializeWriter {
    @Override
    public void writeObject(Serializer serializer, DataOutputStream out, Object value) throws Exception {
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
                    writeObject(serializer, out, item);
                }
            } else if (value instanceof Set) {
                for (Object item : (Set<?>) value) {
                    writeObject(serializer, out, item);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    writeObject(serializer, out, Array.get(value, i));
                }
            }
        } else if (value.getClass().isAnnotationPresent(SerializeClass.class)) {
            // Any SerializeClass
            byte[] data = serializer.serialize(value);
            out.writeInt(data.length);
            out.write(data);
        } else {
            throw new UnsupportedDataTypeException("Unsupported type: " + value.getClass());
        }
    }
}
