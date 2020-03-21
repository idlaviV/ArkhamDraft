package arkhamDraft;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

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
    private static Function<Card, Boolean> nullFilter = (card) -> true;

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

    public static Function<Card, Boolean> generateCardFilter(String attribute, BiFunction<Integer,Integer,Boolean> relator, int value) {
        switch (attribute){
            case "xp":
                return (it) -> relator.apply(it.getXp(),value);
            default:
                return nullFilter;
        }
    }

    public static Function<Card, Boolean> generateCardFilter(String attribute, BiFunction<List<Faction>, Faction, Boolean> relator,
                                                             Faction value) {
        if (attribute.equals("faction")) {
            return (it) -> relator.apply(it.getFaction_code(),value);
        } else {
            return nullFilter;
        }
    }

    public static Function<Card, Boolean> generateCardFilter(String attribute, BiFunction<List<String>, String, Boolean> relator,
                                                             String value) {
        switch (attribute){
            case "trait":
                return (it) -> relator.apply(it.getTraits(),value);
            case "pack":
                return (it) -> relator.apply(Collections.singletonList(it.getPack()),value);
            case "type":
                return (it) -> relator.apply(Collections.singletonList(it.getType()),value);
            default:
                return nullFilter;
        }
    }

    public static Function<Card, Boolean> generateCardFilter(String attribute, String value){
        if (attribute.equals("text")){
            return (it) -> it.getText().contains(value);
        } else {
            return nullFilter;
        }
    }
}
