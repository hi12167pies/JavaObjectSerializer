package cf.pies.serialize.encoder.stream;

import java.io.DataInputStream;
import java.io.InputStream;

public class SerializeInputStream extends DataInputStream {
    public SerializeInputStream(InputStream in) {
        super(in);
    }
}
