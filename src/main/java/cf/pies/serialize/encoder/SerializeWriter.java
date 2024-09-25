package cf.pies.serialize.encoder;

import cf.pies.serialize.Serializer;

import java.io.DataOutputStream;

public interface SerializeWriter {
    void writeObject(Serializer serializer, DataOutputStream out, Object value) throws Exception;
}
