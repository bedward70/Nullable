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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Equal test cases for {@link Nullable}.
 * @since 0.01
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LocalFinalVariableNameCheck (500 lines)
 */
public final class NullableEqualTest {

    @Test
    public void testForNull() {
        final Nullable n1 = new Nullable(null);
        final Nullable n2 = new Nullable(null);
        Assertions.assertEquals(n1.hashCode(), n2.hashCode(), "Testing null hashCode");
        Assertions.assertEquals(n1, n2, "Testing null equals");
    }

    @Test
    public void testForNonNullEquals() {
        final Nullable<Integer> n1 = new Nullable<>(20_071_226);
        final Nullable<Integer> n2 = new Nullable<>(20_071_226);
        Assertions.assertEquals(
            n1.hashCode(),
            n2.hashCode(),
            "Testing non-null equal hashCode"
        );
        Assertions.assertEquals(
            n1,
            n2,
            "Testing non-null equals"
        );
    }

    @Test
    public void testForNonNullNonEquals() {
        final Nullable<Integer> n1 = new Nullable<>(1);
        final Nullable<Integer> n2 = new Nullable<>(2);
        Assertions.assertNotEquals(
            n1.hashCode(),
            n2.hashCode(),
            "Testing non-null non-equal hashCode"
        );
        Assertions.assertNotEquals(
            n1,
            n2,
            "Testing non-null non-equals"
        );
    }

    @Test
    public void testForNullableNonEquals() {
        final Nullable<Integer> n1 = new Nullable<>(null);
        final Nullable<Integer> n2 = new Nullable<>(2);
        Assertions.assertNotEquals(
            n1.hashCode(),
            n2.hashCode(),
            "Testing nullable non-equal hashCode"
        );
        Assertions.assertNotEquals(
            n1,
            n2,
            "Testing nullable non-equals"
        );
    }
}
