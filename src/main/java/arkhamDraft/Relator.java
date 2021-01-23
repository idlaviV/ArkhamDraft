package arkhamDraft;

import java.util.List;
import java.util.function.BiFunction;

public class Relator {
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

    private static BiFunction<List<String>, List<String>, Boolean> containsRelator() {
        return (list, listValue) -> (list.stream().anyMatch(s -> listValue.stream().anyMatch(v -> v.equalsIgnoreCase(s))));
    }

    private static BiFunction<List<String>, List<String>, Boolean> containsNotRelator() {
        return (list, listValue) -> (list.stream().noneMatch(s -> listValue.stream().anyMatch(v -> v.equalsIgnoreCase(s))));
    }


    public static BiFunction<List<String>, List<String>, Boolean> getContainRelator(String relatorString){
        switch (relatorString) {
            case ":":
                return containsRelator();
            case "!:":
                return containsNotRelator();
            default:
                throw new IllegalArgumentException(String.format("RelatorString is invalid. %s was given. Valid options are ':' and '!:'.",relatorString));
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
