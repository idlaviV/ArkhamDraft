package arkhamDraft;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Relator {
    public static BiFunction<Integer, Integer ,Boolean> equalRelator() {
           return (i1,i2) -> i1 == i2;
    }

    public static BiFunction<Integer, Integer ,Boolean> greaterRelator() {
        return (i1,i2) -> i1 > i2;
    }

    public static BiFunction<Integer, Integer ,Boolean> smallerRelator() {
        return (i1,i2) -> i1 < i2;
    }

    public static BiFunction<Integer, Integer ,Boolean> greaterEqualRelator() {
        return (i1,i2) -> i1 >= i2;
    }

    public static BiFunction<Integer, Integer ,Boolean> smallerEqualRelator() {
        return (i1,i2) -> i1 <= i2;
    }

    public static <T> BiFunction<T, List<T> ,Boolean> containsRelator() {
        return (value,list) -> list.contains(value);
    }

    public static <T> BiFunction<T, List<T> ,Boolean> containsNotRelator() {
        return (value,list) -> !list.contains(value);
    }
}
