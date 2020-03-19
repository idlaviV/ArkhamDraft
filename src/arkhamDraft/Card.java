package arkhamDraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Card {
    private String pack_code;
    private String pack_name;
    private String type_code;
    private String type_name;
    private Faction faction_code;
    private String faction_name;
    private Faction faction2_code;
    private Faction faction2_name;
    private int position;
    private boolean exceptional;
    private String code;
    private String name;
    private String real_name;
    private int quantity;
    private int deck_limit;
    private String traits;
    private Integer xp;

    public List<Faction> getFaction_code() {
        if (faction2_code == null) {
            return Collections.singletonList(faction_code);
        } else {
            return Arrays.asList(faction_code, faction2_code);
        }
    }
}
