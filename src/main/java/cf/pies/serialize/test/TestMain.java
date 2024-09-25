package cf.pies.serialize.test;

import cf.pies.serialize.Deserializer;
import cf.pies.serialize.Serializer;

public class TestMain {
    public static void main(String[] args) throws Exception {
        TestData testData = new TestData();
        testData.addUser(new TestUser("John", 15));

        testData.printDebug();

        byte[] serialized = Serializer.serialize(testData);

        TestData deserialized = Deserializer.deserialize(serialized, TestData.class);

        deserialized.printDebug();
    }
}
