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

    public Card(String pack_name, String type_name, String faction_code, String faction2_code, boolean exceptional, String code, String real_name, String subname, Integer xp) {
        this.pack_name = pack_name;
        this.type_name = type_name;
        this.faction_code = faction_code;
        this.faction2_code = faction2_code;
        this.exceptional = exceptional;
        this.code = code;
        this.real_name = real_name;
        this.subname = subname;
        this.xp = xp;
    }

    public String getSubname() {
        return subname;
    }

    public Card getPhysicalCard() {
        return new Card(pack_name, type_name, faction_code, faction2_code, exceptional, code, real_name, subname, xp);
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
            if (traits != null && !traits.isEmpty()) {
                if (traits.substring(traits.length() - 1).equals(".")) {
                    traits = traits.substring(0, traits.length() - 1);
                }
                String[] traitsArray = traits.split(". ");
                traitsList = Arrays.asList(traitsArray);
            } else {
                traitsList = new ArrayList<>();
            }
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
        String name = real_name;
        if (subname != null) {
            name = String.format("%s:%s",name,subname);
        }
        if (xp != null && xp != 0) {
            name = String.format("%s [%d]", name, xp);
        }
        return String.format("%s%s (%s)%s", getFactionColor(), name, pack_name, Face.ANSI_RESET);
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

    public static CardFilter generateCardFilter(String attribute, BiFunction<List<String>, List<String>, Boolean> relator,
                                                             List<String> values) {
        switch (attribute){
            case "faction":
                return new CardFilter((card) -> relator.apply(card.getFaction_code(),values));
            case "trait":
                return new CardFilter((card) -> relator.apply(card.getTraits(),values));
            case "pack":
                return new CardFilter((card) -> relator.apply(Collections.singletonList(card.getPack()),values));
            case "type":
                return new CardFilter((card) -> relator.apply(Collections.singletonList(card.getType()),values));
            case "text":
                return new CardFilter((card) -> values.stream().anyMatch(value -> card.getText() != null && card.getText().contains(value)));
            default:
                return nullFilter;
        }
    }

    private String getCode() {
        return code;
    }

    private Integer getFactionValue() {
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

    private Integer getTypeValue() {
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


    private static Comparator<Card> typeC = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
            Integer type1 = card1.getTypeValue();
            Integer type2 = card2.getTypeValue();
            return compareIntegers(type1, type2);
        }
    };


    private static Comparator<Card> xpC = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
            Integer xp1 = card1.getXp();
            Integer xp2 = card2.getXp();
            return Integer.compare(xp1, xp2);
        }
    };

    private static Comparator<Card> nameC = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
            String name1 = card1.getReal_name();
            String name2 = card2.getReal_name();
            if (card1.getSubname() != null) String.format("%s%s",name1, card1.getSubname());
            if (card2.getSubname() != null) String.format("%s%s",name2, card2.getSubname());
            return name1.compareTo(name2);

        }
    };

    private static Comparator<Card> factionC = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
            Integer faction1 = card1.getFactionValue();
            Integer faction2 = card2.getFactionValue();
            return compareIntegers(faction1, faction2);
        }
    };

    private static Comparator<Card> codeC = new Comparator<Card>() {
        @Override
        public int compare(Card card1, Card card2) {
            Integer code1 = Integer.parseInt(card1.getCode());
            Integer code2 = Integer.parseInt(card2.getCode());
            return compareIntegers(code1, code2);
        }
    };


    private static Integer compareIntegers(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) return 0;
        if (i1 == null) return -1;
        if (i2 == null) return 1;
        return i1-i2;
    }



    /**
     * Note: this comparator imposes orderings that are inconsistent with equals.
     */
    private static Comparator<Card> nullC = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            if (o1 == null) return -1;
            if (o2 == null) return 1;
            return 0;
        }
    };

    public static Comparator<Card> xpNameC = nullC.thenComparing(xpC.thenComparing(nameC));
    public static Comparator<Card> factionXpNameC = nullC.thenComparing(factionC.thenComparing(xpC.thenComparing(nameC)));
    public static Comparator<Card> typeNameC = nullC.thenComparing(typeC.thenComparing(nameC));

    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Card)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Card c = (Card) o;
        return codeC.compare(this, c) == 0;
    }

}
