package arkhamDraft;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decoder {

    public static ArrayList<String> watchDraftInputDecrypter(String input) {
        ArrayList<String> arguments = new ArrayList<>();
        input = input.replaceAll(" +", " ").trim();
        if (input.matches("draft *: *\\d+")) {
            arguments.add("draft");
            arguments.add(input.replaceFirst("draft *: *",""));
        }
        if (input.matches("add *\\d+")) {
            arguments.add("add");
            arguments.add(input.replaceFirst("add *",""));
        }
        if (input.matches("redraft *\\d+")) {
            arguments.add("redraft");
            arguments.add(input.replaceFirst("redraft *",""));
        }
        if (input.matches("add *sideboard *\\d+")) {
            arguments.add("addSideboard");
            arguments.add(input.replaceFirst("add *sideboard *",""));
        }
        if (input.matches("discard *\\d+")) {
            arguments.add("discard");
            arguments.add(input.replaceFirst("discard *",""));
        }
        if (arguments.size() == 0) arguments.add("Could not decrypt draft command.");
        return arguments;
    }

    public static ArrayList<String> watchFilterInputDecrypter(String input) {
        ArrayList<String> arguments = new ArrayList<>();
        input = input.replaceAll(" +", " ").trim();
        if (input.matches(".*[^!]:.*") || input.matches(".*!:.*")) {
            arguments.add("containsFilter");
            if (input.matches(".*[^!]:.*")) {
                arguments.add(":");
                arguments.add(input.replaceFirst(":.*","").trim());
            } else {
                arguments.add("!:");
                arguments.add(input.replaceFirst("!:.*","").trim());
            }
            String[] parameters = input.replaceFirst(".*:","").split(",");
            for (String parameter : parameters) {
                arguments.add(parameter.trim());
            }
        } else if (input.matches(".*<.*|.*>.*|.*=.*")) {
            String relatorString = input.replaceAll("[^<>=!]","");
            if (relatorString.matches(Relator.relatorRegex)) {
                arguments.add("numericalFilter");
                arguments.add(relatorString);
                arguments.add(input.replaceFirst("<=.*|>=.*|=.*|<.*|>.*", "").trim());
                String[] parameters = input.replaceFirst(".*[<>=!]+", "").split(",");
                for (String parameter : parameters) {
                    arguments.add(parameter.trim());
                }
            } else {
                arguments.add("Could not decrypt filter.");
            }
        } else {
            arguments.add("Could not decrypt filter.");
        }
        return arguments;
    }

    public static Comparator<Card> decryptComparator(String comparatorString) {
        switch (comparatorString) {
            case "Type, then name":
                return Card.typeNameC;
            case "Just name":
                return Card.nameC;
            case "Faction, then name":
                return Card.factionNameC;
            case "Faction, then XP, then name":
                return Card.factionXpNameC;
            case "XP, then name":
                return Card.xpNameC;
            case "Cost, then name":
                return Card.costNameC;
        }
        return null;
    }

    public static ArrayList<String> decryptGUIFilter(String attributeString, String relatorString, String valueString) {
        ArrayList<String> arguments = new ArrayList<>();
        if (attributeString.equals("XP")) {
            arguments.add("numericalFilter");
            arguments.add(relatorString);
        } else {
            arguments.add("containsFilter");
            if (relatorString.equals("contains not")) {
                arguments.add("!:");
            } else {
                arguments.add(":");
            }
        }
        arguments.add(attributeString);
        String[] parameters = valueString.replaceAll(" {2}"," ").trim().split(",");
        for (String parameter : parameters) {
            arguments.add(parameter.trim());
        }
        return arguments;
    }

    public static boolean isThisACardEntry(String input) {
        Pattern pattern = Pattern.compile("\\dx.*(.*)\\s*");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static List<Card> findCardInCardBoxFromString(CardBox cardBox, String input) {
        Pattern pattern = Pattern.compile("(?<=x).*(?=\\()"); //Matches anything which is preceded by x and succeeded by (
        Matcher matcher = pattern.matcher(input);
        String realInput = "";
        if (matcher.find()) {
            realInput = matcher.group(0);
        }
        String cardString = input.substring(0,input.indexOf("x"));
        int cardinality = Integer.parseInt(cardString);
        String wholeName = realInput.replaceAll("  "," ").trim();
        int xp = 0;
        String subName = null;
        if (wholeName.contains("[")) {
            xp = Integer.parseInt(wholeName.substring(wholeName.indexOf("[")+1,wholeName.indexOf("]")));
            wholeName = wholeName.substring(0,wholeName.indexOf("[")).trim();
        }
        if (wholeName.contains(":")) {
            subName = wholeName.substring(wholeName.indexOf(":")+1);
            wholeName = wholeName.substring(0, wholeName.indexOf(":"));
        }
        return cardBox.findAllCardsWithGivenTraits(wholeName, subName, xp, cardinality);
    }
}
