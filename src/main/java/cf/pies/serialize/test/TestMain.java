package cf.pies.serialize.test;

import cf.pies.serialize.Deserializer;
import cf.pies.serialize.Serializer;

public class TestMain {
    public static void main(String[] args) throws Exception {
        TestUser user = new TestUser("John", 15);

        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("bytes: " + new String(user.getStringBytes()));

        byte[] serialized = Serializer.serialize(user);

        System.out.println("---");

        TestUser deserialized = Deserializer.deserialize(serialized, TestUser.class);

        System.out.println("Deserialized");
        System.out.println("Name: " + deserialized.getName());
        System.out.println("Age: " + deserialized.getAge());
        System.out.println("bytes: " + new String(deserialized.getStringBytes()));

    }
}
