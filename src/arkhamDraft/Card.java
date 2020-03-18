package arkhamDraft;

public class Card {
    private String pack_code;
    private String pack_name;
    private String type_code;
    private String type_name;
    private String faction_code;
    private String faction_name;
    private int position;
    private boolean exceptional;
    private String code;
    private String name;
    private String real_name;
    private int quantity;
    private int deck_limit;
    private String traits;

    public Card(String pack_code, String pack_name, String type_code, String type_name, String faction_code, String faction_name, int position, boolean exceptional, String code, String name, String real_name, int quantity, int deck_limit, String traits) {
        this.pack_code = pack_code;
        this.pack_name = pack_name;
        this.type_code = type_code;
        this.type_name = type_name;
        this.faction_code = faction_code;
        this.faction_name = faction_name;
        this.position = position;
        this.exceptional = exceptional;
        this.code = code;
        this.name = name;
        this.real_name = real_name;
        this.quantity = quantity;
        this.deck_limit = deck_limit;
        this.traits = traits;
    }
}
