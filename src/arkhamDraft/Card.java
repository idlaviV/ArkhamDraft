package arkhamDraft;

import java.util.*;
import java.util.function.BiFunction;

public class Card {
    private String pack_code;
    private String pack_name;
    private String type_code;
    private String type_name;
    private String subtype_code;
    private String faction_code;
    private String faction_name;
    private String faction2_code;
    private String faction2_name;
    private int position;
    private boolean exceptional;
    private String code;
    private String name;
    private String real_name;
    private String subname;
    private int quantity;
    private int deck_limit;
    private String traits;
    private List<String> traitsList;
    private Integer xp;
    private String text;
    private String real_text;
    private static CardFilter nullFilter = new CardFilter((card)->true);

    public Card(String pack_name, String type_name, String faction_code, String faction2_code, boolean exceptional, String real_name, String subname, Integer xp) {
        this.pack_name = pack_name;
        this.type_name = type_name;
        this.faction_code = faction_code;
        this.faction2_code = faction2_code;
        this.exceptional = exceptional;
        this.real_name = real_name;
        this.subname = subname;
        this.xp = xp;
    }

    public String getSubname() {
        return subname;
    }

    public Card getPhysicalCard() {
        return new Card(pack_name, type_name, faction_code, faction2_code, exceptional, real_name, subname, xp);
    }

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

    public boolean equals(Card card) {
        if (card != null && real_name.equals(card.getReal_name()) && (subname == null || subname.equals(card.getSubname())) && (xp == null || xp.equals(card.getXp()))) {
            return true;
        }
        return false;
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

    public List<String> getFaction_code() {
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

    public String getDraftInfo() {
        //return String.format("%s [%d] (%s,%s)", real_name, xp, pack_name, getFaction_code());
        String name = real_name;
        if (subname != null) {
            name = String.format("%s:%s",name,subname);
        }
        if (xp != null && xp != 0) {
            name = String.format("%s [%d]", name, xp);
        }
        return String.format("%s (%s)", name, pack_name);
    }

    public String getReal_name() {
        return real_name;
    }

    public String getFactionColor() {
        if (getFaction_code().size() != 1 ) {
            return Face.ANSI_MULTICLASS;
        } else {
            switch (getFaction_code().get(0)) {
                case "guardian":
                    return Face.ANSI_BLUE;
                case "seeker":
                    return Face.ANSI_YELLOW;
                case "rogue":
                    return Face.ANSI_GREEN;
                case "survivor":
                    return Face.ANSI_RED;
                case "mystic":
                    return Face.ANSI_PURPLE;
                case "neutral":
                    return Face.ANSI_WHITE;
                default:
                    return Face.ANSI_RESET;
            }
        }
    }

    public boolean isRegular() {
        return xp != null;
        /*try{
        if (type_code.equals("skill") || type_code.equals("event") || type_code.equals("asset")) {
            if (subtype_code == null || !(subtype_code.equals("weakness") || subtype_code.equals("basicweakness"))) {
                if (!text.contains("deck only.")) {
                    return true;
                }
            }
        }
        } catch (Exception e) {
            return false;
        }
        return false;*/
    }

    public static CardFilter generateCardFilter(String attribute, BiFunction<Integer, Integer, Boolean> relator, int value) {
        switch (attribute){
            case "xp":
                return new CardFilter((it) -> relator.apply(it.getXp(),value));
            default:
                return nullFilter;
        }
    }

    public static CardFilter generateCardFilter(String attribute, BiFunction<List<String>, String, Boolean> relator,
                                                             String value) {
        switch (attribute){
            case "faction":
                return new CardFilter((card) -> relator.apply(card.getFaction_code(),value));
            case "trait":
                return new CardFilter((card) -> relator.apply(card.getTraits(),value));
            case "pack":
                return new CardFilter((card) -> relator.apply(Collections.singletonList(card.getPack()),value));
            case "type":
                return new CardFilter((card) -> relator.apply(Collections.singletonList(card.getType()),value));
            case "text":
                return new CardFilter((card) -> (card.getText() != null && card.getText().contains(value)));
            default:
                return nullFilter;
        }
    }

    public static CardFilter generateCardFilter(String attribute, String value){
        if (attribute.equals("text")){
            return new CardFilter((it) -> it.getText().contains(value));
        } else {
            return nullFilter;
        }
    }

    public Integer getFactionValue() {
        if (faction_code == null) return null;
        switch (faction_code) {
            case "guardian":
                return 1;
            case "seeker":
                return 2;
            case "rogue":
                return 3;
            case "mystic":
                return 4;
            case "survivor":
                return 5;
            case "neutral":
                return 6;
            default:
                return 0;
        }
    }

    public Integer getTypeValue() {
        if (type_name == null) return null;
        switch (type_name) {
            case "Asset":
                return 1;
            case "Event":
                return 2;
            case "Skill":
                return 3;
            case "Treachery":
                return 4;
            case "Investigator":
                return 5;
            case "Scenario":
                return 6;
            case "Act":
                return 7;
            case "Agenda":
                return 8;
            case "Location":
                return 9;
            case "Enemy":
                return 10;
            case "Story":
                return 11;
            default:
                return 0;
        }
    }


    public static Comparator<Card> typeC = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
            if (nullC(card1, card2) != null) return nullC(card1, card2);
            Integer type1 = card1.getTypeValue();
            Integer type2 = card2.getTypeValue();
            if (nullC(type1, type2) != null) return nullC(type1, type2);
            return type1 - type2;
        }
    };


    public static Comparator<Card> xpC = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
            if (nullC(card1, card2) != null) return nullC(card1, card2);
            Integer xp1 = card1.getXp();
            Integer xp2 = card2.getXp();
            return Integer.compare(xp1, xp2);
        }
    };

    public static Comparator<Card> nameC = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
            if (nullC(card1, card2) != null) return nullC(card1, card2);
            String name1 = card1.getReal_name();
            String name2 = card2.getReal_name();
            if (card1.getSubname() != null) String.format("%s%s",name1, card1.getSubname());
            if (card2.getSubname() != null) String.format("%s%s",name2, card2.getSubname());
            return name1.compareTo(name2);

        }
    };

    public static Comparator<Card> factionC = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
            if (nullC(card1, card2) != null) return nullC(card1, card2);
            Integer faction1 = card1.getFactionValue();
            Integer faction2 = card2.getFactionValue();
            if (nullC(faction1, faction2) != null) return nullC(faction1, faction2);
            return faction1 - faction2;
        }
    };

    public static Comparator<Card> xpNameC = xpC.thenComparing(nameC);
    public static Comparator<Card> factionXpNameC = factionC.thenComparing(xpC.thenComparing(nameC));
    public static Comparator<Card> typeNameC = typeC.thenComparing(nameC);

    public static Integer nullC(Object o1, Object o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        return null;
    }
}
