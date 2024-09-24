package cf.pies.serialize.test;

import cf.pies.serialize.Deserializer;
import cf.pies.serialize.Serializer;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws ReflectiveOperationException, IOException {
        TestUser user = new TestUser("John", 15);

        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());

        byte[] serialized = Serializer.serialize(user);

        System.out.println("---");

        TestUser deserialized = Deserializer.deserialize(serialized, TestUser.class);

        System.out.println("Deserialized");
        System.out.println("Name: " + deserialized.getName());
        System.out.println("Age: " + deserialized.getAge());

    }
}
