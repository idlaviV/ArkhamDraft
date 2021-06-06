package arkhamDraft;

public class TestCommons {
    public static Card getDummyCardWithFaction(String factionCode, String code) {
        return new Card(null, null, null, factionCode, null, false, code, code, null, null, null, null);
    }

    public static Card getDummyCardWithXP(int xpValue, String code) {
        return new Card(null, null, null, null, null, false, code, code, null, null, xpValue, null);
    }

    public static Card getDummyCardWithName(String name, String code) {
        return new Card(null, null, null, null, null, false, code, name, null, null, null, null);
    }

    public static Card getCardEmptyVessel() {
        return new Card("wgd", "Where the Gods dwell", "Asset", "guardian", null, false, "06276", "Empty Vessel", "Abandoned by the Gods", 1, 4, 2);
    }

    public static Card getCardFearless() {
        return new Card("wda", "Where Doom Awaits", "Skill", "mystic", null, false, "02268", "Fearless", null, null, 2, 2);
    }

    public static Card getCardOpenGate() {
        return new Card("tde", "The Dream-Eaters", "Event", "mystic", null, false, "06029", "Open Gate", null, 1, 0, 3);
    }

    public static Card getCardGuts() {
        return new Card("core", "Core", "Skill", "neutral", null, false, "01089", "Guts", null, null, 0, 2);
    }

    public static Card getCardMilan() {
        return new Card("core", "Core", "Asset", "seeker", null, false, "01033", "Dr. Milan Christopher", "Professor of Entomology", 4, 0, 1);
    }

    public static Card[] getSmallCardBase() {
        return new Card[] {
                new Card("tfa", "The Forgotten Age",  "Asset", "rogue", null, false, "53005", "Decorated Skull", "Doom Begets Doom", 0, 3, 2),
                new Card("rttfa", "Return to the Forgotten Age", "Asset", "neutral", null, false, "53012", "Dendromorphosis", "\"Natural\" Transformation",null, null, 1),
                new Card("jac", "Jacqueline Fine",  "Asset", "mystic", null, false, "60407", "Azure Flame", null, 3,0, 2),
                new Card("jac", "Jacqueline Fine", "Asset", "mystic", null, false, "60425", "Azure Flame", null, 3,3, 2),
                new Card("jac", "Jacqueline Fine", "Asset", "mystic", null, false, "60422", "Robes of Endless Night", null, 2, 2, 2),
                new Card("core", "Core", "Asset", "mystic", null, false, "01059", "Holy Rosary", null, 2, 0, 1),
                new Card("tece", "The Essex County Express", "Asset", "neutral", null, false, "02157", "Relic Hunter", null, null, 3, 2),
                new Card("tece", "The Essex County Express", "Asset", "neutral", null, false, "02158", "Charisma", null, null, 3, 2),
                new Card("litas", "Lost in Time and Space", "Asset", "mystic", null, false, "02306", "Shrivelling", null, 3, 5, 2),
                new Card("ptc", "The Path to Carcosa",  "Asset", "neutral", null, false, "03014", "Spirit-Speaker", "Envoy of the Alusi", 2, null, 1),
                new Card("tof", "Threads of Fate", "Asset", "mystic", null, false, "04109", "Arcane Research", null, null, 0, 2),
                new Card("hote", "Heart of the Elders", "Asset", "mystic", null, false, "04197", "Olive McBride", "Will Try Anything Once", 2, 0, 2),
                new Card("tse", "The Secret Name",  "Asset", "mystic", null, false, "05116", "Scroll of Secrets", null, 1, 0, 2),
                new Card("icc", "In the Clutches of Chaos", "Asset", "mystic", null, false, "05279", "Dayana Esperence", "Deals with \"Devils\"", 4, 3, 2),
                new Card("tic", "The Innsmouth Conspiracy", "Asset", "mystic", null, false, "07029", "Sword Cane", null, 2, 0, 2),
                new Card("rtptc", "Return to the Path to Carcosa", "Event", "mystic", null, false, "52008", "Storm of Spirits", null, 3, 3, 2),
                new Card("tmm", "The Miskatonic Museum", "Event", "mystic", null, false, "02111", "Delve Too Deep", null, 1, 0, 2),
                new Card("ptc", "The Path to Carcosa", "Event", "mystic", null, false, "03033", "Uncage the Soul", null, 0, 0, 2),
                new Card("bsr", "Black Stars Rise", "Event", "mystic", null, false, "03270", "Ward of Protection", null, 1, 2, 2),
                new Card("dc", "Dim Carcosa", "Event", "mystic", null, false, "03311", "Time Warp", null, 1, 2, 2),
                new Card("dsm", "Dark Side of the Moon", "Event", "mystic", null, false, "06201", "Spectral Razor", null, 2, 0, 2),
                new Card("dsm", "Dark Side of the Moon", "Event", "mystic", null, false, "06202", "Word of Command", null, 2, 2, 2),
                new Card("core", "Core", "Skill", "neutral", null, false, "01093", "Unexpected Courage", null, null, 0, 2),
                new Card("ptc", "The Path to Carcosa", "Treachery", "neutral", null, false, "03015", "Angered Spirits", null, null, null, 1),
                new Card("fgg", "For the Greater Good","Asset", "guardian", null, false, "05192", "Enchanted Blade", null, 3, 3, 2),
                new Card("fgg", "For the Greater Good", "Asset", "mystic", null, false, "05193", "Enchanted Blade", null, 3, 3, 2),
                new Card("gota", "Guardians of the Abyss", "Asset", "neutral", null, false, "83058", "Summoned Nightgaunt", "Gift from Nodens", 4, 0, 1),
                new Card("gota", "Guardians of the Abyss", "Asset", "neutral", null, false, "83055", "John & Jessie Burke", "Relentless in Pursuit", 4, 0, 1),
                new Card("rttfa", "Return to the Forgotten Age", "Asset", "neutral", null, false, "53037", "Veda Whitsley", "Skilled Botanist", 1, 0, 1),
                getCardOpenGate(),
                getCardEmptyVessel(),
                getCardGuts(),
                getCardFearless(),
                getCardMilan()
        };
    }

}
