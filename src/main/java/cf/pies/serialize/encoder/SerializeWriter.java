package cf.pies.serialize.encoder;

import cf.pies.serialize.Serializer;
import cf.pies.serialize.encoder.stream.SerializeOutputStream;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Field;

public interface SerializeWriter {
    /**
     * Writes the <code>Object value</code> to the <code>DataOutputStream out</code>
     * @param field Used to check for variable encoding
     */
    void writeObject(Serializer serializer, SerializeOutputStream out, Object value, @Nullable Field field) throws Exception;
}
