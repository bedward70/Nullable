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
 * Filter test cases for {@link Nullable}.
 * @since 0.01
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LocalFinalVariableNameCheck (500 lines)
 */
public final class NullableFilterTest {

    @Test
    public void testForNullCount() {
        final Nullable<String> n = new Nullable<>(null);
        final AtomicInteger countUsePredicate = new AtomicInteger();
        n.filter(
            value -> {
                countUsePredicate.incrementAndGet();
                return false;
            }
        );
        Assertions.assertEquals(
            0,
            countUsePredicate.get(),
            "Testing count for null case"
        );
    }

    @Test
    public void testForNonNullCount() {
        final String string = "value for count";
        final Nullable<String> n = new Nullable<>(string);
        final AtomicInteger countUsePredicate = new AtomicInteger();
        n.filter(
            value -> {
                countUsePredicate.incrementAndGet();
                Assertions.assertEquals(string, value, "Test executing  value");
                return false;
            }
        );
        Assertions.assertEquals(
            1,
            countUsePredicate.get(),
            "Testing count for non-null case"
        );
    }

    @Test
    public void testForNullTrue() {
        final Nullable<String> n = new Nullable<>(null);
        Assertions.assertFalse(
            n.filter(value -> true).isPresent(),
            "Testing true filter for null case"
        );
    }

    @Test
    public void testForNullFalse() {
        final Nullable<String> n = new Nullable<>(null);
        Assertions.assertFalse(
            n.filter(value -> false).isPresent(),
            "Testing false filter for null case"
        );
    }

    @Test
    public void testForNullAlternativeTrue() {
        final Nullable<String> n = new Nullable<>(null);
        final AtomicInteger countUseElse = new AtomicInteger();
        n.filter(value -> true, value -> countUseElse.incrementAndGet());
        Assertions.assertEquals(
            0,
            countUseElse.get(),
            "Testing true filter and consumer for null case"
        );
    }

    @Test
    public void testForNullAlternativeFalse() {
        final Nullable<String> n = new Nullable<>(null);
        final AtomicInteger countUseElse = new AtomicInteger();
        n.filter(value -> false, value -> countUseElse.incrementAndGet());
        Assertions.assertEquals(
            0,
            countUseElse.get(),
            "Testing false filter and consumer for null case"
        );
    }

    @Test
    public void testForNonNullTrue() {
        final Nullable<String> n = new Nullable<>("value for true case");
        Assertions.assertTrue(
            n.filter(value -> true).isPresent(),
            "Testing true filter for non-null case"
        );
    }

    @Test
    public void testForNonNullFalse() {
        final Nullable<String> n = new Nullable<>("value for false case");
        Assertions.assertFalse(
            n.filter(value -> false).isPresent(),
            "Testing false filter for non-null case"
        );
    }

    @Test
    public void testForNonNullAlternativeTrue() {
        final Nullable<String> n = new Nullable<>("value for true alternative case");
        final AtomicInteger countUseElse = new AtomicInteger();
        n.filter(value -> true, value -> countUseElse.incrementAndGet());
        Assertions.assertEquals(
            0,
            countUseElse.get(),
            "Testing true filter and consumer for non-null case"
        );
    }

    @Test
    public void testForNonNullAlternativeFalse() {
        final Nullable<String> n = new Nullable<>("value for false alternative case");
        final AtomicInteger countUseElse = new AtomicInteger();
        n.filter(value -> false, value -> countUseElse.incrementAndGet());
        Assertions.assertEquals(
            1,
            countUseElse.get(),
            "Testing false filter and consumer for non-null case"
        );
    }
}
