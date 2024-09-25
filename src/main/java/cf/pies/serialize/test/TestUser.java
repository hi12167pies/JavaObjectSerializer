package cf.pies.serialize.test;

import cf.pies.serialize.SerializeClass;
import cf.pies.serialize.SerializeField;

import java.util.Arrays;

/**
 * A basic user class for testing data types.
 */
@SerializeClass(
        version = 1
)
public class TestUser {
    @SerializeField
    private String name;

    @SerializeField
    private int age;

    @SerializeField
    private byte[] stringBytes;

    public TestUser() {
    }

    public TestUser(String name, int age) {
        this.name = name;
        this.age = age;
        this.stringBytes = name.getBytes();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public byte[] getStringBytes() {
        return stringBytes;
    }

    public void printDebug() {
        System.out.println("  --- User ---");
        System.out.println("  " + name + " (" + age + ")");
        System.out.println("  Byte Array: " + new String(stringBytes) + " (" + stringBytes.length + ", " + Arrays.toString(stringBytes) + ")");
        System.out.println("  ------------");
    }
}
