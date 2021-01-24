package arkhamDraft;

public class TestCommons {
    public static Card getDummyCardWithFaction(String factionName) {
        return new Card(null, null, factionName, null, false, null, null, null, null, null);
    }

    public static Card getDummyCardWithXP(int xpValue) {
        return new Card(null, null, null, null, false, null, null, null, null, xpValue);
    }

    public static Card getDummyCardWithName(String name) {
        return new Card(null, null, null, null, false, null, name, null, null, null);
    }

}
