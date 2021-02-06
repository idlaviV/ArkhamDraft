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

    public static Card getCardEmptyVessel() {
        return new Card("Where the Gods Dwell", "Asset", "guardian", null, false, "06276", "Empty Vessel", "Abandoned by the Gods", 1, 4);
    }

    public static Card[] getSmallCardBase() {
        return new Card[] {
                new Card("The Forgotten Age", "Asset", "rogue", null, false, "04026", "Decorated Skull", "Doom Begets Doom", 0, 3),
                new Card("Return to the Forgotten Age", "Asset", "neutral", null, false, "53012", "Dendromorphosis", "\"Natural\" Transformation",null, null),
                new Card("Jacqueline Fine", "Asset", "mystic", null, false, "60407", "Azure Flame", null, 3,0),
                new Card("Jacqueline Fine", "Asset", "mystic", null, false, "60425", "Azure Flame", null, 3,3),
                new Card("Jacqueline Fine", "Asset", "mystic", null, false, "60422", "Robes of Endless Night", null, 2, 2),
                new Card("Core Set", "Asset", "mystic", null, false, "01059", "Holy Rosary", null, 2, 0),
                new Card("The Essex County Express", "Asset", "neutral", null, false, "02157", "Relic Hunter", null, null, 3),
                new Card("The Essex County Express", "Asset", "neutral", null, false, "02158", "Charisma", null, null, 3),
                new Card("Lost in Time and Space", "Asset", "mystic", null, false, "02306", "Shrivelling", null, 3, 5),
                new Card("The Path to Carcosa", "Asset", "neutral", null, false, "03014", "Spirit-Speaker", "Envoy of the Alusi", 2, null),
                new Card("Threads of Fate", "Asset", "mystic", null, false, "04109", "Arcane Research", null, null, 0),
                new Card("Heart of the Elders", "Asset", "mystic", null, false, "04197", "Olive McBride", "Will Try Anything Once", 2, 0),
                new Card("The Secret Name", "Asset", "mystic", null, false, "05116", "Scroll of Secrets", null, 1, 0),
                new Card("In the Clutches of Chaos", "Asset", "mystic", null, false, "05279", "Dayana Esperence", "Deals with \"Devils\"", 4, 3),
                getCardEmptyVessel(),
                new Card("The Innsmouth Conspiracy", "Asset", "mystic", null, false, "07029", "Sword Cane", null, 2, 0),
                new Card("Return to the Path to Carcosa", "Event", "mystic", null, false, "52008", "Storm of Spirits", null, 3, 3),
                new Card("The Miskatonic Museum", "Event", "mystic", null, false, "02111", "Delve Too Deep", null, 1, 0),
                new Card("The Path to Carcosa", "Event", "mystic", null, false, "03033", "Uncage the Soul", null, 0, 0),
                new Card("Black Stars Rise", "Event", "mystic", null, false, "03270", "Ward of Protection", null, 1, 2),
                new Card("Dim Carcosa", "Event", "mystic", null, false, "03311", "Time Warp", null, 1, 2),
                new Card("Dark Side of the Moon", "Event", "mystic", null, false, "06201", "Spectral Razor", null, 2, 0),
                new Card("Dark Side of the Moon", "Event", "mystic", null, false, "06202", "Word of Command", null, 2, 2),
                new Card("Core Set", "Skill", "neutral", null, false, "01089", "Guts", null, null, 0),
                new Card("Core Set", "Skill", "neutral", null, false, "01093", "Unexpected Courage", null, null, 0),
                new Card("Where Doom Awaits", "Skill", "mystic", null, false, "02268", "Fearless", null, null, 2),
                new Card("The Path to Carcosa", "Treachery", "neutral", null, false, "03015", "Angered Spirits", null, null, null),
                new Card("For the Greater Good", "Asset", "guardian", null, false, "05192", "Enchanted Blade", null, 3, 3),
                new Card("For the Greater Good", "Asset", "mystic", null, false, "05193", "Enchanted Blade", null, 3, 3),

        };
    }

}
