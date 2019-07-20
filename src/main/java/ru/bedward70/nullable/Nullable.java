/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Eduard Balovnev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
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
 */
public class Nullable<X> {

    /** The value */
    private final X value;

    /**
     * Constructor.
     *
     * @param value a nullable value.
     */
    public Nullable(final X value) {
        this.value = value;
    }

    /**
     * Returns the value.
     * @return the nullable value.
     */
    public X get() {
        return this.value;
    }

    /**
     * Returns the value or default value.
     * @param other a default value.
     * @return the value, if present, otherwise the result the default value .
     */
    public X getOr(final X other) {
        return isPresent() ?
            this.value
            : other;
    }

    /**
     * Returns the value ot a produced value.
     * @param supplier a supplier of a default value.
     * @return the value, if present, otherwise the result produced by the supplying function.
     * @throws NullPointerException if value is not present and the supplier is null.
     */
    public X getOrGet(final Supplier<? extends X> supplier) {
        return isPresent() ?
            this.value
            : supplier.get();
    }

    /**
     * If a value is non-null, returns true, otherwise false.
     * @return true if a value is non-null, otherwise false.
     */
    public boolean isPresent() {
        return this.value != null;
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * returns the original container, otherwise returns an
     * null {@code Nullable}.
     * @param predicate the predicate to apply to a value.
     * @return the original container, otherwise returns an null {@code Nullable}.
     * @throws NullPointerException if value is present and the predicate is null.
     */
    public Nullable<X> filter(final Predicate<? super X> predicate) {

        return filter(predicate, null);
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * returns the original container, otherwise returns an
     * null {@code Nullable}.
     * @param predicate the predicate to apply to a value.
     * @param falseConsumer if the value does not match the given predicate then the consumer to be performed for
     * the current value.
     * @return the original container, otherwise returns an null {@code Nullable}.
     * @throws NullPointerException if value is present and the predicate is null.
     */
    public Nullable<X> filter(final Predicate<? super X> predicate, final Consumer<? super X> falseConsumer) {

        final Nullable<X> result;
        if (!isPresent() && predicate.test(this.value)) {
            result = this;
        } else {
            result = new Nullable<>(null);
        }
        if (isPresent() != result.isPresent() && falseConsumer != null) {
            falseConsumer.accept(this.value);
        }
        return result;
    }

    /**
     * If a value is non-null, returns the result of applying the given mapping function.
     * @param mapper the mapping function to apply to a value.
     * @param <Y> The type of the value returned.
     * @return an {@code Nullable} container with the result or the casted current container.
     * @throws NullPointerException if value is present and the mapping function is null.
     */
    public <Y> Nullable<Y> map(final Function<? super X, ? extends Y> mapper) {
        return map(mapper, null);
    }

    /**
     * If a value is non-null, returns the result of applying the given mapping function.
     * @param mapper the mapping function to apply to a value.
     * @param <Y> The type of the value returned.
     * @param nullResultConsumer if a result value is null then the consumer to be performed for
     * the original value.
     * @return an {@code Nullable} container with the result or the casted current container.
     * @throws NullPointerException if value is present and the mapping function is null.
     */
    public <Y> Nullable<Y> map(final Function<? super X, ? extends Y> mapper, final Consumer<? super X> nullResultConsumer) {
        final Nullable<Y> result;
        if (isPresent()) {
            result = new Nullable<>(mapper.apply(this.value));
        } else {
            result = (Nullable<Y>) this;
        }
        if (isPresent() != result.isPresent() && nullResultConsumer != null) {
            nullResultConsumer.accept(this.value);
        }
        return result;
    }

    /**
     * If a value is non-null, performs the consumer with the value,
     * otherwise does nothing.
     * @param consumer the consumer to be performed, if a value is present.
     * @return the {@code Nullable} current container.
     * @throws NullPointerException if value is present and the consumer is null.
     */
    public Nullable<X> map(final Consumer<? super X> consumer) {
        if (isPresent()) {
            consumer.accept(this.value);
        }
        return this;
    }

    /**
     * If a value is present, returns a sequential Stream containing only that value, otherwise returns an empty Stream.
     * @return the optional value as a Stream
     */
    public Stream<X> stream() {
        return isPresent()
            ? Stream.of(this.value)
            : Stream.empty();
    }

    /**
     * Returns the hash code of the value.
     * @return hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    /**
     * Returns true if the other object is equal.
     * @param obj an object to be tested for equality.
     * @return true if the other object is equal.
     */
    @Override
    public boolean equals(Object obj) {
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

    /**
     * Returns a string representation.
     * @return the string representation.
     */
    @Override
    public String toString() {
        return isPresent()
            ? String.format("Nullable[%s]", this.value)
            : "Nullable.null";
    }
}
