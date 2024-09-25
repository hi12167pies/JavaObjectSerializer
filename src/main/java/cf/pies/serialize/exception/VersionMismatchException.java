package cf.pies.serialize.exception;

/**
 * Thrown when the version from the deserializer does not match the version in the serialized data.
 */
public class VersionMismatchException extends Exception {
    public VersionMismatchException(String message) {
        super(message);
    }
}
