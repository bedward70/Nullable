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

import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Stream test cases for {@link Nullable}.
 * @since 0.01
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class NullableStreamTest {

    @Test
    public void testForNullCount() {
        final Stream stream = new Nullable(null).stream();
        Assertions.assertEquals(
            0,
            stream.count(),
            "Testing stream method for null value"
        );
    }

    @Test
    public void testForNonNullCount() {
        final String value = "test count";
        final Stream<String> stream = new Nullable<>(value).stream();
        Assertions.assertEquals(
            1,
            stream.count(),
            "Testing stream method for non-null value"
        );
    }

    @Test
    public void testForNonNull() {
        final String value = "test first value";
        final Stream<String> stream = new Nullable<>(value).stream();
        final Optional<String> optional = stream.findFirst();
        Assertions.assertTrue(
            optional.isPresent(),
            "Testing existing stream result for non-null value"
        );
        Assertions.assertEquals(
            value,
            optional.get(),
            "Testing stream result value for non-null value"
        );
    }
}
