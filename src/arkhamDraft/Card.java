package arkhamDraft;

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
    private List<String> traitsList;
    private Integer xp;
    private String text;
    private String real_text;

    public boolean compareTexts() {
        if (text == null && real_text == null){
            return true;
        } else if (text != null && real_text != null) {
            if (!text.equals(real_text)){
                System.out.println(text);
                System.out.println(real_text);
            }
            return text.equals(real_text);
        } else {
            System.out.println(name+" is seminull");
            return false;
        }

    }

    public String getText() {
        return text;
    }

    public List<String> getTraits() {
        if (traitsList == null) {
            if(!traits.isEmpty() && traits.substring(traits.length()-1).equals(".")){
                traits = traits.substring(0,traits.length()-1);
            }
            String[] traitsArray = traits.split(". ");
            traitsList = Arrays.asList(traitsArray);
        }
        return traitsList;
    }

    public List<Faction> getFaction_code() {
        if (faction2_code == null) {
            return Collections.singletonList(faction_code);
        } else {
            return Arrays.asList(faction_code, faction2_code);
        }
    }

    public Integer getXp() {
        return xp;
    }

    public String getPack() {
        return pack_code;
    }

    public String getType() {
        return type_code;
    }

    public boolean isExceptional() {
        return exceptional;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getDeck_limit() {
        return deck_limit;
    }
}
