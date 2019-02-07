package pl.artelaguna.backend.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class Suppliers {

    public static <T, U> Supplier<U> andThen(Supplier<T> supplier, Function<? super T, U> function) {
        return () -> function.apply(supplier.get());
    }
}
