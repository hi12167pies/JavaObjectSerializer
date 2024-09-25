package cf.pies.serialize.encoder;

import cf.pies.serialize.Deserializer;
import com.sun.istack.internal.Nullable;

import java.io.DataInputStream;
import java.lang.reflect.Field;

public interface SerializeReader {
    Object readObject(Deserializer deserializer, DataInputStream in, Class<?> type, @Nullable Field field) throws Exception;
}
