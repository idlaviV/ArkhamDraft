package arkhamDraft;

import java.util.ArrayList;
import java.util.Comparator;

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
}
