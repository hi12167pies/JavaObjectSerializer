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

    public TestUser() {
    }

    public TestUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
