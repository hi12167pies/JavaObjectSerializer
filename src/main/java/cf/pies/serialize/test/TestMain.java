package cf.pies.serialize.test;

import cf.pies.serialize.Deserializer;
import cf.pies.serialize.Serializer;

import java.util.Arrays;

public class TestMain {
    public static void main(String[] args) throws Exception {
        TestData testData = new TestData();
        testData.addUser(new TestUser("John", 15));

        testData.printDebug();

        byte[] serialized = new Serializer().serialize(testData);

        System.out.println();
        System.out.println("Serialized (" + serialized.length + "): " + Arrays.toString(serialized));
        System.out.println();

        TestData deserialized = new Deserializer().deserialize(serialized, TestData.class);

        deserialized.printDebug();
    }
}
