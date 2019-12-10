/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Eduard Balovnev (bedward70)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.bedward70.nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A container object which may or may not contain a non-null value.
 *
 * @param <X> the type of value
 * @since 0.01
 *
 * @checkstyle AvoidInlineConditionalsCheck (500 lines)
 * @checkstyle DesignForExtension (500 lines)
 */
public class Nullable<X> {

    /**
     * The value.
     */
    private final X value;

    /**
     * Constructor.
     *
     * @param value A nullable value.
     */
    public Nullable(final X value) {
        this.value = value;
    }

    /**
     * Returns the value.
     * @return The nullable value.
     */
    public X get() {
        return this.value;
    }

    /**
     * Returns the value or default value.
     * @param other A default value.
     * @return The value, if present, otherwise the result the default value .
     */
    public X getOr(final X other) {
        return this.isPresent()
            ? this.value
            : other;
    }

    /**
     * Returns the value ot a produced value.
     * @param supplier A supplier of a default value.
     * @return The value, if present, otherwise the result produced by the supplying function.
     * @throws NullPointerException If value is not present and the supplier is null.
     */
    public X getOrGet(final Supplier<? extends X> supplier) {
        return this.isPresent()
            ? this.value
            : supplier.get();
    }

    /**
     * If a value is non-null, returns true, otherwise false.
     * @return True if a value is non-null, otherwise false.
     */
    public boolean isPresent() {
        return this.value != null;
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * returns the original container, otherwise returns an
     * null {@code Nullable}.
     * @param predicate The predicate to apply to a value.
     * @return The original container, otherwise returns an null {@code Nullable}.
     * @throws NullPointerException If value is present and the predicate is null.
     */
    public Nullable<X> filter(final Predicate<? super X> predicate) {
        final Nullable<X> result;
        if (!this.isPresent() || predicate.test(this.value)) {
            result = this;
        } else {
            result = new Nullable<>(null);
        }
        return result;
    }

    /**
     * If a value is non-null, returns the result of applying the given mapping function.
     * @param mapper The mapping function to apply to a value.
     * @param <Y> The type of the value returned.
     * @return An {@code Nullable} container with the result or the casted current container.
     * @throws NullPointerException If value is present and the mapping function is null.
     */
    public <Y> Nullable<Y> map(final Function<? super X, ? extends Y> mapper) {
        final Nullable<Y> result;
        if (this.isPresent()) {
            result = new Nullable<>(mapper.apply(this.value));
        } else {
            result = (Nullable<Y>) this;
        }
        return result;
    }

    /**
     * If a value is null, returns the result by supplier.
     * @param supplier A supplier of a default value.
     * @return An {@code Nullable} container with the result or the casted current container.
     */
    public Nullable<X> mapOrGet(
        final Supplier<? extends X> supplier
    ) {
        final Nullable<X> result;
        if (this.isPresent()) {
            result = this;
        } else {
            result = new Nullable<>(supplier.get());
        }
        return result;
    }

    /**
     * If a value is non-null, performs the consumer with the value,
     * otherwise does nothing.
     * @param consumer The consumer to be performed, if a value is present.
     * @return The {@code Nullable} current container.
     * @throws NullPointerException If value is present and the consumer is null.
     */
    public Nullable<X> ifPresent(final Consumer<? super X> consumer) {
        if (this.isPresent()) {
            consumer.accept(this.value);
        }
        return this;
    }

    /**
     * If a value is present, returns a sequential Stream containing only that value,
     * otherwise returns an empty Stream.
     * @return The optional value as a Stream
     */
    public Stream<X> stream() {
        return this.isPresent()
            ? Stream.of(this.value)
            : Stream.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean result;
        if (this == obj) {
            result = true;
        } else if (obj instanceof Nullable) {
            result = Objects.equals(this.value, ((Nullable<?>) obj).value);
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public String toString() {
        return this.isPresent()
            ? String.format("Nullable[%s]", this.value)
            : "Nullable.null";
    }
}
