package cf.pies.serialize.encoder;

import cf.pies.serialize.Deserializer;
import cf.pies.serialize.encoder.stream.SerializeInputStream;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Field;

public interface SerializeReader {
    /**
     * Read an object from the <code>DataInputStream in</code> based on the arguments.
     * @param type The type of the object being read.
     * @param field Used if there is an array or set, to get the type of the generic.
     */
    Object readObject(Deserializer deserializer, SerializeInputStream in, Class<?> type, @Nullable Field field) throws Exception;
}
