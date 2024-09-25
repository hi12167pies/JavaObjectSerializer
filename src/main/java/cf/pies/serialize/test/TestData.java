package cf.pies.serialize.test;

import cf.pies.serialize.SerializeClass;
import cf.pies.serialize.SerializeField;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A full class containing many data for tests
 */
@SerializeClass(
        version = 1
)
public class TestData {
    @SerializeField
    private final List<TestUser> users = new ArrayList<>();

    @SerializeField
    private final Set<TestUser> userSet = new HashSet<>();

    public TestData() {
    }

    public void addUser(TestUser user) {
        users.add(user);
        userSet.add(user);
    }

    public void printDebug() {
        System.out.println("--- [ start ] ---");

        System.out.println("Users: " + users.size());
        for (TestUser user : users) {
            user.printDebug();
        }

        System.out.println("Users Set: " + userSet.size());
        for (TestUser user : userSet) {
            user.printDebug();
        }

        System.out.println("--- [ end ] ---");
    }
}
