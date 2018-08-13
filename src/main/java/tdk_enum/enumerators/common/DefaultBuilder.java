package tdk_enum.enumerators.common;

import java.util.function.Supplier;

public class DefaultBuilder<E> {
    private Supplier<E> supplier;

    public DefaultBuilder(Supplier<E> supplier) {
        this.supplier = supplier;
    }

    public E createInstance() {
        return supplier.get();
    }
}
