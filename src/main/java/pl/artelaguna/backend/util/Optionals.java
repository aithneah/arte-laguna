package pl.artelaguna.backend.util;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Optionals {

    public static <T> void ifPresentOrElse(Optional<T> optional, Consumer<T> ifPresent, Runnable orElse) {
        if (optional.isPresent())
            ifPresent.accept(optional.get());
        else
            orElse.run();
    }

    public static <T, U> void ifPresentOrElse(Optional<T> optional0, Optional<U> optional1, BiConsumer<T, U> ifPresent, Runnable orElse) {
        if (optional0.isPresent() && optional1.isPresent())
            ifPresent.accept(optional0.get(), optional1.get());
        else
            orElse.run();
    }

    public static <T> Optional<T> invert(Optional<T> optional, Supplier<T> supplier) {
        return optional.map(t -> Optional.<T>empty())
                .orElseGet(Suppliers.andThen(supplier, Optional::of));
    }
}
