package cf.pies.serialize.encoder.stream;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class SerializeOutputStream extends DataOutputStream {
    public SerializeOutputStream(OutputStream out) {
        super(out);
    }
}
