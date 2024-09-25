package cf.pies.serialize.encoder;

import cf.pies.serialize.Serializer;

import java.io.DataOutputStream;

public interface SerializeWriter {
    /**
     * Writes the <code>Object value</code> to the <code>DataOutputStream out</code>
     */
    void writeObject(Serializer serializer, DataOutputStream out, Object value) throws Exception;
}
