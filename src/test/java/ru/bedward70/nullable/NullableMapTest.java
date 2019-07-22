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

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Map test cases for {@link Nullable}.
 * @since 0.01
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LocalFinalVariableNameCheck (500 lines)
 */
public final class NullableMapTest {

    @Test
    public void testForNullCount() {
        final Nullable<String> n = new Nullable<>(null);
        final AtomicInteger countUseMap = new AtomicInteger();
        n.map(
            value -> {
                countUseMap.incrementAndGet();
                return false;
            }
        );
        Assertions.assertEquals(
            0,
            countUseMap.get(),
            "Testing count for null case"
        );
    }

    @Test
    public void testForNonNullToNonNull() {
        final String string = "value to non-null";
        final Nullable<String> n = new Nullable<>(string);
        final AtomicInteger countUseMap = new AtomicInteger();
        final String expected = "expected";
        final Nullable<String> result = n.map(
            value -> {
                countUseMap.incrementAndGet();
                Assertions.assertEquals(
                    string,
                    value,
                    "Test executing  value to non-null"
                );
                return expected;
            }
        );
        Assertions.assertEquals(
            1,
            countUseMap.get(),
            "Testing count for non-null case"
        );
        Assertions.assertEquals(
            expected,
            result.get(),
            "Testing result for non-null case"
        );
        Assertions.assertTrue(
            result.isPresent(),
            "Testing isPresent for non-null case"
        );
    }

    @Test
    public void testForNonNulltoNull() {
        final String string = "value to null";
        final Nullable<String> n = new Nullable<>(string);
        final AtomicInteger countUseMap = new AtomicInteger();
        final Nullable<String> result = n.map(
            value -> {
                countUseMap.incrementAndGet();
                Assertions.assertEquals(
                    string,
                    value,
                    "Test executing non-null value to null"
                );
                return null;
            }
        );
        Assertions.assertEquals(
            1,
            countUseMap.get(),
            "Testing count to null case"
        );
        Assertions.assertNull(
            result.get(),
            "Testing result for non case"
        );
        Assertions.assertFalse(
            result.isPresent(),
            "Testing isPresent for null case"
        );
    }

    @Test
    public void testForNullAlternative() {
        final Nullable<String> n = new Nullable<>(null);
        final AtomicInteger countUseElse = new AtomicInteger();
        n.map(value -> true, value -> countUseElse.incrementAndGet());
        Assertions.assertEquals(
            0,
            countUseElse.get(),
            "Testing true map and consumer for null case"
        );
    }

    @Test
    public void testForNonNullAlternativeNonNull() {
        final Nullable<String> n = new Nullable<>("value for non-null alternative case");
        final AtomicInteger countUseElse = new AtomicInteger();
        n.map(value -> true, value -> countUseElse.incrementAndGet());
        Assertions.assertEquals(
            0,
            countUseElse.get(),
            "Testing true map and consumer for non-null case"
        );
    }

    @Test
    public void testForNonNullAlternativeNull() {
        final String string = "value for null alternative case";
        final Nullable<String> n = new Nullable<>(string);
        final AtomicInteger countUseElse = new AtomicInteger();
        n.map(
            value -> {
                return (Boolean) null;
            },
            value -> {
                countUseElse.incrementAndGet();
                Assertions.assertEquals(
                    string,
                    value,
                    "Test executing value to null"
                );
            }
        );
        Assertions.assertEquals(
            1,
            countUseElse.get(),
            "Testing false map and consumer for non-null case"
        );
    }
}
