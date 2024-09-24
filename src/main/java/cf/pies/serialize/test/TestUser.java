package cf.pies.serialize.test;

import cf.pies.serialize.SerializeClass;
import cf.pies.serialize.SerializeField;

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
}
