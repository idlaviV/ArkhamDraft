package arkhamDraft;

import java.util.List;
import java.util.function.BiFunction;

public class Relator {
    //public static String relatorRegex = "[<>]{0,1}={0,1}|![=:]{0,1}";
    public static String relatorRegex = "<|>|<=|>=|=|:|!:";


    private static BiFunction<Integer, Integer, Boolean> equalRelator() {
           return (i1, i2) -> i2.equals(i1);
    }

    private static BiFunction<Integer, Integer, Boolean> greaterRelator() {
        return (i1, i2) -> {
            if (i1 == null || i2 == null){
                return false;
            }
            return i1 > i2;
        };

    }

    private static BiFunction<Integer, Integer, Boolean> smallerRelator() {
        return (i1, i2) -> {
            if (i1 == null || i2 == null){
                return false;
            }
            return i1 < i2;
        };
    }

    private static BiFunction<Integer, Integer, Boolean> greaterEqualRelator() {
        return (i1, i2) -> {
            if (i1 == null || i2 == null){
                return false;
            }
            return i1 >= i2;
        };
    }

    private static BiFunction<Integer, Integer, Boolean> smallerEqualRelator() {
        return (i1, i2) -> {
            if (i1 == null || i2 == null){
                return false;
            }
            return i1 <= i2;
        };
    }

    private static BiFunction<List<String>, String, Boolean> containsRelator() {
        return (list, value) -> list.stream().anyMatch(s -> s.equalsIgnoreCase(value));
    }

    private static BiFunction<List<String>, String, Boolean> containsNotRelator() {
        return (list, value) -> list.stream().noneMatch(s -> s.equalsIgnoreCase(value));
    }

    public static boolean isContainRelator(String relatorString) {
        return relatorString.equals("!:") || relatorString.equals(":");
    }

    public static BiFunction<List<String>,String,Boolean> getContainRelator(String relatorString){
        switch (relatorString) {
            case ":":
                return containsRelator();
            case "!:":
                return containsNotRelator();
            default:
                return null;
        }
    }

    public static BiFunction<Integer, Integer, Boolean> getNumericalRelator(String relatorString) {
        switch(relatorString){
            case "<=":
                return smallerEqualRelator();
            case "<":
                return smallerRelator();
            case ">=":
                return greaterEqualRelator();
            case ">":
                return greaterRelator();
            case "=":
                return equalRelator();
            default:
                return null;
        }
    }
}
