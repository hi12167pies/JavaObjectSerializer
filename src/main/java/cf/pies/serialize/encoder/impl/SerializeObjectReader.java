package cf.pies.serialize.encoder.impl;

import cf.pies.serialize.Deserializer;
import cf.pies.serialize.SerializerUtil;
import cf.pies.serialize.annotation.SerializeClass;
import cf.pies.serialize.encoder.SerializeReader;
import com.sun.istack.internal.Nullable;

import javax.activation.UnsupportedDataTypeException;
import java.io.DataInputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SerializeObjectReader implements SerializeReader {
    @Override
    public Object readObject(Deserializer deserializer, DataInputStream in, @Nullable Class<?> type, Field field) throws Exception {
        // Basic data types
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
        }
        // Start of advanced data types
        else if (type.isArray()) {
            // Arrays - eg. char[]
            int length = in.readInt();
            Object array = Array.newInstance(type.getComponentType(), length);

            for (int i = 0; i < length; i++) {
                Array.set(array, i, readObject(deserializer, in, type.getComponentType(), null));
            }

            return array;
        } else if (type == List.class) {
            // Lists - eg. List<String>
            int length = in.readInt();
            List<Object> list = new ArrayList<>(length);

            Class<?> listType = SerializerUtil.getFieldGeneric(field);

            for (int i = 0; i < length; i++) {
                list.add(readObject(deserializer, in, listType, null));
            }

            return list;
        } else if (type == Set.class) {
            // Sets - eg. Set<String>
            int length = in.readInt();
            Set<Object> list = new HashSet<>(length);

            Class<?> listType = SerializerUtil.getFieldGeneric(field);

            for (int i = 0; i < length; i++) {
                list.add(readObject(deserializer,in, listType, null));
            }

            return list;
        } else if (type.isAnnotationPresent(SerializeClass.class)) {
            // Any SerializeClass
            int length = in.readInt();
            byte[] data = new byte[length];
            in.read(data, 0, length);

            return deserializer.deserialize(data, type);
        } else {
            throw new UnsupportedDataTypeException("Unsupported type: " + type);
        }
    }
}
